package io.github.teammoim.moim.view.fragment

import android.annotation.SuppressLint
import android.app.Dialog
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import io.github.teammoim.moim.R

class EventInfomationFragment: BottomSheetDialogFragment(){
    @SuppressLint("RestrictedApi")
    override fun setupDialog(dialog: Dialog?, style: Int) {
        super.setupDialog(dialog, style)
        val contentView = View.inflate(context, R.layout.fragment_event_infomation, null)
        dialog?.setContentView(contentView)
    }
}