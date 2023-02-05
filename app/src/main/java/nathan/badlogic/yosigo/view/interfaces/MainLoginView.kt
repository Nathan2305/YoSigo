package nathan.badlogic.yosigo.view.interfaces

interface MainLoginView {
    abstract fun notifyViewShowMessage(msgInfo: String)
    fun notifyViewStartSpinkitLoading()
    fun notifyViewStopSpinkitLoading()
    fun notifyViewSuccessfulLogin(businessName:String,businessObjectId:String,businessAddress:String)
}