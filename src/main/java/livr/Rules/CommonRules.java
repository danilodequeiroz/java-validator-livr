package livr.Rules;

import kotlin.jvm.functions.Function1;
import livr.FunctionKeeper;
import livr.LIVRUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;
import java.util.function.Function;

/**
 * Created by vladislavbaluk on 9/28/2017.
 */
public class CommonRules {

    public static Function1<List<Object>, Function1> required = objects -> (Function1<FunctionKeeper, Object>) (wrapper) -> {
        if (LIVRUtils.isNoValue(wrapper.getValue())) {
            return "REQUIRED";
        }
        return "";
    };

//    public static Function1<List<Integer>, Function1> requiredL = objects -> (Function1<FunctionKeeper, JSONObject>) (wrapper) -> {
//        if (LIVRUtils.isNoValue(wrapper.getValue())) {
//            return "REQUIRED";
//        }
//        return "";
//    };
//
//    public static Function1<List<Object>, Function1> Krequired = new Function1<List<Object>, Function1>() {
//
//        @Override
//        public Function1 invoke(List<Object> objects) {
//
//        };
//    };

    public static Function1<List<Object>, Function1> not_empty = objects -> (Function1<FunctionKeeper, Object>) (wrapper) -> {
        if (wrapper.getValue() != null && wrapper.getValue().equals("")) {
            return "CANNOT_BE_EMPTY";
        }
        return "";
    };

    public static Function1<List<Object>, Function1> not_empty_list = objects -> (Function1<FunctionKeeper, Object>) (wrapper) -> {
        if (LIVRUtils.isNoValue(wrapper.getValue())) return "CANNOT_BE_EMPTY";
        if (!(wrapper.getValue() instanceof JSONArray)) return "FORMAT_ERROR";
        if (((JSONArray) wrapper.getValue()).size() == 0) return "CANNOT_BE_EMPTY";

        return "";
    };

    public static Function1<List<Object>, Function1> any_object = objects -> (Function1<FunctionKeeper, Object>) (wrapper) -> {
        if (LIVRUtils.isNoValue((wrapper.getValue()))) return "";

        if (!LIVRUtils.isObject(wrapper.getValue())) {
            return "FORMAT_ERROR";
        }
        return "";
    };

}
