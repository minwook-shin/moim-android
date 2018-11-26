package io.github.teammoim.moim.view.fragment

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import io.github.teammoim.moim.App
import io.github.teammoim.moim.CommentModel
import io.github.teammoim.moim.R
import io.github.teammoim.moim.TimelineModel
import io.github.teammoim.moim.adapter.CommentRecyclerViewAdapter
import io.github.teammoim.moim.common.FirebaseManager
import kotlinx.android.synthetic.main.fragment_comment.*
import kotlinx.android.synthetic.main.fragment_comment.view.*
import kotlinx.android.synthetic.main.fragment_textwriter.*
import kotlinx.android.synthetic.main.fragment_textwriter.view.*
import kotlinx.android.synthetic.main.fragment_timeline.*
import org.jetbrains.anko.toast
import java.text.SimpleDateFormat
import java.util.*
import android.util.DisplayMetrics
import android.content.Context.WINDOW_SERVICE
import androidx.core.content.ContextCompat.getSystemService
import android.view.WindowManager
import androidx.recyclerview.widget.RecyclerView






@SuppressLint("ValidFragment")
class CommentFragment(val post : String): BottomSheetDialogFragment(),View.OnClickListener{
    private var commentList: ArrayList<CommentModel> = ArrayList()
    override fun onClick(v: View?) {

    }

    @SuppressLint("RestrictedApi", "SimpleDateFormat")
    override fun setupDialog(dialog: Dialog?, style: Int) {
        commentList.add(CommentModel("test","testest"))
        super.setupDialog(dialog, style)
        val contentView = View.inflate(context, R.layout.fragment_comment, null)
        dialog?.setContentView(contentView)
        val button = contentView.findViewById(R.id.sendButton) as Button
        button.setOnClickListener {
            val cal = Calendar.getInstance()
//            val sdf = SimpleDateFormat("HH:mm:ss")

            FirebaseManager.getRef("post")?.child(FirebaseManager.getUserUid().toString())?.child(post)?.child("comments")?.child("uid")?.setValue(FirebaseManager.getUserUid())
            FirebaseManager.getRef("post")?.child(FirebaseManager.getUserUid().toString())?.child(post)?.child("comments")?.child("text")?.setValue(contentView.commentEditText.text.toString())

            this.dismiss()
        }

        val adapter = CommentRecyclerViewAdapter(activity!!.applicationContext, commentList)

        val recyclerView = contentView!!.findViewById(R.id.comment_list) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter


    }
}
