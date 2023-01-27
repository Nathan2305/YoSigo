package nathan.badlogic.yosigo.model

import com.backendless.Backendless
import com.backendless.BackendlessUser
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import nathan.badlogic.yosigo.Utils.Constants
import nathan.badlogic.yosigo.presenter.RegisterAsServicePresenter

class RegisterAsServiceModel(private val presenter: RegisterAsServicePresenter) {

    fun initRegister(
        businessName: String,
        txtEmail: String,
        txtPassword: String,
        txtPasswordAgain: String
    ) {
        if (businessName.isNotEmpty() and txtEmail.isNotEmpty() and txtPassword.isNotEmpty() and txtPasswordAgain.isNotEmpty()) {
            if (txtPassword == txtPasswordAgain) {
                presenter.notifyViewShowLoading()
                val userAsService = BackendlessUser()
                userAsService.email = txtEmail
                userAsService.password = txtPassword
                userAsService.setProperty(
                    Constants.USER_AS_SERVICE_COLUMN_BUSINESS_NAME,
                    businessName
                )
                Backendless.UserService.register(userAsService,
                    object : AsyncCallback<BackendlessUser> {
                        override fun handleResponse(response: BackendlessUser?) {
                            presenter.notifyViewStopLoading()
                            presenter.notifyViewSuccessfulRegister()
                        }

                        override fun handleFault(fault: BackendlessFault?) {
                            val msg = "Error registrando empresa: ${fault!!.message}"
                            presenter.notifyViewStopLoading()
                            presenter.notifyViewShowMessage(msg)
                        }

                    })
            } else {
                val msg = "Las contraseñas no coinciden"
                presenter.notifyViewShowMessage(msg)
            }
        } else {
            val msg = "Debe llenar todos los campos"
            presenter.notifyViewShowMessage(msg)
        }
    }
}