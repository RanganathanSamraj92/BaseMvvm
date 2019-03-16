package development.app.checking.utils

import android.view.View
import java.text.FieldPosition

open class Utils {
    interface OnClickListener {
        fun onClick(v: View)
    }

    interface OnItemClickListener {
        fun onClick(v: View,item: Any)
    }
}