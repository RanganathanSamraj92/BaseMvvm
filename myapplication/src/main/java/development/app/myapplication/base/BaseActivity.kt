package development.app.myapplication.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

import kotlinx.android.synthetic.main.activity_mlkit_lib.*

open class BaseActivity : AppCompatActivity() {

    var context: Context = this;


    companion object {
        private val INTENT_USER_ID = "user_id"
        fun newIntent(context: Context, cls: Class<*>): Intent {
            return Intent(context, cls)
        }

        fun showMsg(context: Context, view: View, msg: String) {
            Snackbar.make(view, msg, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

    }

}
