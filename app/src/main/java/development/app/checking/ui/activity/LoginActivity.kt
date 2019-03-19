package development.app.checking.ui.activity

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.palette.graphics.Palette
import com.squareup.picasso.Picasso
import development.app.checking.model.AndroidVersion
import development.app.checking.ui.base.BaseActivity
import development.app.checking.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.app_bar_collapse.*
import kotlinx.android.synthetic.main.content_scrolling.*
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import development.app.checking.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.content_login.*


class LoginActivity : BaseActivity() {

    private lateinit var loginViewModel: LoginViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(development.app.checking.R.layout.activity_base_collapse)
        setStub(development.app.checking.R.layout.content_login)
        setAppBarCollapse("Details")
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        viewModelSetup(this, loginViewModel)



        loginViewModel.loginModel.observe(this, Observer { login ->
            txtContent.text = login.token
            toolbar_layout.title = login.message



        })

        btnLogin.setOnClickListener {
            loginViewModel.login("","")
        }



    }





    private fun dynamicColor(bitmap: Bitmap?) {
        Palette.from(bitmap!!).generate { palette ->
            window.statusBarColor = palette!!.getMutedColor(resources.getColor(development.app.checking.R.color.colorPrimaryDark))
            toolbar_layout!!.setContentScrimColor(palette!!.getMutedColor(resources.getColor(development.app.checking.R.color.colorPrimary)))
            toolbar_layout!!.setStatusBarScrimColor(palette.getMutedColor(resources.getColor(development.app.checking.R.color.colorPrimaryDark)))
        }

    }


}
