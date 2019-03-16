package development.app.checking.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import development.app.checking.R
import development.app.checking.model.AndroidVersion
import development.app.checking.utils.Utils

class RecyclerAdapter(private val v: Utils.OnItemClickListener, private val versions: ArrayList<AndroidVersion>) :RecyclerView.Adapter<AndroidVersionsHolder>() {

    override fun onBindViewHolder(holder: AndroidVersionsHolder, position: Int) {
        val version = versions[position]
        holder.bindAndroidVersions(version)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AndroidVersionsHolder {
        val inflatedView = (LayoutInflater.from(parent.context).inflate(R.layout.list_item_android_version, parent, false))
        return AndroidVersionsHolder(v,inflatedView)
    }


    override fun getItemCount() = versions.size

}