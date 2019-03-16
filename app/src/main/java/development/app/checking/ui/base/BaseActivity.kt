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
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import development.app.checking.R
import development.app.checking.ui.fragment.BottomSheetEx
import development.app.checking.viewmodel.BaseViewModel.BaseViewModel
import kotlinx.android.synthetic.main.alert_ly.view.*
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.container_ly.*
import kotlinx.android.synthetic.main.error_ly.*
import kotlinx.android.synthetic.main.error_ly.view.*
import kotlinx.android.synthetic.main.progress_ly.*

open class BaseActivity : AppCompatActivity(), BottomSheetEx.BottomSheetListener {


    open fun viewModelSetup(activity: BaseActivity, viewModel: BaseViewModel) {


        viewModel.loadingStatus.observe(activity, Observer {
            if (it) {
                showProgress("")
            } else {
                hideProgress()
            }
        })
        viewModel.errorStatus.observe(activity, Observer { error ->
            showException(error)
        })
    }

    override fun onOptionClick(text: String) {

    }


    companion object {
        private val INTENT_USER_ID = "user_id"

        fun newIntent(context: Context, cls: Class<*>): Intent {
            ContextCompat.startActivity(context, Intent(context, cls), null)
            return Intent(context, cls)
        }

        open fun showMsg(view: View, msg: String) {
            makeLog(msg)

            Snackbar.make(view, msg, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        private fun makeLog(msg: String) {
            Log.w("base", msg)
        }


    }


    public fun setStub(id: Int) {
        val viewStub = findViewById<ViewStub>(R.id.viewStub)

        viewStub.layoutResource = id
        viewStub.inflate()
    }


    open fun showErrorMsg(view: View, msg: String) {
        makeLog(msg)
        Snackbar.make(view, "Error - $msg", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()
    }


    fun setAppBar(title: String) {
        toolbar.title = title
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
        containerLy.visibility = VISIBLE
        progressLy.visibility = GONE
        errorLy.visibility = VISIBLE
        txtErrorMessage.text = message
    }

    @SuppressLint("SetTextI18n")
    open fun showException(message: String) {
        val view = layoutInflater.inflate(R.layout.fragment_bottom_sheet_ex, null)
        view.errorLy.txtErrorMessage.text = message
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(view)
        dialog.show()
    }

    @SuppressLint("SetTextI18n")
    open fun showAlert(title: String, message: String, alertOkListner: Any?) {
        val view = layoutInflater.inflate(R.layout.fragment_bottom_sheet_ex, null)
        view.errorLy.visibility= GONE
        view.alertLy.txtAlertMessage.text = message
        view.alertLy.txtAlertTitle.text = title
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(view)
        dialog.show()
    }


}
