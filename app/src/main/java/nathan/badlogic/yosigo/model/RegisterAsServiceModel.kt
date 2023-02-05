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
        txtAddress: String,
        txtPassword: String,
        txtPasswordAgain: String
    ) {
        if (businessName.isNotEmpty() and txtEmail.isNotEmpty() and txtAddress.isNotEmpty() and txtPassword.isNotEmpty() and txtPasswordAgain.isNotEmpty()) {
            if (txtPassword == txtPasswordAgain) {
                presenter.notifyViewShowLoading()
                val userAsService = BackendlessUser()
                userAsService.email = txtEmail
                userAsService.password = txtPassword
                userAsService.setProperty(Constants.BACKEND_COLUMN_BUSINESS_NAME, businessName)
                userAsService.setProperty(Constants.BACKEND_COLUMN_ADDRESS, txtAddress)
                Backendless.UserService.register(
                    userAsService,
                    object : AsyncCallback<BackendlessUser> {
                        override fun handleResponse(response: BackendlessUser) {
                            presenter.notifyViewStopLoading()
                            val msg =
                                "Empresa ${response.properties[Constants.BACKEND_COLUMN_BUSINESS_NAME].toString()} registrada correctamente"
                            presenter.notifyViewSuccessfulRegister(msg)
                        }

                        override fun handleFault(fault: BackendlessFault) {
                            //1155 duplicate businessName
                            val errorCode = fault.code
                            if (errorCode == "1155") {
                                val msg="Ya existe una empresa con el nombre $businessName, si la empresa tiene más de 1 sede, te recomendamos agregar alguna descripción junto al nombre de la empresa para diferenciarla de la otra sede"
                                presenter.notifyViewStopLoading()
                                presenter.notifyViewShowDialogMessage(msg)
                            } else {
                                val msg = "Error registrando empresa: ${fault.message}"
                                presenter.notifyViewStopLoading()
                                presenter.notifyViewShowMessage(msg)
                            }
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