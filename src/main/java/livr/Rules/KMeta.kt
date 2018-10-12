package livr.Rules

import com.google.common.collect.Lists
import livr.FunctionKeeper
import livr.LIVRUtils
import livr.Validator
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.json.simple.parser.ParseException

import java.io.IOException
import java.util.*

/**
 * Created by vladislavbaluk on 9/28/2017.
 */
object KMeta {
    val nested_object = fun (objects: List<Any>): Function1<FunctionKeeper, Any?>? {
        try {
            val validator = Validator(objects[1] as Map<String, Function1<*, *>>).init(objects[0] as JSONObject, false).prepare()
            return fun(wrapper: FunctionKeeper) : Any?{
                if (LIVRUtils.isNoValue(wrapper.value)) return ""
                if (!LIVRUtils.isObject(wrapper.value)) return "FORMAT_ERROR"

                try {
                    val result = validator.validate(wrapper.value)

                    if (result != null) {
                        wrapper.fieldResultArr.add(result)
                        return ""
                    } else {
                        return validator.errors
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }

                return null
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return null
    }

    val list_of = fun (objects: List<Any>): Function1<FunctionKeeper, Any?>? {
        try {
            val field = JSONObject()
            val array = JSONArray()
            array.addAll(Lists.newArrayList(objects[0]))
            if (objects[0].javaClass == JSONArray::class.java) {
                field["field"] = objects[0]
            } else {
                field["field"] = array
            }
            val validator = Validator(objects[1] as Map<String, Function1<*, *>>).init(field, false).prepare()

            return fun(wrapper: FunctionKeeper) : Any?{
                if (LIVRUtils.isNoValue(wrapper.value)) return ""
                if (wrapper.value !is JSONArray) return "FORMAT_ERROR"

                try {
                    var hasErrors = false
                    val results = JSONArray()
                    val errors = JSONArray()
                    val arr = (wrapper.value as JSONArray).toTypedArray()
                    for (value in arr) {
                        val fieldv = JSONObject()
                        fieldv["field"] = value
                        val result = validator.validate(fieldv)
                        if (result != null) {
                            results.add(result["field"])
                            errors.add(null)
                        } else {
                            hasErrors = true
                            errors.add(validator.errors["field"])
                            results.add(null)
                        }

                    }
                    if (hasErrors) {
                        return errors
                    } else {
                        wrapper.fieldResultArr.add(results)
                        return ""
                    }

                } catch (e: IOException) {
                    e.printStackTrace()
                } catch (e: ParseException) {
                    e.printStackTrace()
                }

                return null
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return null
    }

    val list_of_objects = fun (objects: List<Any>): Function1<FunctionKeeper, Any?>? {
        try {
            val validator = Validator(objects[1] as Map<String, Function1<*, *>>).init(objects[0] as JSONObject, false).prepare()

            return fun(wrapper: FunctionKeeper) : Any?{
                if (LIVRUtils.isNoValue(wrapper.value)) return ""
                if (wrapper.value !is JSONArray) return "FORMAT_ERROR"

                try {
                    var hasErrors = false
                    val results = JSONArray()
                    val errors = JSONArray()
                    for (value in (wrapper.value as JSONArray).toTypedArray()) {

                        if (!LIVRUtils.isObject(value)) {
                            errors.add("FORMAT_ERROR")
                            hasErrors = true
                            continue
                        }
                        val result = validator.validate(value)

                        if (result != null) {
                            results.add(result)
                            errors.add(null)
                        } else {
                            hasErrors = true
                            errors.add(validator.errors)
                            results.add(null)
                        }
                    }
                    if (hasErrors) {
                        return errors
                    } else {
                        wrapper.fieldResultArr.add(results)
                        return ""
                    }

                } catch (e: IOException) {
                    e.printStackTrace()
                }

                return null
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return null
    }

    val list_of_different_objects = fun (objects: List<Any>): Function1<FunctionKeeper, Any?>? {
        try {
            val validators : HashMap<Any?, Validator> = hashMapOf()
            val it = (objects[0] as JSONArray).iterator()
            val selectorField = it.next()
            val values = it.next() as JSONObject
            for (key in values.keys) {
                val selectorValue = values[key] as JSONObject

                val validator = Validator(objects[1] as Map<String, Function1<*, *>>).init(selectorValue, false)
                        .registerRules(objects[1] as Map<String, Function1<*, *>>)
                        .prepare()

                validators[key] = validator
            }
            return fun(wrapper: FunctionKeeper) : Any?{
                if (LIVRUtils.isNoValue(wrapper.value)) return ""
                if (wrapper.value !is JSONArray) return "FORMAT_ERROR"

                try {
                    var hasErrors = false
                    val results = JSONArray()
                    val errors = JSONArray()

                    for (value in (wrapper.value as JSONArray).toTypedArray()) {


                        if (!LIVRUtils.isObject(value) || (value as JSONObject)[selectorField] == null || validators[value[selectorField]] == null) {
                            errors.add("FORMAT_ERROR")
                            continue
                        }

                        val validator = validators[value[selectorField]]

                        val result = validator?.validate(value)
                        if (result != null) {
                            results.add(result)
                            errors.add(null)
                        } else {
                            hasErrors = true
                            errors.add(validator?.getErrors())
                            results.add(null)
                        }
                    }
                    if (hasErrors) {
                        return errors
                    } else {
                        wrapper.fieldResultArr.add(results)
                        return ""
                    }

                } catch (e: IOException) {
                    e.printStackTrace()
                } catch (e: ParseException) {
                    e.printStackTrace()
                }

                return null
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return null
    }

    val variable_object = fun (objects: List<Any>): Function1<FunctionKeeper, Any?>? {
        try {
            val validators = HashMap<Any?, Validator>()
            val it = (objects[0] as JSONArray).iterator()
            val selectorField = it.next()
            val values = it.next() as JSONObject
            for (key in values.keys) {
                val selectorValue = values[key] as JSONObject

                val validator = Validator(objects[1] as Map<String, Function1<*, *>>).init(selectorValue, false)
                        .registerRules(objects[1] as Map<String, Function1<*, *>>)
                        .prepare()

                validators[key] = validator
            }
            return fun(wrapper: FunctionKeeper) : Any?{
                if (LIVRUtils.isNoValue(wrapper.value)) return ""

                try {
                    val value = wrapper.value
                    if (!LIVRUtils.isObject(value) || (value as JSONObject)[selectorField] == null || validators[value[selectorField]] == null) {
                        return "FORMAT_ERROR"
                    }

                    val validator = validators[value[selectorField]]

                    val result = validator?.validate(value)
                    if (result != null) {
                        wrapper.fieldResultArr.add(result)
                        return ""
                    } else {
                        return validator?.getErrors()
                    }

                } catch (e: IOException) {
                    e.printStackTrace()
                }

                return null
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return null
    }

    val or = fun (objects: List<Any>): Function1<FunctionKeeper, Any?>? {
        try {
            val validators = ArrayList<Validator>()
            for (entry in (objects[0] as JSONArray).toTypedArray()) {
                val field = JSONObject()
                field["field"] = entry
                val validator = Validator(objects[1] as Map<String, Function1<*, *>>).init(field, false).prepare()
                validators.add(validator)
            }
            return fun(wrapper: FunctionKeeper) : Any?{
                if (LIVRUtils.isNoValue(wrapper.value)) return ""
                try {
                    val value = wrapper.value
                    var lastError: Any? = null
                    for (validator in validators) {
                        val valValue = JSONObject()
                        valValue["field"] = value
                        val result = validator.validate(valValue)
                        if (result != null) {
                            wrapper.fieldResultArr.add(result["field"])
                            return ""
                        } else {
                            lastError = validator.errors["field"]
                        }
                    }
                    if (lastError != null) {
                        return lastError
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                return null
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return null
    }
}