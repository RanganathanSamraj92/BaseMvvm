package development.app.checking.ui.activity

import android.Manifest
import android.os.Bundle
import android.os.Handler
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import development.app.checking.R
import development.app.checking.pref.PrefMgr
import development.app.checking.ui.activity.auth.LoginActivity
import development.app.checking.ui.activity.misc.AppInfoActivity
import development.app.checking.ui.activity.profile.ProfileActivity
import development.app.checking.ui.base.BaseActivity
import development.app.myapplication.MainActivity
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar_home.*

class HomeActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var doubleBackToExitPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)

        inject()
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
           doubleTapExit()
        }
    }

    private fun doubleTapExit() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()

        Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_logout -> {
               prefs.putData(PrefMgr.KEY_ACCESS_TOKEN,"")
                newIntent(this@HomeActivity, LoginActivity::class.java, "")
            }
            R.id.nav_gallery -> {
                makePermissionsRequest(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
            R.id.nav_feedback-> {

                newIntent(this@HomeActivity, MainActivity::class.java, "")
            }
            R.id.nav_profile -> {

                newIntent(this@HomeActivity, ProfileActivity::class.java, "")
            }
            R.id.nav_app_info -> {

                newIntent(this@HomeActivity,    AppInfoActivity::class.java, "")
            }

        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }


}
