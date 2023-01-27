package nathan.badlogic.yosigo.view.interfaces

interface RegisterAsServiceView {
    abstract fun notifyViewShowMessage(msg: String)
    fun notifyViewShowLoading()
    fun notifyViewStopLoading()
    fun notifyViewSuccessfulRegister()
}