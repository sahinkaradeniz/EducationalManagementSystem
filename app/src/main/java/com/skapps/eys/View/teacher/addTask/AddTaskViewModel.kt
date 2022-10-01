package com.skapps.eys.View.teacher.addTask

import android.app.Application
import android.content.Context
import com.skapps.eys.Base.BaseViewModel
import com.skapps.eys.Database.FirebaseDatabase

class AddTaskViewModel(application: Application) : BaseViewModel(application) {
    private val firebaseDatabase=FirebaseDatabase(coroutineContext,application)
    fun addTask(userID:String, taskText:String, document:String, context: Context){
            firebaseDatabase.addTask(userID,taskText, document, context)
    }

}