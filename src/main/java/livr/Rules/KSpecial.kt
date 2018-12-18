package livr.Rules

import livr.FunctionKeeper
import livr.LIVRUtils
import org.apache.commons.lang3.math.NumberUtils
import org.json.simple.JSONArray
import java.math.BigDecimal
import java.util.regex.Pattern

/**
 * Created by vladislavbaluk on 9/28/2017.
 */
object KSpecial {

    val email = fun(objects: List<Any>): Function1<FunctionKeeper, Any?>? {
        val VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE)
        return fun(wrapper: FunctionKeeper): Any? {
            if (LIVRUtils.isNoValue(wrapper.value)) return ""
            if (!LIVRUtils.isPrimitiveValue(wrapper.value)) return "FORMAT_ERROR"

            val value = wrapper.value.toString() + ""

            if (!VALID_EMAIL_ADDRESS_REGEX.matcher(value).matches()) return "WRONG_EMAIL"

            wrapper.fieldResultArr.add(value)
            return ""
        }
    }

    val equal_to_field = fun(objects: List<Any>): Function1<FunctionKeeper, Any?>? {
        val field = objects[0].toString() + ""
        return fun(wrapper: FunctionKeeper): Any? {
            if (LIVRUtils.isNoValue(wrapper.value)) return ""
            if (!LIVRUtils.isPrimitiveValue(wrapper.value)) return "FORMAT_ERROR"
            val value = wrapper.value.toString() + ""

            return if (value != wrapper.args!![field]) "FIELDS_NOT_EQUAL" else ""
        }
    }

    val url = fun(objects: List<Any>): Function1<FunctionKeeper, Any?>? {
        val pattern = Pattern.compile("(?i)^(?:(?:https?|ftp)://)(?:\\S+(?::\\S*)?@)?(?:(?!(?:10)(?:\\.\\d{1,3}){3})(?!(?:169\\.254|192\\.168)(?:\\.\\d{1,3}){2})(?!172\\\\.(?:1[6-9]|2\\d|3[0-1])(?:\\.\\d{1,3}){2})(?:[1-9]\\d?|1\\d\\d|2[01]\\d|22[0-3])(?:\\.(?:1?\\d{1,2}|2[0-4]\\d|25[0-5])){2}(?:\\.(?:[1-9]\\d?|1\\d\\d|2[0-4]\\d|25[0-4]))|(?:(?:[a-z\\\\u00a1-\\uffff0-9]-*)*[a-z\\u00a1-\\uffff0-9]+)(?:\\.(?:[a-z\\u00a1-\\uffff0-9]-*)*[a-z\\u00a1-\\uffff0-9]+)*(?:\\.(?:[a-z\\u00a1-\\uffff]{2,}))\\.?)(?::\\d{2,5})?(?:[/?#]\\S*)?$", Pattern.CASE_INSENSITIVE)
        return fun(wrapper: FunctionKeeper): Any? {
            if (LIVRUtils.isNoValue(wrapper.value)) return ""
            if (!LIVRUtils.isPrimitiveValue(wrapper.value)) return "FORMAT_ERROR"
            val value = wrapper.value.toString() + ""

            return if (value.length < 2083 && pattern.matcher(value).matches()) "" else "WRONG_URL"
        }
    }


    var iso_date = fun (objects: List<Any>): Function1<FunctionKeeper, Any> {
        return fun(wrapper: FunctionKeeper) : Any{
            if (LIVRUtils.isNoValue(wrapper.value)) return ""
            if (!LIVRUtils.isPrimitiveValue(wrapper.value)) return "FORMAT_ERROR"
            val value = wrapper.value.toString() + ""

            return if (value.matches("^([0-9]{4})(-?)(1[0-2]|0[1-9])\\2(3[01]|0[1-9]|[12][0-9])$".toRegex())) {
                ""
            } else "WRONG_DATE"
        }
    }

}