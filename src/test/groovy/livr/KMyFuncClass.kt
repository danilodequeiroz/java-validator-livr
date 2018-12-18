package livr

import org.json.simple.JSONArray
import org.json.simple.JSONObject
import java.util.function.Function

/**
 * Created by vladislavbaluk on 10/19/2017.
 */
object KMyFuncClass {

    var my_trim = fun(objects: List<Any>): Function1<FunctionKeeper, Any> {
        return fun(wrapper: FunctionKeeper): Any {
            if (LIVRUtils.isNoValue(wrapper.value) || wrapper.value!!.javaClass == JSONObject::class.java) return ""
            wrapper.fieldResultArr.add((wrapper.value).toString().trim({ it <= ' ' }))

            return ""
        }
    }

    var my_lc = fun(objects: List<Any>): Function1<FunctionKeeper, Any> {
        return fun(wrapper: FunctionKeeper): Any {
            if (LIVRUtils.isNoValue(wrapper.value) || wrapper.value!!.javaClass == JSONObject::class.java) return ""
            wrapper.fieldResultArr.add((wrapper.value).toString().toLowerCase())

            return ""
        }
    }

    var my_ucfirst = fun(objects: List<Any>): Function1<FunctionKeeper, Any> {
        return fun(wrapper: FunctionKeeper): Any {
            if (LIVRUtils.isNoValue(wrapper.value) || wrapper.value!!.javaClass == JSONObject::class.java) return ""
            wrapper.fieldResultArr.add((wrapper.value).toString().substring(0, 1).toUpperCase() + (wrapper.value.toString() + "").substring(1))

            return ""
        }
    }

    fun patchRule(ruleName: String, ruleBuilder: Function1<Any?, Any?>): Function1<FunctionKeeper, Any?> {
        val bla = fun (arguments :Any) : Function1<FunctionKeeper, Any?>  {

            val ruleValidator = ruleBuilder.invoke(arguments) as Function1<FunctionKeeper, Any?>
            var ruleArgs1: Any? = null
            if ((arguments as List<Any>).size == 2)
                ruleArgs1 = arguments[0]
            val ruleArgs = JSONArray()
            ruleArgs1?.apply {
                ruleArgs.add(this)
            }

             val bla2 = fun(q : FunctionKeeper) : Any? {
                val errorCode = ruleValidator.apply { this.invoke(q) }
                if (errorCode != null) {
                    val rule = JSONObject()
                    rule[ruleName] = ruleArgs
                    val json = JSONObject()
                    json["code"] = errorCode
                    json["rule"] = rule
                    return json
                }
                return ""
            }
            return bla2
        }
        return bla
    }
}
