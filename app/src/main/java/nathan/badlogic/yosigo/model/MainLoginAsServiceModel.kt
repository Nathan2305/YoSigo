package nathan.badlogic.yosigo.model

import com.backendless.Backendless
import com.backendless.BackendlessUser
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import nathan.badlogic.yosigo.Utils.Constants
import nathan.badlogic.yosigo.presenter.MainLoginPresenter


class MainLoginAsServiceModel(private var presenter: MainLoginPresenter) {

    fun login(email: String, passwd: String) {
        if (email.isEmpty() or passwd.isEmpty()) {
            val msgInfo = "Debes llenar todos los datos"
            presenter.notifyViewShowMessage(msgInfo)
        } else
            presenter.notifyViewStartSpinkitLoading()
        Backendless.UserService.login(email, passwd, object : AsyncCallback<BackendlessUser> {
            override fun handleResponse(response: BackendlessUser) {
                val businessName=response.properties[Constants.BACKEND_COLUMN_BUSINESS_NAME].toString()
                val businessObjectId=response.objectId
                val businessAddress=response.properties[Constants.BACKEND_COLUMN_ADDRESS].toString()
                presenter.notifyViewSuccessfulLogin(businessName,businessObjectId,businessAddress)
            }

            override fun handleFault(fault: BackendlessFault?) {
                val msgInfo = "Error iniciando sesión: ${fault!!.message}"
                presenter.notifyViewStopSpinkitLoading()
                presenter.notifyViewShowMessage(msgInfo)
            }
        },true)
    }
}
