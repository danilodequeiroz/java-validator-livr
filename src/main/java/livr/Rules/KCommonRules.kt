package livr.Rules

import livr.FunctionKeeper
import livr.LIVRUtils
import org.json.simple.JSONArray

/**
 * Created by vladislavbaluk on 9/28/2017.
 */
class KCommonRules {


    var required = fun (objects: List<Any>): Function1<FunctionKeeper, Any> {
        return fun(wrapper: FunctionKeeper) : Any{
            return if (LIVRUtils.isNoValue(wrapper.value)) {
                "REQUIRED"
            } else ""
        }
    }

    var not_empty = fun (objects: List<Any>): Function1<FunctionKeeper, Any> {
        return fun(wrapper: FunctionKeeper) : Any{
            return if (wrapper.value != null && wrapper.value == "") {
                "CANNOT_BE_EMPTY"
            } else ""
        }
    }

    var not_empty_list = fun (objects: List<Any>): Function1<FunctionKeeper, Any> {
        return fun(wrapper: FunctionKeeper) : Any{
            if (LIVRUtils.isNoValue(wrapper.value)) return "CANNOT_BE_EMPTY"
            if (wrapper.value !is JSONArray) return "FORMAT_ERROR"
            return if ((wrapper.value as JSONArray).size == 0) "CANNOT_BE_EMPTY" else ""

        }
    }

    var any_object = fun (objects: List<Any>): Function1<FunctionKeeper, Any> {
        return fun(wrapper: FunctionKeeper) : Any{
            if (LIVRUtils.isNoValue(wrapper.value)) return ""

            return if (!LIVRUtils.isObject(wrapper.value)) {
                "FORMAT_ERROR"
            } else ""
        }
    }



}
