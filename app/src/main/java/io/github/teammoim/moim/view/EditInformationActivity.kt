package io.github.teammoim.moim.view

import android.os.Bundle
import io.github.teammoim.moim.R
import io.github.teammoim.moim.base.BaseActivity
import io.github.teammoim.moim.common.FirebaseManager
import kotlinx.android.synthetic.main.activity_fill_information.*
import org.jetbrains.anko.design.snackbar

class EditInformationActivity:BaseActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill_information)

        pushButton.setOnClickListener {
            if (!emailText.text.isNullOrBlank()){
                FirebaseManager.getRef("users")?.child(FirebaseManager.getUserUid()!!)?.child("email")?.setValue(emailText.text.toString())
            }
            if (!nameText.text.isNullOrBlank()){
                FirebaseManager.getRef("users")?.child(FirebaseManager.getUserUid()!!)?.child("name")?.setValue(nameText.text.toString())
            }
            if (!nicknameText.text.isNullOrBlank()){
                FirebaseManager.getRef("users")?.child(FirebaseManager.getUserUid()!!)?.child("nickname")?.setValue(nicknameText.text.toString())

            }
            if (!birthdayText.text.isNullOrBlank()){
                FirebaseManager.getRef("users")?.child(FirebaseManager.getUserUid()!!)?.child("birthday")?.setValue(birthdayText.text.toString())

            }
            if (!genderText.text.isNullOrBlank()){
                FirebaseManager.getRef("users")?.child(FirebaseManager.getUserUid()!!)?.child("gender")?.setValue(genderText.text.toString())

            }
            if (!phoneText.text.isNullOrBlank()){
                FirebaseManager.getRef("users")?.child(FirebaseManager.getUserUid()!!)?.child("phone")?.setValue(phoneText.text.toString())

            }
            pushButton.snackbar("입력한 정보만 새로 추가되었습니다.")
        }


    }
}