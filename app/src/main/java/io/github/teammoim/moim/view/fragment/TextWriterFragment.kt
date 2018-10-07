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


class TextWriterFragment: BottomSheetDialogFragment(),View.OnClickListener{

    override fun onClick(v: View?) {

    }

    @SuppressLint("RestrictedApi")
    override fun setupDialog(dialog: Dialog?, style: Int) {
        super.setupDialog(dialog, style)
        val contentView = View.inflate(context, R.layout.fragment_textwriter, null)
        dialog?.setContentView(contentView)
        val button = contentView.findViewById(R.id.sendButton) as Button
        button.setOnClickListener {
            App.INSTANCE.timelineArray.add(TimelineModel(FirebaseManager.getUserEmail()!!,"",contentView.chatEditText.text.toString()))
            this.dismiss()
        }
    }
}
