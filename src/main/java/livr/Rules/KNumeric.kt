package livr.Rules

import livr.FunctionKeeper
import livr.LIVRUtils
import livr.Validator
import org.apache.commons.lang3.math.NumberUtils
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import java.io.IOException

import java.math.BigDecimal

/**
 * Created by vladislavbaluk on 9/28/2017.
 */
object KNumeric {
    var integer = fun(objects: List<Any>): Function1<FunctionKeeper, Any> {
        return fun(wrapper: FunctionKeeper): Any {
            if (LIVRUtils.isNoValue(wrapper.value)) return ""
            if (!LIVRUtils.isPrimitiveValue(wrapper.value)) return "FORMAT_ERROR"
            if (!LIVRUtils.looksLikeNumber(wrapper.value)) return "NOT_INTEGER"

            if (!LIVRUtils.isInteger(wrapper.value)) return "NOT_INTEGER"
            wrapper.fieldResultArr.add(NumberUtils.createNumber(wrapper.value.toString() + ""))
            return ""
        }
    }

    var positive_integer = fun(objects: List<Any>): Function1<FunctionKeeper, Any> {
        return fun(wrapper: FunctionKeeper): Any {
            if (LIVRUtils.isNoValue(wrapper.value)) return ""
            if (!LIVRUtils.isPrimitiveValue(wrapper.value)) return "FORMAT_ERROR"
            if (!LIVRUtils.looksLikeNumber(wrapper.value)) return "NOT_POSITIVE_INTEGER"

            if (!LIVRUtils.isInteger(wrapper.value) || java.lang.Long.valueOf(wrapper.value.toString() + "") < 1)
                return "NOT_POSITIVE_INTEGER"
            wrapper.fieldResultArr.add(NumberUtils.createNumber(wrapper.value.toString() + ""))
            return ""
        }
    }


    var decimal = fun(objects: List<Any>): Function1<FunctionKeeper, Any> {
        return fun(wrapper: FunctionKeeper): Any {
            if (LIVRUtils.isNoValue(wrapper.value)) return ""
            if (!LIVRUtils.isPrimitiveValue(wrapper.value)) return "FORMAT_ERROR"
            if (!LIVRUtils.looksLikeNumber(wrapper.value)) return "NOT_DECIMAL"

            if (!LIVRUtils.isDecimal(wrapper.value)) return "NOT_DECIMAL"
            wrapper.fieldResultArr.add(NumberUtils.createNumber(wrapper.value.toString() + ""))
            return ""
        }
    }


    var positive_decimal = fun(objects: List<Any>): Function1<FunctionKeeper, Any> {
        return fun(wrapper: FunctionKeeper): Any {
            if (LIVRUtils.isNoValue(wrapper.value)) return ""
            if (!LIVRUtils.isPrimitiveValue(wrapper.value)) return "FORMAT_ERROR"
            if (!LIVRUtils.looksLikeNumber(wrapper.value)) return "NOT_POSITIVE_DECIMAL"

            if (!LIVRUtils.isDecimal(wrapper.value) || java.lang.Double.valueOf(wrapper.value.toString() + "") < 0)
                return "NOT_POSITIVE_DECIMAL"
            wrapper.fieldResultArr.add(NumberUtils.createNumber(wrapper.value.toString() + ""))
            return ""
        }
    }

    val max_number = fun(objects: List<Any>): Function1<FunctionKeeper, Any?>? {
        val maxNumber = java.lang.Long.valueOf(objects[0].toString() + "")
        return fun(wrapper: FunctionKeeper): Any? {
            if (LIVRUtils.isNoValue(wrapper.value)) return ""
            if (!LIVRUtils.isPrimitiveValue(wrapper.value)) return "FORMAT_ERROR"
            if (!LIVRUtils.looksLikeNumber(wrapper.value)) return "NOT_NUMBER"

            val value = java.lang.Double.valueOf(wrapper.value.toString() + "")

            if (value > maxNumber) return "TOO_HIGH"

            wrapper.fieldResultArr.add(NumberUtils.createNumber(wrapper.value.toString() + ""))
            return ""
        }
    }

    val min_number = fun(objects: List<Any>): Function1<FunctionKeeper, Any?>? {
        val min_number = java.lang.Long.valueOf(objects[0].toString() + "")
        return fun(wrapper: FunctionKeeper): Any? {
            if (LIVRUtils.isNoValue(wrapper.value)) return ""
            if (!LIVRUtils.isPrimitiveValue(wrapper.value)) return "FORMAT_ERROR"
            if (!LIVRUtils.looksLikeNumber(wrapper.value)) return "NOT_NUMBER"
            val value = java.lang.Double.valueOf(wrapper.value.toString() + "")

            if (value < min_number) return "TOO_LOW"

            wrapper.fieldResultArr.add(NumberUtils.createNumber(wrapper.value.toString() + ""))
            return ""
        }
    }

    val number_between = fun(objects: List<Any>): Function1<FunctionKeeper, Any?>? {
        val it = (objects[0] as JSONArray).iterator()
        val minNumber = java.lang.Long.valueOf(it.next().toString() + "")
        val maxNumber = java.lang.Long.valueOf(it.next().toString() + "")
        return fun(wrapper: FunctionKeeper): Any? {
            if (LIVRUtils.isNoValue(wrapper.value)) return ""
            if (!LIVRUtils.isPrimitiveValue(wrapper.value)) return "FORMAT_ERROR"
            if (!LIVRUtils.looksLikeNumber(wrapper.value)) return "NOT_NUMBER"
            val value = NumberUtils.createBigDecimal(wrapper.value.toString() + "")
            if (value.compareTo(BigDecimal.valueOf(minNumber)) < 0) return "TOO_LOW"
            if (value.compareTo(BigDecimal.valueOf(maxNumber)) > 0) return "TOO_HIGH"

            wrapper.fieldResultArr.add(NumberUtils.createNumber(wrapper.value.toString() + ""))
            return ""
        }
    }

}
