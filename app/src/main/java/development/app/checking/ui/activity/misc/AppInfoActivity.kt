package development.app.checking.ui.activity.misc

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import development.app.checking.BR
import development.app.checking.R
import development.app.checking.databinding.ContentAppInfoBinding
import development.app.checking.ui.base.BaseActivity
import development.app.checking.utils.ForceUpdateChecker
import development.app.checking.viewmodel.AppInfoViewModel
import kotlinx.android.synthetic.main.content_app_info.*


class AppInfoActivity : BaseActivity(), ForceUpdateChecker.OnUpdateNeededListener, View.OnClickListener {

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnCheckForUpdate -> {
                ForceUpdateChecker.with(this).onUpdateNeeded(this).check()
            }
        }
    }


    override fun onUpToDate() {
        makeLog("onUpToDate :")
        val dialog = AlertDialog.Builder(this)
            .setTitle("App is up to date!")
            .setMessage("You are currently using a latest one!")
            .setPositiveButton("Ok", DialogInterface.OnClickListener { dialog, which ->
            }).create()

        dialog.show()
    }

    override fun onUpdateNeeded(updateUrl: String) {
        makeLog("onUpdateNeeded :$updateUrl")
        val dialog = AlertDialog.Builder(this)
            .setTitle("New version available")
            .setMessage("Please, update app to new version to continue reposting.")
            .setPositiveButton("Update", DialogInterface.OnClickListener { dialog, which ->
                redirectStore(updateUrl)
            }).setNegativeButton("cancel", null).create()

        dialog.show()

    }

    private lateinit var appInfoViewModel: AppInfoViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ContentAppInfoBinding = DataBindingUtil.setContentView(this, R.layout.content_app_info)

        setAppBar("")

        appInfoViewModel = ViewModelProviders.of(this).get(AppInfoViewModel::class.java)
        binding.setVariable(BR.appInfoViewModel, appInfoViewModel)

        btnCheckForUpdate.setOnClickListener(this)

    }


}
