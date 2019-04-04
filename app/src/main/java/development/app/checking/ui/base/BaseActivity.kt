package development.app.checking.ui.base

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewStub
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.github.florent37.runtimepermission.kotlin.PermissionException
import com.github.florent37.runtimepermission.kotlin.coroutines.experimental.askPermission
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import development.app.checking.app.App
import development.app.checking.pref.Prefs
import development.app.checking.ui.activity.SplashActivity
import development.app.checking.ui.activity.auth.LoginActivity
import development.app.checking.ui.fragment.BottomSheetEx
import development.app.checking.utils.Utils
import development.app.checking.viewmodel.BaseViewModel.BaseViewModel
import kotlinx.android.synthetic.main.alert_ly.view.*
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.app_bar_collapse.*
import kotlinx.android.synthetic.main.container_ly.*
import kotlinx.android.synthetic.main.error_ly.*
import kotlinx.android.synthetic.main.error_ly.view.*
import kotlinx.android.synthetic.main.progress_ly.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import java.io.Serializable
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


open class BaseActivity : AppCompatActivity(), BottomSheetEx.BottomSheetListener {

    public val context:Context = this@BaseActivity
    @Inject
    lateinit var prefs: Prefs

    private val parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default

    val scope = CoroutineScope(coroutineContext)

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }


    /*injects MyComponents' Prefs instance*/
    fun inject() {
        when (this) {
            is LoginActivity -> (applicationContext as App).myComponent.inject(this)

            is SplashActivity -> (applicationContext as App).myComponent.inject(this)

            // is ProfileActivity -> (applicationContext as App).myComponent.inject(this)

            else -> {
                (applicationContext as App).myComponent.inject(this)
            }
        }

    }

    public var showApiErrorMsg = true
    internal var showProgress = true
    open fun viewModelSetup(activity: BaseActivity, viewModel: BaseViewModel) {


        viewModel.loadingStatus.observe(activity, Observer {
            if (showProgress) {
                if (it) {
                    showProgress("")
                } else {
                    hideProgress()
                }
            }
        })
        viewModel.errorStatus.observe(activity, Observer { error ->
            if (showApiErrorMsg)
                showException(error)
        })
    }

    override fun onOptionClick(text: String) {

    }


    internal fun makePermissionsRequest(vararg permissions: String) {
        scope.launch {
            try {
                val result = askPermission(*permissions)
                //all permissions already granted or just granted
                //your action
                makeLog("Accepted :${result.accepted.toString()}")

            } catch (e: PermissionException) {
                if (e.hasDenied()) {
                    makeLog("Denied :")
                    //the list of denied permissions
                    e.denied.forEach { permission ->
                        makeLog(permission)
                    }
                    //but you can ask them again, eg:

                    runOnUiThread(Runnable {
                        AlertDialog.Builder(this@BaseActivity)
                            .setMessage("Please accept our permissions")
                            .setPositiveButton("yes") { dialog, which ->
                                e.askAgain()
                            }
                            .setNegativeButton("no") { dialog, which ->
                                dialog.dismiss()
                            }
                            .show();
                    })

                }

                if (e.hasForeverDenied()) {
                    makeLog("ForeverDenied")
                    //the list of forever denied permissions, user has check 'never ask again'
                    e.foreverDenied.forEach { permission ->
                        makeLog(permission)
                    }
                    //you need to open setting manually if you really need it
                    e.goToSettings();
                }
            }
        }
    }

    companion object {
        private val INTENT_USER_ID = "user_id"

        fun newIntent(context: Context, cls: Class<*>, intentData: Any): Intent {
            val intent = Intent(context, cls)
            if (intentData != "") {
                intent.putExtra("intent_data", intentData as Serializable)
            }
            ContextCompat.startActivity(context, intent, null)
            return intent
        }

        open fun showMsg(view: View, msg: String) {
            makeLog(msg)

            Snackbar.make(view, msg, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        internal fun makeLog(msg: String) {
            Log.w("base", msg)
        }


    }


    public fun setStub(id: Int) {
        val viewStub = findViewById<ViewStub>(development.app.checking.R.id.viewStub)
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
        toolbar.navigationIcon = getDrawable(development.app.checking.R.drawable.ic_arrow_back_black_24dp)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    fun setAppBarCollapse(title: String) {
        app_bar_collapse.visibility = VISIBLE
        toolbarCollapse.title = title
        toolbarCollapse.navigationIcon = getDrawable(development.app.checking.R.drawable.ic_arrow_back_black_24dp)
        setSupportActionBar(toolbarCollapse)
        toolbarCollapse.setNavigationOnClickListener { onBackPressed() }
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
        val view = layoutInflater.inflate(development.app.checking.R.layout.fragment_bottom_sheet_ex, null)
        view.errorLy.txtErrorMessage.text = message
        view.alertLy.visibility = GONE
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(view)
        dialog.show()
    }

    @SuppressLint("SetTextI18n")
    open fun showAlert(title: String, message: String, alertOkListner: Any?, cancelListner: Any?) {
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(development.app.checking.R.layout.fragment_bottom_sheet_ex, null)
        view.errorLy.visibility = GONE
        view.alertLy.txtAlertMessage.text = message
        view.alertLy.txtAlertTitle.text = title

        view.alertLy.btnUpdate.setOnClickListener {
            alertOkListner as Utils.OnClickListener
            alertOkListner.onClick(it)
            dialog.dismiss()
        }
        view.alertLy.btnSkip.setOnClickListener {
            cancelListner as Utils.OnClickListener
            cancelListner.onClick(it)
            dialog.dismiss()
        }


        dialog.setContentView(view)
        dialog.show()
    }


}
