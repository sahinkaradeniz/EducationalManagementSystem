package com.skapps.eys.View.login

import android.app.Application
import android.content.ContentValues
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.skapps.eys.Base.BaseViewModel
import com.skapps.eys.Database.LocalDatabase
import com.skapps.eys.Util.warningToast

class LoginViewModel(application: Application) :BaseViewModel(application) {
    var loginsuccesful= MutableLiveData<Boolean>()
    private var auth: FirebaseAuth = Firebase.auth
    fun loginUser(password:String,email:String,context: Context){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener{ task ->
                if (task.isSuccessful) {
                    loginsuccesful.value = true
                    // Sign in success, update UI with the signed-in user's information
                    Log.e(ContentValues.TAG,"signInWithEmail:success")
                    val user = auth.currentUser
                    saveuid(user!!.uid,context)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.e(task.exception.toString(), "signInWithEmail:failure")
                    task.exception?.toString()?.let { context.warningToast(it) }
                }
            }
    }
    fun saveuid(uid:String,context: Context){
        val local = LocalDatabase()
        local.setSharedPreference(context,"useruid",uid)
    }

}