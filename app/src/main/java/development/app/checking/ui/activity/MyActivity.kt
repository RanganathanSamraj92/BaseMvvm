package development.app.checking.ui.activity

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import development.app.checking.R
import development.app.checking.model.UserModel
import development.app.checking.ui.base.BaseActivity
import development.app.checking.viewmodel.MyViewModel
import kotlinx.android.synthetic.main.activity_scrolling.*
import kotlinx.android.synthetic.main.content_scrolling.*

class MyActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)
        // Create a ViewModel the first time the system calls an activity's onCreate() method.
        // Re-created activities receive the same MyViewModel instance created by the first activity.

        val model = ViewModelProviders.of(this).get(MyViewModel::class.java)
        model.getUsers().observe(this, Observer<List<UserModel>>{ users ->
            // update UI
            txtScroll.text = users!![0].email
        })
        model.loadUsers()
        initlistenrs()


    }

    private fun initlistenrs(){
        fab.setOnClickListener {
            newIntent(this@MyActivity, MainActivity::class.java) }
    }
}
