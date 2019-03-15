package development.app.checking.ui.activity

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import development.app.checking.R
import development.app.checking.ui.base.BaseActivity
import development.app.checking.viewmodel.TmdbViewModel
import kotlinx.android.synthetic.main.content_android_versions.*

class MovieActivity : BaseActivity() {

    private lateinit var viewModel: TmdbViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        setStub(R.layout.content_android_versions)
        setAppBar("")
        initListeners()
        viewModel = ViewModelProviders.of(this).get(TmdbViewModel::class.java)



        viewModel.popularMoviesLiveData.observe(this, Observer {versions ->

           if (versions.size>0){
               val s = StringBuilder()
               versions.forEach {
                   s.append(" ${it.name} ${it.api_level} + \n")
               }
               txtContent.text = s.toString()
           }
        })

    }


    private fun initListeners() {

        btnLoad.setOnClickListener {
            viewModel.fetchMovies(it)
        }
    }


}
