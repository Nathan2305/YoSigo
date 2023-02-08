package nathan.badlogic.yosigo.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.backendless.Backendless
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import com.github.ybq.android.spinkit.SpinKitView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import nathan.badlogic.yosigo.R
import nathan.badlogic.yosigo.Utils.AdapterAreas
import nathan.badlogic.yosigo.Utils.Constants
import nathan.badlogic.yosigo.Utils.StaticMethods
import nathan.badlogic.yosigo.dao.Area
import nathan.badlogic.yosigo.presenter.MainProfilePresenter
import nathan.badlogic.yosigo.view.interfaces.MainProfileView

class MainProfileActivity : AppCompatActivity(), MainProfileView, (Area) -> Unit {
    private lateinit var rootConstraint: ConstraintLayout
    private lateinit var toolbar: Toolbar
    private lateinit var txtBusinessName: TextView
    private lateinit var txtBusinessAddress: TextView
    private lateinit var txtMsgEmptyCategories: TextView
    private lateinit var spinkitLoading: SpinKitView
    private lateinit var recyclerView: RecyclerView
    var txtMessageEmptyAreas = ""
    private lateinit var businessName: String
    private lateinit var businessObjectId: String
    private lateinit var businessAddress: String
    private lateinit var fabAddCategory: FloatingActionButton

    private lateinit var presenterMainProfile: MainProfilePresenter

    private val adapter: AdapterAreas = AdapterAreas(this)

    private val layoutManager = LinearLayoutManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_profile_as_business)
        businessName = intent.getStringExtra(Constants.BACKEND_COLUMN_BUSINESS_NAME)!!
        businessObjectId = intent.getStringExtra(Constants.BACKEND_COLUMN_OBJECT_ID)!!
        businessAddress = intent.getStringExtra(Constants.BACKEND_COLUMN_ADDRESS)!!
        initUI()
        initUIClick()
        presenterMainProfile = MainProfilePresenter(this)
        presenterMainProfile.showCategories(businessObjectId)
    }

    private fun initUIClick() {
        fabAddCategory.setOnClickListener {
            lateinit var btnCreate: Button
            lateinit var btnCancel: Button
            lateinit var valueArea: EditText
            lateinit var descArea: EditText
            lateinit var floorArea: EditText
            lateinit var newAreaValue: String
            lateinit var newDescAreaValue: String
            lateinit var newFloorAreaValue: String
            val builder = AlertDialog.Builder(this/*, R.style.dialog_create_cat*/)
            with(builder) {
                val inflater = LayoutInflater.from(context)
                val view = inflater.inflate(R.layout.dialog_new_area, null)
                setView(view)
                setCancelable(false)
                valueArea = view.findViewById(R.id.valueArea)
                descArea = view.findViewById(R.id.descArea)
                floorArea = view.findViewById(R.id.floorArea)
                btnCreate = view.findViewById(R.id.btnCreate)
                btnCancel = view.findViewById(R.id.btnCancel)
            }
            val alertDialog = builder.create()
            btnCreate.setOnClickListener {
                newAreaValue = valueArea.text.toString()
                newDescAreaValue = descArea.text.toString()
                newFloorAreaValue = floorArea.text.toString()
                if (newAreaValue.isNotEmpty() and newDescAreaValue.isNotEmpty() and newFloorAreaValue.isNotEmpty()) {
                    alertDialog.cancel()
                    presenterMainProfile.createNewArea(
                        newAreaValue,
                        newDescAreaValue,
                        newFloorAreaValue
                    )
                }
            }
            btnCancel.setOnClickListener {
                alertDialog.cancel()
            }
            alertDialog.show()
        }
    }

    private fun initUI() {
        rootConstraint = findViewById(R.id.rootConstraint)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        spinkitLoading = findViewById(R.id.spinkitLoading)
        txtBusinessName = findViewById(R.id.txtBusinessName)
        txtBusinessAddress = findViewById(R.id.txtBusinessAddress)
        txtMsgEmptyCategories = findViewById(R.id.txtMsgEmptyCategories)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        fabAddCategory = findViewById(R.id.fabAddCategory)
        txtBusinessName.text = businessName
        txtBusinessAddress.text = businessAddress
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.service_profile, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logOut -> {
                initLogOut()
            }
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun initLogOut() {
        val bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_log_out)
        bottomSheetDialog.show()
        val btnAccept: Button? = bottomSheetDialog.findViewById(R.id.btnAccept)
        val btnCancel: Button? = bottomSheetDialog.findViewById(R.id.btnCancel)
        btnCancel?.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
        btnAccept?.setOnClickListener {
            bottomSheetDialog.dismiss()
            notifyViewShowSpinkitLoading(true)
            presenterMainProfile.initLogOut()

        }
    }

    override fun notifyViewShowAllCategories(listCategories: MutableList<Area>) {
        adapter.submitList(listCategories)
    }

    override fun notifyViewShowEmptyCategories() {
        txtMessageEmptyAreas = "Aún no has creado ninguna área"
        txtMsgEmptyCategories.text = txtMessageEmptyAreas
    }

    override fun notifyViewShowSpinkitLoading(enableViews: Boolean) {
        spinkitLoading.visibility = if (enableViews) {
            View.VISIBLE
        } else {
            View.GONE
        }
        enableView(!enableViews)
    }

    override fun notifyViewSuccessfulAreaCreated(
        areaName: String,
        newListUpdated: MutableList<Area>
    ) {
        notifyViewShowInfo("Área $areaName creada correctamente")
        adapter.submitList(newListUpdated)
        if (newListUpdated.isNotEmpty()) {
            txtMsgEmptyCategories.text = ""
        }
    }

    override fun notifyViewShowInfo(msg: String) {
        StaticMethods.showToast(this, msg)
    }

    override fun notifyViewSuccessfulLogOut() {
        val intent = Intent(applicationContext, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
    }

    private fun enableView(enableViews: Boolean) {
        fabAddCategory.isEnabled = enableViews
        fabAddCategory.isClickable = enableViews
        rootConstraint.alpha = if (enableViews) {
            1.0F
        } else {
            0.5F
        }
    }

    override fun invoke(area: Area) {
        val areaSelected = area.name
        val intent=Intent(this,AreaSelectedActivity::class.java).apply {
            putExtra("areaSelected",areaSelected)
            putExtra("businessObjectId",businessObjectId)
        }
        startActivity(intent)

    }
}