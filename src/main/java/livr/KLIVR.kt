package livr

import livr.Rules.*
import java.time.zone.ZoneRulesProvider.getRules

import java.util.HashMap

/**
 * Created by vladislavbaluk on 9/29/2017.
 */
object KLIVR {




        fun validator(): Validator {
            val `val` = Validator()
            `val`.registerDefaultRules(this.getRules())
            return `val`
        }


        fun getRules(): MutableMap<String, Function1<*, *>> {
            val rules: MutableMap<String, Function1<*, *>> = HashMap()
            rules["required"] = KCommonRules.required
            rules["any_object"] = KCommonRules.any_object
            rules["not_empty"] = KCommonRules.not_empty
            rules["not_empty_list"] = KCommonRules.not_empty_list

            rules["string"] = KStringRules.string
            rules["eq"] = KStringRules.eq
            rules["one_of"] = KStringRules.one_of
            rules["max_length"] = KStringRules.max_length
            rules["min_length"] = KStringRules.min_length
            rules["length_equal"] = KStringRules.length_equal
            rules["length_between"] = KStringRules.length_between
            rules["like"] = KStringRules.like

            rules["integer"] = KNumeric.integer
            rules["positive_integer"] = KNumeric.positive_integer
            rules["decimal"] = KNumeric.decimal
            rules["positive_decimal"] = KNumeric.positive_decimal
            rules["max_number"] = KNumeric.max_number
            rules["min_number"] = KNumeric.min_number
            rules["number_between"] = KNumeric.number_between

            rules["email"] = KSpecial.email
            rules["equal_to_field"] = KSpecial.equal_to_field
            rules["iso_date"] = KSpecial.iso_date
            rules["url"] = KSpecial.url

            rules["nested_object"] = KMeta.nested_object
            rules["list_of"] = KMeta.list_of
            rules["list_of_objects"] = KMeta.list_of_objects
            rules["list_of_different_objects"] = KMeta.list_of_different_objects
            rules["variable_object"] = KMeta.variable_object
            rules["or"] = KMeta.or

            rules["default"] = KModifiers.default1
            rules["to_lc"] = KModifiers.to_lc
            rules["to_uc"] = KModifiers.to_uc
            rules["trim"] = KModifiers.trim
            rules["remove"] = KModifiers.remove
            rules["leave_only"] = KModifiers.leave_only
            return rules
        }

        fun registerDefaultRules(rules: MutableMap<String, Function1<*, *>>) {
            rules.putAll(rules)
        }



}
