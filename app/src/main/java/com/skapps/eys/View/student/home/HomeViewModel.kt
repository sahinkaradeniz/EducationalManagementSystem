package com.skapps.eys.View.student.home

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.skapps.eys.Base.BaseViewModel
import com.skapps.eys.Model.Task

class HomeViewModel(application: Application) : BaseViewModel(application) {
    var taskList=MutableLiveData<ArrayList<Task>>()

    fun getTaskList(){
        var taskList2=ArrayList<Task>(arrayListOf())

        taskList.value=taskList2
    }

}