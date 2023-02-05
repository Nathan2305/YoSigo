package nathan.badlogic.yosigo.view.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.github.ybq.android.spinkit.SpinKitView
import nathan.badlogic.yosigo.R
import nathan.badlogic.yosigo.Utils.Constants.Companion.BACKEND_COLUMN_ADDRESS
import nathan.badlogic.yosigo.Utils.Constants.Companion.BACKEND_COLUMN_BUSINESS_NAME
import nathan.badlogic.yosigo.Utils.Constants.Companion.BACKEND_COLUMN_OBJECT_ID
import nathan.badlogic.yosigo.Utils.StaticMethods
import nathan.badlogic.yosigo.presenter.MainLoginPresenter
import nathan.badlogic.yosigo.view.interfaces.MainLoginView

class LoginActivity : AppCompatActivity(), MainLoginView {

    private lateinit var rootConstraint: ConstraintLayout
    private lateinit var txtEmail: EditText
    private lateinit var txtPasswd: EditText
    private lateinit var btnLogin: Button
    private lateinit var spinkitLoading: SpinKitView
    private lateinit var txtRegisterLink: TextView
    private lateinit var presenterMainLogin: MainLoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_as_service)
        initUI()
        //initBackendless()
        presenterMainLogin = MainLoginPresenter(this)
        initClickUI()

    }

    /*private fun initBackendless() {
        Backendless.initApp(this, Constants.APPLICATION_ID, Constants.ANDROID_API_KEY)
    }*/

    private fun initClickUI() {
        btnLogin.setOnClickListener {
            val emailValue = txtEmail.text.toString()
            val passwdValue = txtPasswd.text.toString()
            presenterMainLogin.initLogin(emailValue, passwdValue)
        }
        txtRegisterLink.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initUI() {
        rootConstraint = findViewById(R.id.rootConstraint)
        txtEmail = findViewById(R.id.txtEmail)
        txtPasswd = findViewById(R.id.txtPasswd)
        btnLogin = findViewById(R.id.btnLogin)
        txtRegisterLink = findViewById(R.id.txtRegisterLink)
        spinkitLoading = findViewById(R.id.spinkitLoading)
    }

    override fun notifyViewShowMessage(msgInfo: String) {
        StaticMethods.showToast(this, msgInfo)
    }

    override fun notifyViewStartSpinkitLoading() {
        showSpinkitLoading(true)
        enableViews(false)
    }

    override fun notifyViewStopSpinkitLoading() {
        showSpinkitLoading(false)
        enableViews(true)
    }

    override fun notifyViewSuccessfulLogin(businessName:String,businessObjectId:String,businessAddress:String) {
        val intent = Intent(this, MainProfileActivity::class.java).apply {
            putExtra(BACKEND_COLUMN_BUSINESS_NAME, businessName)
            putExtra(BACKEND_COLUMN_OBJECT_ID, businessObjectId)
            putExtra(BACKEND_COLUMN_ADDRESS, businessAddress)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
    }

    private fun enableViews(isEnabled: Boolean) {
        rootConstraint.alpha = if (isEnabled) {
            1.0F
        } else {
            0.5F
        }
        txtEmail.isEnabled = isEnabled
        txtEmail.isClickable = isEnabled
        txtPasswd.isEnabled = isEnabled
        txtPasswd.isClickable = isEnabled
        btnLogin.isEnabled = isEnabled
        btnLogin.isClickable = isEnabled
    }

    private fun showSpinkitLoading(isVisible: Boolean) {
        spinkitLoading.visibility = if (isVisible) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

}