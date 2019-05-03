package development.app.checking.ui.activity.ml

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaScannerConnection
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.github.florent37.runtimepermission.kotlin.askPermission
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import development.app.checking.R
import development.app.checking.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_mlkit.*
import kotlinx.android.synthetic.main.progress_ly.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*


class MLKitActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mlkit)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        setTitle(R.string.title_activity_ml_lit_image_label)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        fab.setOnClickListener { view ->
            getPermission()
        }
    }

    private fun getPermission() {
        askPermission(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE) {
            if (it.isAccepted) {
                showMsg( toolbar, "Granted")
                showPictureDialog()
            }


        }.onDeclined { e ->
            //at least one permission have been declined by the user
            if (e.hasDenied()) {
                showMsg( toolbar, "Denied")
                //the list of denied permissions
                e.denied.forEach {
                    showMsg( toolbar, "Denied")

                }

                AlertDialog.Builder(this@MLKitActivity)
                    .setMessage("Please accept our permissions")
                    .setPositiveButton("yes") { dialog, which ->
                        e.askAgain();
                    } //ask again
                    .setNegativeButton("no") { dialog, which ->
                        dialog.dismiss();
                    }
                    .show();
            }

            if (e.hasForeverDenied()) {
                showMsg( toolbar, "ForeverDenied")
                //the list of forever denied permissions, user has check 'never ask again'
                e.foreverDenied.forEach {
                    showMsg( toolbar, it)
                }
                // you need to open setting manually if you really need it
                e.goToSettings();
            }

        }
    }

    private fun showPictureDialog() {
        val pictureDialog = AlertDialog.Builder(this)
        pictureDialog.setTitle("Select Action")
        val pictureDialogItems = arrayOf("Select photo from gallery", "Capture photo from camera")
        pictureDialog.setItems(
            pictureDialogItems
        ) { dialog, which ->
            when (which) {
                0 -> selectImageInAlbum()
                1 -> takePhoto()
            }
        }
        pictureDialog.show()
    }

    private fun selectImageInAlbum() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, REQUEST_SELECT_IMAGE_IN_ALBUM)
        }
    }

    private fun takePhoto() {
        val intent1 = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent1.resolveActivity(packageManager) != null) {
            startActivityForResult(intent1, REQUEST_TAKE_PHOTO)
        }
    }

    companion object {
        private val REQUEST_TAKE_PHOTO = 0
        private val REQUEST_SELECT_IMAGE_IN_ALBUM = 1
        private val IMAGE_DIRECTORY = "/baseApp/Images"
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_SELECT_IMAGE_IN_ALBUM) {
                if (data != null) {
                    val contentURI = data!!.data
                    try {
                        val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, contentURI)
                        val path = saveImage(bitmap)

                        Toast.makeText(context, "Image Saved!", Toast.LENGTH_SHORT).show()
                        imageView!!.setImageBitmap(bitmap)
                        detectImage(bitmap)

                    } catch (e: IOException) {
                        e.printStackTrace()
                        Toast.makeText(context, "Failed!", Toast.LENGTH_SHORT).show()
                    }

                }
            } else if (requestCode == REQUEST_TAKE_PHOTO) {
                var thumbnail = data!!.extras!!.get("data") as Bitmap
                imageView!!.setImageBitmap(thumbnail)

                val path = saveImage(thumbnail)
                Toast.makeText(context, "Image Saved!", Toast.LENGTH_SHORT).show()

                val bmOptions = BitmapFactory.Options()
                var bitmap = BitmapFactory.decodeFile(path, bmOptions)
                bitmap = Bitmap.createScaledBitmap(bitmap, 200, 200, true)
                imageView.setImageBitmap(bitmap)

                detectImage(bitmap)
            }
        } else {

        }
    }

    fun saveImage(myBitmap: Bitmap): String {


        val bytes = ByteArrayOutputStream()
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
        val wallpaperDirectory = File(
            (Environment.getExternalStorageDirectory()).toString() + IMAGE_DIRECTORY
        )
        // have the object build the directory structure, if needed.
        Log.d("fee", wallpaperDirectory.toString())
        if (!wallpaperDirectory.exists()) {

            wallpaperDirectory.mkdirs()
        }

        try {
            Log.d("heel", wallpaperDirectory.toString())
            val f = File(
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
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath())

            return f.getAbsolutePath()
        } catch (e1: IOException) {
            e1.printStackTrace()
        }

        return ""
    }

    @SuppressLint("SetTextI18n")
    private fun detectImage(bitmap: Bitmap) {

        progressLy!!.visibility = View.VISIBLE
        val image = FirebaseVisionImage.fromBitmap(bitmap)
        val labeler = FirebaseVision.getInstance().onDeviceImageLabeler

        //1
        labeler.processImage(image).addOnSuccessListener { labels ->
            progressLy!!.visibility = View.GONE
            var stringBuilder = StringBuilder()

            for (label in labels) {
                val text = label.text
                val entityId = label.entityId
                val confidence = label.confidence
                stringBuilder.append("Name : $text    Confidence : $confidence\n")
            }
            txtContent.text = stringBuilder.toString()
        }
            .addOnFailureListener { e ->
                // Task failed with an exception
                txtContent.text = "Task failed with an exception $e"
            }

    }

}
