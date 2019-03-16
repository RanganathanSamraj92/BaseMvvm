package development.app.checking.ui.activity

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import development.app.checking.R
import development.app.checking.data.source.remote.APIResponse
import development.app.checking.model.AndroidVersion
import development.app.checking.ui.adapter.RecyclerAdapter
import development.app.checking.ui.base.BaseActivity
import development.app.checking.utils.Utils
import development.app.checking.viewmodel.VersionViewModel
import kotlinx.android.synthetic.main.content_android_versions.*
import kotlinx.android.synthetic.main.recyclerview_ly.*

class AndroidVersionActivity : BaseActivity() {

    private lateinit var viewModel: VersionViewModel

    private lateinit var adapter: RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        setStub(R.layout.content_android_versions)
        setAppBar("Android Arch")
        initListeners()
        viewModel = ViewModelProviders.of(this).get(VersionViewModel::class.java)
        viewModelSetup(this,viewModel)

        viewModel.androidVersions.observe(this, Observer {versions->

            adapter = RecyclerAdapter( object : Utils.OnItemClickListener {
                override fun onClick(v: View,item:Any) {
                    item as AndroidVersion
                    showMsg(v,"${item.name} v : ${item.images.get(0).image}")
                }
            },versions.toCollection(ArrayList<AndroidVersion>()))
            recyclerAndroidVersions.adapter = adapter
        })
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
                    //txtContent.text = s.toString()
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
