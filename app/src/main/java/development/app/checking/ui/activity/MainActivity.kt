package development.app.checking.ui.activity

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.View
import development.app.checking.R
import development.app.checking.ui.base.BaseActivity
import development.app.checking.viewmodel.CustomViewModelFactory
import development.app.checking.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private lateinit var viewModel: UserViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //binding  = DataBindingUtil.setContentView(this,R.layout.activity_main)

        initListeners()
        val factory = CustomViewModelFactory()


        viewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
        /*binding.user = viewModel
        binding.executePendingBindings()*/
        viewModel.getName().observe(this, listener)
        //viewModel.loadBooks()
        viewModel.getLoading().observe(this, loadingListener)


    }

    private fun initListeners(){
        fab.setOnClickListener {
            viewModel.onLikeClick()
        }
    }

    private val listener = Observer<String> {
       // txtName.text = it
    }

    private val loadingListener = Observer<Boolean> { value ->

       /* if (value == false) {
            progressBar.visibility = View.INVISIBLE
        } else {
            progressBar.visibility = View.VISIBLE
        }*/
    }



}
