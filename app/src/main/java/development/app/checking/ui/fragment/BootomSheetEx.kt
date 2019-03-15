package development.app.checking.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import development.app.checking.R
import kotlinx.android.synthetic.main.fragment_bottom_sheet_ex.view.*

class BottomSheetEx : BottomSheetDialogFragment() {

    private var mBottomSheetListener: BottomSheetListener?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_bottom_sheet_ex, container, false)

        //handle clicks
       /* v.btnRetry.setOnClickListener {
            mBottomSheetListener!!.onOptionClick("Call clicked...")
            dismiss() //dismiss bottom sheet when item click
        }*/


        return v
    }

    interface BottomSheetListener{
        fun onOptionClick(text: String)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        try {
            mBottomSheetListener = context as BottomSheetListener?
        }
        catch (e: ClassCastException){
            throw ClassCastException(context!!.toString())
        }

    }


}