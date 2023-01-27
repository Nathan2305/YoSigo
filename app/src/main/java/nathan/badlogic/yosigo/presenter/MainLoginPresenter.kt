package nathan.badlogic.yosigo.presenter

import nathan.badlogic.yosigo.model.MainLoginModel
import nathan.badlogic.yosigo.view.interfaces.MainLoginView

class MainLoginPresenter(private var view:MainLoginView) {

    private val model=MainLoginModel(this)

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
}