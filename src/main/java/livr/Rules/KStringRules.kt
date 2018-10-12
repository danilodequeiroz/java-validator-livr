package livr.Rules

import com.google.common.collect.Lists
import livr.FunctionKeeper
import livr.LIVRUtils
import org.json.simple.JSONArray
import java.util.regex.Pattern
import java.util.stream.Collectors

/**
 * Created by vladislavbaluk on 9/28/2017.
 */
object KStringRules {
    val string = fun(objects: List<Any>): Function1<FunctionKeeper, Any?>? {
        return fun(wrapper: FunctionKeeper): Any? {
            if (wrapper.value == null || wrapper.value.toString() + "" == "") return ""
            if (!LIVRUtils.isPrimitiveValue(wrapper.value)) return "FORMAT_ERROR"

            val value = wrapper.value.toString() + ""
            wrapper.fieldResultArr.add(value)
            return ""
        }
    }


    var eq = fun (objects: List<Any>): Function1<FunctionKeeper, Any> {
        val allowedValue = objects[0]

        return fun(wrapper: FunctionKeeper) : Any{
            if (wrapper.value == null || wrapper.value.toString() + "" == "") return ""
            if (!LIVRUtils.isPrimitiveValue(wrapper.value)) return "FORMAT_ERROR"

            val value = wrapper.value.toString() + ""
            if (value == allowedValue.toString() + "") {
                wrapper.fieldResultArr.add(allowedValue)
                return ""
            }

            return "NOT_ALLOWED_VALUE"
        }
    }

    var one_of = fun (objects: List<Any>): Function1<FunctionKeeper, Any> {
        val allowedValues = Lists.newArrayList<Any>(*(objects[0] as JSONArray).toTypedArray())

        return fun(wrapper: FunctionKeeper) : Any{
            val allowedStrValues :List<String> = allowedValues.map { obj -> obj.toString() }
            if (wrapper.value == null || wrapper.value.toString() + "" == "") return ""
            if (!LIVRUtils.isPrimitiveValue(wrapper.value)) return "FORMAT_ERROR"

            val value = wrapper.value.toString() + ""
            if (allowedStrValues.contains(value)) {
                wrapper.fieldResultArr.add(allowedValues[allowedStrValues.indexOf(value)])
                return ""
            }

            return "NOT_ALLOWED_VALUE"
        }
    }

    var max_length = fun (objects: List<Any>): Function1<FunctionKeeper, Any> {
        val maxLength = java.lang.Long.valueOf(objects[0].toString() + "")

        return fun(wrapper: FunctionKeeper) : Any{
            if (wrapper.value == null || wrapper.value.toString() + "" == "") return ""
            if (!LIVRUtils.isPrimitiveValue(wrapper.value)) return "FORMAT_ERROR"

            val value = wrapper.value.toString() + ""
            if (value.length > maxLength) return "TOO_LONG"
            wrapper.fieldResultArr.add(value)
            return ""
        }
    }

    var min_length = fun (objects: List<Any>): Function1<FunctionKeeper, Any> {
        val minLength = java.lang.Long.valueOf(objects[0].toString() + "")

        return fun(wrapper: FunctionKeeper) : Any{
            if (wrapper.value == null || wrapper.value.toString() + "" == "") return ""
            if (!LIVRUtils.isPrimitiveValue(wrapper.value)) return "FORMAT_ERROR"

            val value = wrapper.value.toString() + ""
            if (value.length < minLength) return "TOO_SHORT"
            wrapper.fieldResultArr.add(value)
            return ""
        }
    }

    var length_equal = fun (objects: List<Any>): Function1<FunctionKeeper, Any> {
        val length = java.lang.Long.valueOf(objects[0].toString() + "")

        return fun(wrapper: FunctionKeeper) : Any{
            if (wrapper.value == null || wrapper.value.toString() + "" == "") return ""
            if (!LIVRUtils.isPrimitiveValue(wrapper.value)) return "FORMAT_ERROR"

            val value = wrapper.value.toString() + ""

            if (value.length < length) return "TOO_SHORT"
            if (value.length > length) return "TOO_LONG"

            wrapper.fieldResultArr.add(value)
            return ""
        }
    }

    var length_between = fun (objects: List<Any>): Function1<FunctionKeeper, Any> {
        val it = (objects[0] as JSONArray).iterator()
        val minLength = java.lang.Long.valueOf(it.next().toString() + "")
        val maxLength = java.lang.Long.valueOf(it.next().toString() + "")

        return fun(wrapper: FunctionKeeper) : Any{
            if (wrapper.value == null || wrapper.value.toString() + "" == "") return ""
            if (!LIVRUtils.isPrimitiveValue(wrapper.value)) return "FORMAT_ERROR"

            val value = wrapper.value.toString() + ""
            if (value.length < minLength) return "TOO_SHORT"
            if (value.length > maxLength) return "TOO_LONG"

            wrapper.fieldResultArr.add(value)
            return ""
        }
    }


    var like = fun (objects: List<Any>): Function1<FunctionKeeper, Any> {
        val pattern: String
        val isIgnoreCase: Boolean
        if (objects[0].javaClass == JSONArray::class.java) {
            val it = (objects[0] as JSONArray).iterator()
            pattern = it.next() as String
            isIgnoreCase = it.next() == "i"
        } else {
            pattern = objects[0] as String
            isIgnoreCase = false
        }

        return fun(wrapper: FunctionKeeper) : Any{
            if (wrapper.value == null || wrapper.value.toString() + "" == "") return ""
            if (!LIVRUtils.isPrimitiveValue(wrapper.value)) return "FORMAT_ERROR"

            val value = wrapper.value.toString() + ""
            val caseInValue = if (isIgnoreCase) value.toLowerCase() else value
            if (!caseInValue.matches(pattern.toRegex())) return "WRONG_FORMAT"
            wrapper.fieldResultArr.add(value)
            return ""
        }
    }
}