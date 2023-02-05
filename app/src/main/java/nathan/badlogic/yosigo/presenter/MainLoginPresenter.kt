package nathan.badlogic.yosigo.presenter

import nathan.badlogic.yosigo.model.MainLoginAsServiceModel
import nathan.badlogic.yosigo.view.interfaces.MainLoginView

class MainLoginPresenter(private var view:MainLoginView) {

    private val model=MainLoginAsServiceModel(this)

     fun initLogin(email:String,passwd:String){
         model.login(email,passwd)
     }

    fun notifyViewShowMessage(msgInfo: String) {
        view.notifyViewShowMessage(msgInfo)
    }

    fun notifyViewStartSpinkitLoading() {
        view.notifyViewStartSpinkitLoading()
    }

    fun notifyViewStopSpinkitLoading() {
        view.notifyViewStopSpinkitLoading()
    }

    fun notifyViewSuccessfulLogin(businessName:String,businessObjectId:String,businessAddress:String) {
        view.notifyViewSuccessfulLogin(businessName,businessObjectId,businessAddress)
    }
}