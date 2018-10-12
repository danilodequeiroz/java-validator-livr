package livr.Rules

import livr.FunctionKeeper
import livr.LIVRUtils
import livr.Validator
import org.json.simple.JSONObject
import java.io.IOException
import java.util.regex.Pattern

/**
 * Created by vladislavbaluk on 9/28/2017.
 */
object KModifiers {

    val default1 = fun(objects: List<Any>): Function1<FunctionKeeper, Any?>? {
        val defaultValue = objects[0]
        return fun(wrapper: FunctionKeeper): Any? {
            if (LIVRUtils.isNoValue(wrapper.value)) {
                wrapper.fieldResultArr.add(defaultValue)
                return ""
            }
            return ""
        }
    }

    var trim = fun (objects: List<Any>): Function1<FunctionKeeper, Any> {
        return fun(wrapper: FunctionKeeper) : Any{
            if (LIVRUtils.isNoValue(wrapper.value) || wrapper.value.javaClass == JSONObject::class.java) return ""
            wrapper.fieldResultArr.add((wrapper.value.toString() + "").trim { it <= ' ' })

            return ""
        }
    }

    var to_lc = fun (objects: List<Any>): Function1<FunctionKeeper, Any> {
        return fun(wrapper: FunctionKeeper) : Any{
            if (LIVRUtils.isNoValue(wrapper.value) || wrapper.value.javaClass == JSONObject::class.java) return ""
            wrapper.fieldResultArr.add((wrapper.value.toString() + "").toLowerCase())

            return ""
        }
    }

    var to_uc = fun (objects: List<Any>): Function1<FunctionKeeper, Any> {
        return fun(wrapper: FunctionKeeper) : Any{
            if (LIVRUtils.isNoValue(wrapper.value) || wrapper.value.javaClass == JSONObject::class.java) return ""
            wrapper.fieldResultArr.add((wrapper.value.toString() + "").toUpperCase())

            return ""
        }
    }

    val remove = fun(objects: List<Any>): Function1<FunctionKeeper, Any?>? {
        val escaped = Pattern.quote(objects[0].toString() + "")

        val chars = "[$escaped]"
        return fun(wrapper: FunctionKeeper): Any? {
            if (LIVRUtils.isNoValue(wrapper.value) || wrapper.value.javaClass == JSONObject::class.java) return ""
            wrapper.fieldResultArr.add((wrapper.value.toString() + "").replace(chars.toRegex(), ""))

            return ""
        }
    }
    val leave_only = fun(objects: List<Any>): Function1<FunctionKeeper, Any?>? {
        val escaped = Pattern.quote(objects[0].toString() + "")

        val chars = "[^$escaped]"

        return fun(wrapper: FunctionKeeper): Any? {
            if (LIVRUtils.isNoValue(wrapper.value) || wrapper.value.javaClass == JSONObject::class.java) return ""
            wrapper.fieldResultArr.add((wrapper.value.toString() + "").replace(chars.toRegex(), ""))

            return ""
        }
    }


}
