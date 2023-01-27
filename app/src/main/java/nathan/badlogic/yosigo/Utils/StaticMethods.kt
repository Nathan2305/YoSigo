package nathan.badlogic.yosigo.Utils

import android.content.Context
import android.widget.Toast

class StaticMethods {
    companion object{
        fun showToast(context:Context,msg:String){
            Toast.makeText(context,msg,Toast.LENGTH_SHORT).show()
        }
    }

}