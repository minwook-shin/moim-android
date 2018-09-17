package io.github.teammoim.moim.common

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

object FirebaseManager{
    private val database by lazy { FirebaseDatabase.getInstance() }
    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    fun getRef(ref: String): DatabaseReference = database.getReference(ref)

    fun getAuth(): FirebaseAuth = mAuth

    fun getCredential(currentPassword: String) = EmailAuthProvider.getCredential(getUserEmail()!!, currentPassword)!!

    fun getUserUid(): String? = getAuth().currentUser?.uid

    fun getUserEmail(): String? = getAuth().currentUser?.email

    fun signOut() = getAuth().signOut()

    fun getEmailSignUp(email: String, password: String): Task<AuthResult> {
        return mAuth. createUserWithEmailAndPassword(email, password)
    }

    fun getEmailLogIn(email: String, password: String): Task<AuthResult> {
        return mAuth.signInWithEmailAndPassword(email, password)
    }

}