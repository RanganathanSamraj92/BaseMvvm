package development.app.checking.ui.activity.android_verisons

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


class ScrollingActivity : BaseActivity() {

    private lateinit var viewModel: DetailViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(development.app.checking.R.layout.activity_base_collapse)
        setStub(development.app.checking.R.layout.content_scrolling)
        setAppBarCollapse("Details")
        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        viewModelSetup(this, viewModel)

        val version = intent.extras.getSerializable("intent_data") as? AndroidVersion
        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        viewModelSetup(this, viewModel)

        viewModel.versionDetail.observe(this, Observer { details ->
            txtScroll.text = details.description
            toolbar_layout.title = version!!.name
            Picasso.get().load(version!!.images[0].image).into(object : com.squareup.picasso.Target {
                override fun onBitmapFailed(e: java.lang.Exception?, errorDrawable: Drawable?) {
                }

                override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                }

                override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                    dynamicColor(bitmap)
                    imgCollapse.setImageBitmap(bitmap)

                }
            })


        })
        viewModel.fetchDetails()


    }


    private fun dynamicColor(bitmap: Bitmap?) {
        Palette.from(bitmap!!).generate { palette ->
            window.statusBarColor =
                palette!!.getMutedColor(resources.getColor(development.app.checking.R.color.colorPrimaryDark))
            toolbar_layout!!.setContentScrimColor(palette!!.getMutedColor(resources.getColor(development.app.checking.R.color.colorPrimary)))
            toolbar_layout!!.setStatusBarScrimColor(palette.getMutedColor(resources.getColor(development.app.checking.R.color.colorPrimaryDark)))
        }

    }


}
