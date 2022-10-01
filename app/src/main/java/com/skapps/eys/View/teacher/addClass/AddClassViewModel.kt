package com.skapps.eys.View.teacher.addClass

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.skapps.eys.Base.BaseViewModel
import com.skapps.eys.Database.FirebaseDatabase

class AddClassViewModel(application: Application) : BaseViewModel(application) {
    private val firebaseDatabase=FirebaseDatabase(coroutineContext,application)
    private var auth: FirebaseAuth = Firebase.auth
    fun addClass(name:String,department:String,context: Context){
        firebaseDatabase.addClass(auth.currentUser!!.uid,name,department, context)
    }
}