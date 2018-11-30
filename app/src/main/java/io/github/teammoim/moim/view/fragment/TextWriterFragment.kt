package io.github.teammoim.moim.view.fragment

import android.annotation.SuppressLint
import android.app.Dialog
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import io.github.teammoim.moim.App
import io.github.teammoim.moim.R
import io.github.teammoim.moim.TimelineModel
import io.github.teammoim.moim.common.FirebaseManager
import kotlinx.android.synthetic.main.fragment_textwriter.*
import kotlinx.android.synthetic.main.fragment_textwriter.view.*
import org.jetbrains.anko.toast
import java.text.SimpleDateFormat
import java.util.*


class TextWriterFragment: BottomSheetDialogFragment(),View.OnClickListener{

    override fun onClick(v: View?) {

    }

    @SuppressLint("RestrictedApi", "SimpleDateFormat")
    override fun setupDialog(dialog: Dialog?, style: Int) {
        super.setupDialog(dialog, style)
        val contentView = View.inflate(context, R.layout.fragment_textwriter, null)
        dialog?.setContentView(contentView)
        val button = contentView.findViewById(R.id.sendButton) as Button
        button.setOnClickListener {
            val cal = Calendar.getInstance()
//            val sdf = SimpleDateFormat("HH:mm:ss")

            App.INSTANCE.timelineArray.add(TimelineModel(FirebaseManager.getUserEmail()!!,0.0,contentView.chatEditText.text.toString(),cal.timeInMillis.toString(),FirebaseManager.getUserUid().toString()))
            FirebaseManager.getRef("post")?.child(FirebaseManager.getUserUid().toString())?.child(cal.timeInMillis.toString())?.child("uid")?.setValue(FirebaseManager.getUserUid())
            FirebaseManager.getRef("post")?.child(FirebaseManager.getUserUid().toString())?.child(cal.timeInMillis.toString())?.child("text")?.setValue(contentView.chatEditText.text.toString())
            FirebaseManager.getRef("post")?.child(FirebaseManager.getUserUid().toString())?.child(cal.timeInMillis.toString())?.child("url")?.setValue("")
            FirebaseManager.getRef("post")?.child(FirebaseManager.getUserUid().toString())?.child(cal.timeInMillis.toString())?.child("postId")?.setValue(cal.timeInMillis.toString())

            this.dismiss()
        }
    }
}
