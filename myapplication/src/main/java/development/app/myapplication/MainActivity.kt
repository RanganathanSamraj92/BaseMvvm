package development.app.myapplication

import development.app.myapplication.base.BaseActivity.Companion.newIntent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import development.app.myapplication.activity.MLActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startActivity(newIntent(this@MainActivity, MLActivity::class.java))


        startActivity(newIntent(this@MainActivity, MLKitActivity::class.java))
    }
}
