package nathan.badlogic.yosigo.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import com.github.ybq.android.spinkit.SpinKitView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import nathan.badlogic.yosigo.R
import nathan.badlogic.yosigo.Utils.StaticMethods
import nathan.badlogic.yosigo.presenter.RegisterAsServicePresenter
import nathan.badlogic.yosigo.view.interfaces.RegisterAsServiceView

class RegisterAsServiceActivity : AppCompatActivity(), RegisterAsServiceView {
    private lateinit var rootConstraint: ConstraintLayout
    private lateinit var txtBusinessName: EditText
    private lateinit var txtEmail: EditText
    private lateinit var txtPassword: EditText
    private lateinit var txtPasswordAgain: EditText
    private lateinit var fabRegister: FloatingActionButton
    private lateinit var spinkitLoading: SpinKitView

    private lateinit var presenterRegisterAsService: RegisterAsServicePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_as_service)
        initUI()
        initUIClick()
        presenterRegisterAsService = RegisterAsServicePresenter(this)
    }

    private fun initUIClick() {
        fabRegister.setOnClickListener {
            val businessName = txtBusinessName.text.toString()
            val txtEmail = txtEmail.text.toString()
            val txtPassword = txtPassword.text.toString()
            val txtPasswordAgain = txtPasswordAgain.text.toString()
            presenterRegisterAsService.initRegister(
                businessName,
                txtEmail,
                txtPassword,
                txtPasswordAgain
            )

        }
    }

    private fun initUI() {
        rootConstraint = findViewById(R.id.rootConstraint)
        txtBusinessName = findViewById(R.id.txtBusinessName)
        txtEmail = findViewById(R.id.txtEmail)
        txtPassword = findViewById(R.id.txtPassword)
        txtPasswordAgain = findViewById(R.id.txtPasswordAgain)
        spinkitLoading = findViewById(R.id.spinkitLoading)
        fabRegister = findViewById(R.id.fabRegister)
    }

    override fun notifyViewShowMessage(msg: String) {
        StaticMethods.showToast(this, msg)
    }

    override fun notifyViewShowLoading() {
        showSpinkitLoading(true)
        enableViews(false)
    }

    override fun notifyViewStopLoading() {
        showSpinkitLoading(false)
        enableViews(true)
    }

    override fun notifyViewSuccessfulRegister() {
        finish()
    }

    private fun enableViews(enableView: Boolean) {
        rootConstraint.alpha = if (enableView) {
            1.0F
        } else {
            0.5f
        }
        txtBusinessName.isEnabled = enableView
        txtBusinessName.isClickable = enableView
        txtEmail.isEnabled = enableView
        txtEmail.isClickable = enableView
        txtPassword.isEnabled = enableView
        txtPassword.isClickable = enableView
        txtPasswordAgain.isEnabled = enableView
        txtPasswordAgain.isClickable = enableView
        fabRegister.isEnabled = enableView
        fabRegister.isClickable = enableView
    }

    private fun showSpinkitLoading(showLoading: Boolean) {
        spinkitLoading.visibility = if (showLoading) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
}