package development.app.checking.ui.activity.profile

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.squareup.picasso.Picasso
import development.app.checking.R
import development.app.checking.pref.PrefMgr.Companion.KEY_TOKEN
import development.app.checking.ui.base.BaseActivity
import development.app.checking.utils.Utils
import development.app.checking.viewmodel.ProfileViewModel
import kotlinx.android.synthetic.main.content_profile.*


class ProfileActivity : BaseActivity() {

    private lateinit var profileViewModel: ProfileViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(development.app.checking.R.layout.activity_base)
        setStub(development.app.checking.R.layout.content_profile)
        setAppBar(resources.getString(R.string.app_name))
        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        viewModelSetup(this, profileViewModel)

        /*Prefs is @Injected in BaseActivity*/
        /*dagger injecting Prefs in MyComponent*/
        inject()



        profileViewModel.metaStatus.observe(this, Observer { error ->

            showAlert("Authentication Failed", error, object : Utils.OnClickListener {
                override fun onClick(v: View) {

                }

            }, object : Utils.OnClickListener {
                override fun onClick(v: View) {

                }
            })
        })


        profileViewModel.userInfo.observe(this, Observer { me ->
            txtUserName.text = me.name
            txtEmail.text = me.email
            txtMobile.text = me.mobile
            if (me.image.isNotEmpty()) {
                Picasso.get().load(me.image).into(imgProfile)
            }


        })

        profileViewModel.getProfile(prefs.getData(KEY_TOKEN))


    }


}
