package development.app.checking.ui.activity

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import development.app.checking.R
import development.app.checking.data.repository.remote.APIResponse
import development.app.checking.model.AndroidVersion
import development.app.checking.ui.base.BaseActivity
import development.app.checking.viewmodel.VersionViewModel
import kotlinx.android.synthetic.main.content_android_versions.*

class AndroidVersionActivity : BaseActivity() {

    private lateinit var viewModel: VersionViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        setStub(R.layout.content_android_versions)
        setAppBar("")
        initListeners()
        viewModel = ViewModelProviders.of(this).get(VersionViewModel::class.java)

        viewModel.response.observe(this, loadingListener)


    }

    private val loadingListener = Observer<APIResponse> { res ->

        when (res) {
            is APIResponse.Success -> {
                hideProgress()
                val versions = res.result as List<AndroidVersion>

                val s: StringBuilder = StringBuilder()
                versions.forEach {
                    s.append(" ${it.name} ${it.api_level} + \n")
                }
                txtContent.text = s.toString()
                newIntent(this@AndroidVersionActivity,MovieActivity::class.java)
            }
            is APIResponse.Error -> {
                hideProgress()
                showMsg(btnLoad, res.errorMessage as String)
                // showError(res.message)
            }

            is APIResponse.Processing -> {
                showProgress("loading")
            }
            is APIResponse.Exception -> {
                hideProgress()
                showException(res.error as String)
            }
        }

    }

    private fun initListeners() {

        btnLoad.setOnClickListener {
            viewModel.fetchMovies()
        }
    }


}
