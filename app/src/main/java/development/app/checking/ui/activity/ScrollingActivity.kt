package development.app.checking.ui.activity

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.squareup.picasso.Picasso
import development.app.checking.R
import development.app.checking.model.AndroidVersion
import development.app.checking.ui.base.BaseActivity
import development.app.checking.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.app_bar_collapse.*
import kotlinx.android.synthetic.main.content_scrolling.*

class ScrollingActivity : BaseActivity() {

    private lateinit var viewModel: DetailViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_collapse)
        setStub(R.layout.content_scrolling)
        setAppBarCollapse("Details")
        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        viewModelSetup(this, viewModel)

        val version = intent.extras.getSerializable("intent_data") as? AndroidVersion
        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        viewModelSetup(this, viewModel)

        viewModel.versionDetail.observe(this, Observer { details ->
            txtScroll.text = details.description
            toolbar_layout.title = version!!.name
            Picasso.get().load(version!!.images[0].image).into(imgCollapse)

        })
        viewModel.fetchDetails()


    }


}
