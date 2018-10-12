package livr

import livr.Rules.*

import java.util.HashMap

/**
 * Created by vladislavbaluk on 9/29/2017.
 */
object KLIVR {


    fun getRules(): MutableMap<String, Function1<*, *>> {
        val rules: MutableMap<String, Function1<*, *>> = HashMap()
        rules["required"] = KCommonRules().required
        rules["any_object"] = CommonRules.any_object
        rules["not_empty"] = CommonRules.not_empty
        rules["not_empty_list"] = CommonRules.not_empty_list

        rules["string"] = StringRules.string
        rules["eq"] = StringRules.eq
        rules["one_of"] = StringRules.one_of
        rules["max_length"] = StringRules.max_length
        rules["min_length"] = StringRules.min_length
        rules["length_equal"] = StringRules.length_equal
        rules["length_between"] = StringRules.length_between
        rules["like"] = StringRules.like

        rules["integer"] = Numeric.integer
        rules["positive_integer"] = Numeric.positive_integer
        rules["decimal"] = Numeric.decimal
        rules["positive_decimal"] = Numeric.positive_decimal
        rules["max_number"] = Numeric.max_number
        rules["min_number"] = Numeric.min_number
        rules["number_between"] = Numeric.number_between

        rules["email"] = Special.email
        rules["equal_to_field"] = Special.equal_to_field
        rules["iso_date"] = Special.iso_date
        rules["url"] = Special.url

        rules["nested_object"] = Meta.nested_object
        rules["list_of"] = Meta.list_of
        rules["list_of_objects"] = Meta.list_of_objects
        rules["list_of_different_objects"] = Meta.list_of_different_objects
        rules["variable_object"] = Meta.variable_object
        rules["or"] = Meta.or

        rules["default"] = Modifiers.default1
        rules["to_lc"] = Modifiers.to_lc
        rules["to_uc"] = Modifiers.to_uc
        rules["trim"] = Modifiers.trim
        rules["remove"] = Modifiers.remove
        rules["leave_only"] = Modifiers.leave_only
        return rules
    }


}
