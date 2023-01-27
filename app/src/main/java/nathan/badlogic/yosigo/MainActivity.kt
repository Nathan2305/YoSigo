package nathan.badlogic.yosigo


import android.content.Intent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {
    private lateinit var btnEnterAsService: Button
    private lateinit var btnEnterAsUser: Button
    private lateinit var txtViewAsService: TextView
    private lateinit var txtViewAsUser: TextView
    private lateinit var rootConstraint: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUI()
        initUIClick()
    }

    private fun initUIClick() {
        txtViewAsUser.setOnClickListener {
            val msg = resources.getString(R.string.txtAsUser)
            showSnackBar(msg)
        }
        txtViewAsService.setOnClickListener {
            val msg = resources.getString(R.string.txtAsService)
            showSnackBar(msg)
        }

        btnEnterAsService.setOnClickListener {
            val intent=Intent(this,MainLoginEnterAsService::class.java)
            startActivity(intent)
        }

    }

    private fun initUI() {
        rootConstraint = findViewById(R.id.rootConstraint)
        btnEnterAsService = findViewById(R.id.btnEnterAsService)
        btnEnterAsUser = findViewById(R.id.btnEnterAsUser)
        txtViewAsService = findViewById(R.id.txtViewAsService)
        txtViewAsUser = findViewById(R.id.txtViewAsUser)
    }


    private fun showSnackBar(msg: String) {
        Snackbar.make(rootConstraint, msg, Snackbar.LENGTH_LONG).show()
    }
}