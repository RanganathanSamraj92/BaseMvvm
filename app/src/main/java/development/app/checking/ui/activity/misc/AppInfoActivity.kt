package development.app.checking.ui.activity.misc

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import development.app.checking.BR
import development.app.checking.R
import development.app.checking.databinding.ContentAppInfoBinding
import development.app.checking.ui.base.BaseActivity
import development.app.checking.viewmodel.AppInfoViewModel


class AppInfoActivity : BaseActivity() {

    private lateinit var appInfoViewModel: AppInfoViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ContentAppInfoBinding = DataBindingUtil.setContentView(this, R.layout.content_app_info)

        setAppBar("")

        appInfoViewModel = ViewModelProviders.of(this).get(AppInfoViewModel::class.java)
        binding.setVariable(BR.appInfoViewModel, appInfoViewModel)


    }


}
