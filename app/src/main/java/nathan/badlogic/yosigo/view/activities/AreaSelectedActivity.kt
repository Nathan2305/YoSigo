package nathan.badlogic.yosigo.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import nathan.badlogic.yosigo.R
import nathan.badlogic.yosigo.view.interfaces.InterfaceInitUI

class AreaSelectedActivity : AppCompatActivity(), InterfaceInitUI {

    private lateinit var areaSelected: String
    private lateinit var businessObjectId: String

    private lateinit var areaName: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_area_selected)
        val intent = intent
        areaSelected = intent.getStringExtra("areaSelected")!!
        businessObjectId = intent.getStringExtra("businessObjectId")!!
        initUI()
    }

    override fun initUI() {
        areaName = findViewById(R.id.areaName)
        areaName.text = areaSelected
    }
}