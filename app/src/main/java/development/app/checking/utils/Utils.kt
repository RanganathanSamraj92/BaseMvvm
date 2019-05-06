package development.app.checking.utils

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View
import androidx.core.content.ContextCompat.getSystemService
import java.text.FieldPosition

open class Utils {
    interface OnClickListener {
        fun onClick(v: View)
    }

    interface OnItemClickListener {
        fun onClick(v: View, item: Any)
    }

    companion object {
        public fun vibrate(context: Context) {
            val vibratorService = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibratorService.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                vibratorService.vibrate(50)
            }
        }
    }

}