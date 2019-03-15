package development.app.checking.ui.base

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewStub
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import development.app.checking.R
import development.app.checking.ui.fragment.BottomSheetEx
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.container_ly.*
import kotlinx.android.synthetic.main.container_ly.view.*
import kotlinx.android.synthetic.main.error_ly.*
import kotlinx.android.synthetic.main.error_ly.view.*
import kotlinx.android.synthetic.main.fragment_bottom_sheet_ex.*
import kotlinx.android.synthetic.main.progress_ly.*
import kotlinx.android.synthetic.main.progress_ly.view.*

open class BaseActivity : AppCompatActivity(), BottomSheetEx.BottomSheetListener {

    override fun onOptionClick(text: String) {

    }


    companion object {
        private val INTENT_USER_ID = "user_id"

        fun newIntent(context: Context, cls: Class<*>): Intent {
            ContextCompat.startActivity(context, Intent(context, cls), null)
            return Intent(context, cls)
        }


    }


    public fun setStub(id: Int) {
        val viewStub = findViewById<ViewStub>(R.id.viewStub)

        viewStub.layoutResource = id
        viewStub.inflate()
    }

    open fun showMsg(view: View, msg: String) {
        makeLog(msg)

        Snackbar.make(view, msg, Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()
    }

    open fun showErrorMsg(view: View, msg: String) {
        makeLog(msg)
        Snackbar.make(view, "Error - $msg", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()
    }

    private fun makeLog(msg: String) {
        Log.w("base", msg)
    }

    fun setAppBar(msg: String) {
        toolbar.title = "android architecture"
        toolbar.navigationIcon = getDrawable(R.drawable.ic_arrow_back_black_24dp)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    open fun showProgress(msg: String) {
        containerLy.visibility = VISIBLE
    }

    open fun hideProgress() {
        containerLy.visibility = GONE
    }

    open fun showError(message: String) {
        // containerLy.visibility = VISIBLE
        progressLy.visibility = GONE
        //   errorLy.visibility = VISIBLE
        txtErrorMessage.text = message
    }

    @SuppressLint("SetTextI18n")
    open fun showException(message: String) {
        val bottomSheet = BottomSheetEx()
        val view = layoutInflater.inflate(R.layout.fragment_bottom_sheet_ex, null)
        view.errorLy.txtErrorMessage.text = message
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(view)
        dialog.show()
    }


}
