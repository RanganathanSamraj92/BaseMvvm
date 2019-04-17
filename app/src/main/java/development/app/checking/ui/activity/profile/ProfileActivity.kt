package development.app.checking.ui.activity.profile

import android.content.Intent
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.media.session.MediaSessionCompat.KEY_TOKEN
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.squareup.picasso.Picasso
import development.app.checking.R
import development.app.checking.pref.PrefMgr
import development.app.checking.pref.Prefs
import development.app.checking.ui.base.BaseActivity
import development.app.checking.utils.Utils
import development.app.checking.viewmodel.ImageUploadViewModel
import development.app.checking.viewmodel.ProfileViewModel
import kotlinx.android.synthetic.main.content_profile.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.MediaType


class ProfileActivity : BaseActivity() {

    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var imageUploadViewModel: ImageUploadViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(development.app.checking.R.layout.activity_base)
        setStub(development.app.checking.R.layout.content_profile)
        setAppBar(resources.getString(R.string.title_activity_profile))
        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        viewModelSetup(this, profileViewModel)

        imageUploadViewModel = ViewModelProviders.of(this).get(ImageUploadViewModel::class.java)
        viewModelSetup(this, imageUploadViewModel)

        /*Prefs is @Injected in BaseActivity*/
        /*dagger injecting Prefs in MyComponent*/
        inject()


        if (prefs.getData("imageProfile").isNotEmpty())
            Picasso.get().load(prefs.getData("imageProfile")).into(imgProfile)


        fab.setOnClickListener {

            //imageUploadViewModel.uploadImage()

            selectImageInAlbum()
        }

        imageUploadViewModel.uploadResModel.observe(this, Observer { result ->

            showAlert(result.message, result.imageUrl, object : Utils.OnClickListener {
                override fun onClick(v: View) {
                    prefs.putData("imageProfile", result.imageUrl)
                }

            }, object : Utils.OnClickListener {
                override fun onClick(v: View) {

                }
            })
        })
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

        profileViewModel.getProfile(prefs.getData(PrefMgr.KEY_ACCESS_TOKEN))


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        /* if (resultCode == this.RESULT_CANCELED)
         {
         return
         }*/
        if (requestCode == REQUEST_SELECT_IMAGE_IN_ALBUM) {
            if (data != null) {


                val contentURI = data!!.data
                try {
                    val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, contentURI)
                    val file = saveImage(bitmap)
                    imgProfile!!.setImageBitmap(bitmap)
                    val requestFile = RequestBody.create(MediaType.parse("image/jpeg"), file)

                    val body = MultipartBody.Part.createFormData("file", "image.jpg", requestFile)
                    val name = RequestBody.create(MediaType.parse("text/plain"), "profile_image_name")

                    //al part = MultipartBody.Part.createFormData("file", file.getName(), requestFile)
                    imageUploadViewModel.uploadImage(prefs.getData(PrefMgr.KEY_ACCESS_TOKEN),body, name)

                } catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(this@ProfileActivity, "Failed!", Toast.LENGTH_SHORT).show()
                }

            }

        } else if (requestCode == REQUEST_TAKE_PHOTO) {
            val thumbnail = data!!.extras!!.get("data") as Bitmap
            imgProfile!!.setImageBitmap(thumbnail)
            saveImage(thumbnail)
            Toast.makeText(this@ProfileActivity, "Image Saved!", Toast.LENGTH_SHORT).show()
        }


    }


    fun saveImage(myBitmap: Bitmap): File {
        lateinit var f: File
        val bytes = ByteArrayOutputStream()
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
        val wallpaperDirectory = File(
            (Environment.getExternalStorageDirectory()).toString() + IMAGE_DIRECTORY
        )
        // have the object build the directory structure, if needed.
        makeLog(wallpaperDirectory.toString())
        if (!wallpaperDirectory.exists()) {

            wallpaperDirectory.mkdirs()
        }

        try {
            makeLog(wallpaperDirectory.toString())
            f = File(
                wallpaperDirectory, ((Calendar.getInstance()
                    .getTimeInMillis()).toString() + ".jpg")
            )
            f.createNewFile()
            val fo = FileOutputStream(f)
            fo.write(bytes.toByteArray())
            MediaScannerConnection.scanFile(
                this,
                arrayOf(f.getPath()),
                arrayOf("image/jpeg"), null
            )
            fo.close()
            makeLog("File Saved::--->" + f.getAbsolutePath())

            return f
        } catch (e1: IOException) {
            e1.printStackTrace()
        }

        return f
    }

    fun selectImageInAlbum() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, REQUEST_SELECT_IMAGE_IN_ALBUM)
        }
    }

    fun takePhoto() {
        val intent1 = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent1.resolveActivity(packageManager) != null) {
            startActivityForResult(intent1, REQUEST_TAKE_PHOTO)
        }
    }

    companion object {
        private val REQUEST_TAKE_PHOTO = 0
        private val REQUEST_SELECT_IMAGE_IN_ALBUM = 1
        private val IMAGE_DIRECTORY = "/imageDocuments"
    }


}
