package nathan.badlogic.yosigo.view.interfaces

interface MainLoginView {
    abstract fun notifyViewShowMessage(msgInfo: String)
    fun notifyViewStartSpinkitLoading()
    fun notifyViewStopSpinkitLoading()
}