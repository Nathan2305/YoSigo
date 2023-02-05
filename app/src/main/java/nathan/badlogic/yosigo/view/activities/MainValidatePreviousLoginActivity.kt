package nathan.badlogic.yosigo.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.backendless.Backendless
import com.backendless.BackendlessUser
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import com.backendless.persistence.local.UserIdStorageFactory
import nathan.badlogic.yosigo.R
import nathan.badlogic.yosigo.Utils.Constants
import nathan.badlogic.yosigo.Utils.StaticMethods

class MainValidatePreviousLoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_validate_previous_login)
        initBackendless()
        validatePreviousLogin()
    }

    private fun validatePreviousLogin() {
        Backendless.UserService.isValidLogin(object : AsyncCallback<Boolean> {
            override fun handleResponse(response: Boolean?) {
                if (response == true) {
                    val currentUserObjectId = UserIdStorageFactory.instance().storage.get()
                    Backendless.UserService.findById(currentUserObjectId,
                        object : AsyncCallback<BackendlessUser> {
                            override fun handleResponse(userFound: BackendlessUser) {
                                val businessName =
                                    userFound.properties[Constants.BACKEND_COLUMN_BUSINESS_NAME].toString()
                                val businessObjectId =
                                    userFound.properties[Constants.BACKEND_COLUMN_OBJECT_ID].toString()
                                val businessAddress =
                                    userFound.properties[Constants.BACKEND_COLUMN_ADDRESS].toString()
                                val intent = Intent(
                                    applicationContext,
                                    MainProfileActivity::class.java
                                ).apply {
                                    putExtra(Constants.BACKEND_COLUMN_BUSINESS_NAME, businessName)
                                    putExtra(Constants.BACKEND_COLUMN_OBJECT_ID, businessObjectId)
                                    putExtra(Constants.BACKEND_COLUMN_ADDRESS, businessAddress)
                                    flags =
                                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                }
                                startActivity(intent)
                            }

                            override fun handleFault(fault: BackendlessFault?) {
                                val msgError="Error validando sesión:${fault!!.message}"
                                StaticMethods.showToast(applicationContext,msgError)
                                startLoginActivity()
                            }

                        }
                    )
                } else {
                    startLoginActivity()
                }

            }

            override fun handleFault(fault: BackendlessFault?) {
                startLoginActivity()
            }

        })
    }

    private fun startLoginActivity() {
        val intent = Intent(applicationContext, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
    }

    private fun initBackendless() {
        Backendless.initApp(this, Constants.APPLICATION_ID, Constants.ANDROID_API_KEY)
    }

}