package nathan.badlogic.yosigo.presenter

import nathan.badlogic.yosigo.model.RegisterAsServiceModel
import nathan.badlogic.yosigo.view.interfaces.RegisterAsServiceView

class RegisterAsServicePresenter(private var view: RegisterAsServiceView) {
    private val model = RegisterAsServiceModel(this)
    fun initRegister(businessName: String,txtEmail: String,txtPassword: String,txtPasswordAgain: String
    ) {
        model.initRegister(businessName,txtEmail,txtPassword,txtPasswordAgain)
    }

    fun notifyViewShowMessage(msg: String) {
        view.notifyViewShowMessage(msg)
    }

    fun notifyViewShowLoading() {
        view.notifyViewShowLoading()
    }

    fun notifyViewStopLoading() {
        view.notifyViewStopLoading()
    }

    fun notifyViewSuccessfulRegister() {
        view.notifyViewSuccessfulRegister()
    }


}