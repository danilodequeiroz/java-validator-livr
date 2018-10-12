package livr

import org.apache.commons.lang3.math.NumberUtils
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import org.json.simple.parser.ParseException

/**
 * Created by vladislavbaluk on 9/28/2017.
 */
object KLIVRUtils {
    private val parser = JSONParser()

    fun isPrimitiveValue(value: Any): Boolean {
        if (value.javaClass == String::class.java || value.javaClass == Boolean::class.java) return true
        if (value is Number) return true
        return if (value == "true" || value == "false") true else false
    }

    fun looksLikeNumber(value: Any): Boolean {

        return value is Number || NumberUtils.isCreatable(value.toString() + "")
    }

    fun isObject(value: Any): Boolean {
        if (value.javaClass == JSONObject::class.java || value.javaClass == JSONArray::class.java) {
            return true
        }

        try {
            return parser.parse(value.toString()) != null
        } catch (e: ParseException) {
            return false
        }

    }

    fun isEmptyObject(map: Map<String, String>): Boolean {
        return map.isEmpty()
    }

    fun isNoValue(value: Any?): Boolean {
        return value == null || value.toString() + "" == ""
    }

    fun isInteger(value: Any): Boolean {
        return if (value is Long) {
            true
        } else NumberUtils.isDigits(value.toString() + "")

    }

    fun isDecimal(value: Any): Boolean {
        if (value is Double) {
            return true
        }

        try {
            java.lang.Double.valueOf(value.toString() + "")
        } catch (nfe: NumberFormatException) {
            return false
        }

        return true
    }
}
