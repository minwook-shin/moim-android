package io.github.teammoim.moim.view

import android.os.Bundle
import io.github.teammoim.moim.App
import io.github.teammoim.moim.R
import io.github.teammoim.moim.base.BaseActivity
import io.github.teammoim.moim.common.FirebaseManager
import kotlinx.android.synthetic.main.activity_profile.*
import org.jetbrains.anko.startActivity

class ProfileActivity:BaseActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        overridePendingTransition(R.anim.left_to_right,R.anim.left_to_right)

        prevButton.setOnClickListener {
            this.onBackPressed()
        }

        editInfo_button.setOnClickListener {
            startActivity<EditInformationActivity>()
        }

        nameTextView.text = App.INSTANCE.myInfo.name
        emailTextView.text = App.INSTANCE.myInfo.Email
        nickNameTextView.text = App.INSTANCE.myInfo.nickname
        genderTextView.text = App.INSTANCE.myInfo.gender
        birthdayTextView.text = App.INSTANCE.myInfo.birthday
        phoneTextView.text = App.INSTANCE.myInfo.phone


    }
}