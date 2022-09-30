package com.skapps.eys.View.teacher.home

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.skapps.eys.Base.BaseViewModel
import com.skapps.eys.Model.Task

class HomeTeacherViewModel(application: Application) : BaseViewModel(application) {
    var taskList= MutableLiveData<ArrayList<Task>>()
    var task= Task("deneme","deneme","deneme","deneme","deneme","deneme")
    var task2= Task("deneme","deneme","deneme","deneme","deneme","deneme")
    var task3= Task("deneme","deneme","deneme","deneme","deneme","deneme")
    var task4= Task("deneme","deneme","deneme","deneme","deneme","deneme")
    var task5= Task("deneme","deneme","deneme","deneme","deneme","deneme")
    var task6= Task("deneme","deneme","deneme","deneme","deneme","deneme")

    fun getTaskList(){
        var taskList2=ArrayList<Task>(arrayListOf())
        taskList2.add(task)
        taskList2.add(task2)
        taskList2.add(task3)
        taskList2.add(task4)
        taskList2.add(task5)
        taskList2.add(task6)
        taskList2.add(task)
        taskList2.add(task2)
        taskList2.add(task3)
        taskList2.add(task4)
        taskList2.add(task5)
        taskList2.add(task6)
        taskList2.add(task)
        taskList2.add(task2)
        taskList2.add(task3)
        taskList2.add(task4)
        taskList2.add(task5)
        taskList2.add(task6)
        taskList.value=taskList2
    }
}