package development.app.checking.ui.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import development.app.checking.model.AndroidVersion
import development.app.checking.utils.Utils
import kotlinx.android.synthetic.main.list_item_android_version.view.*
import java.lang.Exception

class AndroidVersionsHolder(val v1: Utils.OnItemClickListener, v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
    //2
    private var view: View = v
    private var androidVersion: AndroidVersion? = null

    //3
    init {
        v.setOnClickListener(this)
    }

    //4
    override fun onClick(v: View) {
        Log.d("RecyclerView", "CLICK!")
        v1.onClick(v, androidVersion!!)
    }

    @SuppressLint("SetTextI18n")
    fun bindAndroidVersions(androidVersion: AndroidVersion) {
        this.androidVersion = androidVersion
            //Picasso.with(view.context).load(photo.url).into(view.itemImage)
        Picasso.get().load(androidVersion.images.get(0).image).into(view.imgAndroidVersionIcon,
            object : Callback {
                override fun onError(e: Exception?) {
                }

                override fun onSuccess() {
                   // animateImageView()
                }

            })
        view.txtVersionName.text = "${androidVersion.name} "
        view.txtDescription.text = "version code : ${androidVersion.version_code} \nApi level: ${androidVersion.api_level} "
    }

    companion object {
        //5
        private val PHOTO_KEY = "PHOTO"


    }
}
