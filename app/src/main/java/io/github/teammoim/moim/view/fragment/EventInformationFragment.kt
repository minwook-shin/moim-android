package io.github.teammoim.moim.view.fragment

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import io.github.teammoim.moim.R
import io.github.teammoim.moim.common.FirebaseManager
import kotlinx.android.synthetic.main.activity_search.view.*
import kotlinx.android.synthetic.main.fragment_event_infomation.*
import kotlinx.android.synthetic.main.fragment_event_infomation.view.*
import org.jetbrains.anko.design.snackbar
import org.osmdroid.views.overlay.OverlayItem
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("ValidFragment")
class EventInformationFragment  constructor(private var overlayItem: OverlayItem) : BottomSheetDialogFragment(){
    @SuppressLint("RestrictedApi", "SimpleDateFormat")
    override fun setupDialog(dialog: Dialog?, style: Int) {
        super.setupDialog(dialog, style)
        val contentView = View.inflate(context, R.layout.fragment_event_infomation, null)
        val cal = Calendar.getInstance()
        contentView.eventName.text = overlayItem.title
        contentView.Description.text = overlayItem.snippet
        cal.timeInMillis = overlayItem.uid.toLong()

        val formatter = SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS")
        dialog?.setContentView(contentView)

        contentView.Date.text =  formatter.format(cal.time)
    }
}