package livr


import java.util.ArrayList


/**
 * Created by vladislavbaluk on 10/3/2017.
 */
class FunctionKeeper(var args: Map<*, *>?, var function: Function1<FunctionKeeper, Any>?) {

    var value: Any? = null
    var fieldResultArr: MutableList<Any?> = ArrayList()
}
