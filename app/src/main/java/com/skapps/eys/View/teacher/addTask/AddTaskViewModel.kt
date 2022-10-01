package com.skapps.eys.View.teacher.addTask

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import com.skapps.eys.Base.BaseViewModel
import com.skapps.eys.Database.LocalFirebase
import com.skapps.eys.databinding.FragmentAddTaskBinding

class AddTaskViewModel(application: Application) : BaseViewModel(application) {
    private val localFirebase=LocalFirebase(coroutineContext)
    fun addTask(userID:String,teacheName:String,teacherPhoto: String,teacherDepartment:String,taskText:String,document:String,context: Context){
        localFirebase.addTask(userID, teacheName, teacherPhoto, teacherDepartment, taskText, document, context)
    }

}