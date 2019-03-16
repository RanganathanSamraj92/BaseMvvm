package development.app.checking.ui.activity

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import development.app.checking.R
import development.app.checking.data.source.remote.APIResponse
import development.app.checking.ui.base.BaseActivity
import development.app.checking.viewmodel.VersionViewModel
import kotlinx.android.synthetic.main.content_android_versions.*

class AndroidVersionActivity : BaseActivity() {

    private lateinit var viewModel: VersionViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        setStub(R.layout.content_android_versions)
        setAppBar("Android Arch")
        initListeners()
        viewModel = ViewModelProviders.of(this).get(VersionViewModel::class.java)
        viewModelSetup(this,viewModel)

        viewModel.androidVersions.observe(this, Observer {versions->

            val s = StringBuilder()
            versions.forEach {
                s.append(" ${it.name} ${it.api_level} + \n")
            }
            txtContent.text = s.toString()
        })


        //viewModel.baseApiResponse.observe(this, loadingListener)
    }



    private fun initListeners() {
        btnLoad.setOnClickListener {
            viewModel.fetchVersions()
        }
    }

    private val loadingListener = Observer<APIResponse> { res ->

        when (res) {

            is APIResponse.Success -> {
                hideProgress()
                res.successResult as APIResponse?
                if (res.successResult.meta.status){
                    val versions = res.successResult.data.versions
                    val s = StringBuilder()
                    versions.forEach {
                        s.append(" ${it.name} ${it.api_level} + \n")
                    }
                    txtContent.text = s.toString()
                }else{
                    showMsg(btnLoad, res.successResult.meta.message)
                }
            }
            is APIResponse.Error -> {
                hideProgress()
                showMsg(btnLoad, res.errorMessage as String)
            }

            is APIResponse.Processing -> {
                showProgress("loading")
            }
            is APIResponse.Exception -> {
                hideProgress()
                showException(res.exception as String)
            }
        }

    }


}
