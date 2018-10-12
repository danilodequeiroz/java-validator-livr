package livr;


import kotlin.jvm.functions.Function1;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by vladislavbaluk on 10/3/2017.
 */
public class FunctionKeeper {
    public FunctionKeeper(Map args, Function1<FunctionKeeper, Object> function) {
        this.args = args;
        this.function = function;
    }

    private Object value;
    private Map args;
    private Function1<FunctionKeeper, Object> function;
    private List<Object> fieldResultArr = new ArrayList<>();

    public Map getArgs() {
        return args;
    }

    public void setArgs(Map args) {
        this.args = args;
    }

    public Function1<FunctionKeeper, Object> getFunction() {
        return function;
    }

    public void setFunction(Function1<FunctionKeeper, Object> function) {
        this.function = function;
    }

    public List<Object> getFieldResultArr() {
        return fieldResultArr;
    }

    public void setFieldResultArr(List<Object> fieldResultArr) {
        this.fieldResultArr = fieldResultArr;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
