package development.app.checking.ui.activity

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.view.menu.MenuBuilder
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import development.app.checking.model.AndroidVersion
import development.app.checking.ui.adapter.RecyclerAdapter
import development.app.checking.ui.base.BaseActivity
import development.app.checking.utils.Utils
import development.app.checking.viewmodel.VersionViewModel
import kotlinx.android.synthetic.main.recyclerview_ly.*


class AndroidVersionActivity : BaseActivity() {

    private lateinit var viewModel: VersionViewModel

    private lateinit var adapter: RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(development.app.checking.R.layout.activity_base)
        setStub(development.app.checking.R.layout.content_android_versions)
        setAppBar("Android Arch")

        initListeners()
        viewModel = ViewModelProviders.of(this).get(VersionViewModel::class.java)
        viewModelSetup(this, viewModel)

        viewModel.androidVersions.observe(this, Observer { versions ->

            adapter = RecyclerAdapter(object : Utils.OnItemClickListener {
                override fun onClick(v: View, item: Any) {
                    item as AndroidVersion
                    showMsg(v, "${item.name} v : ${item.version_code}")
                    newIntent(this@AndroidVersionActivity,ScrollingActivity::class.java,item)
                }
            }, versions.toCollection(ArrayList<AndroidVersion>()))
            recyclerAndroidVersions.adapter = adapter
        })
        viewModel.fetchVersions()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(development.app.checking.R.menu.menu_android_versions, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            development.app.checking.R.id.action_refresh -> {
                viewModel.fetchVersions()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }



    private fun initListeners() {

    }


}
