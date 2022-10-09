package com.skapps.eys.View.student.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.skapps.eys.Base.BaseViewModel
import com.skapps.eys.Model.Task
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : BaseViewModel(application) {
    private val dbFireStore= Firebase.firestore
    var taskList= MutableLiveData<ArrayList<Task>>()

    fun getTaskList(){
        launch {
            //  collection("tasks").document().collection("12").document()
            //dbFirestore.collection("marun").document("tasks").collection("task")
            val tasks=ArrayList<Task>(arrayListOf())
            dbFireStore.collection("marun").document("tasks").collection("task").orderBy("date",
                Query.Direction.DESCENDING)
                .addSnapshotListener { document, error ->
                    try {
                        if (error!=null){
                            Log.e("getTaskList", "Listen failed.",error)
                            return@addSnapshotListener
                        }
                        if (document!=null){
                            tasks.clear()
                            for (value in document){
                                //  Log.e("document",document.toString())
                                val task=Task(value.get("taskID").toString(),
                                    value.get("classID").toString(),
                                    value.get("className").toString(),
                                    value.get("teacherID").toString(),
                                    value.get("teacherName").toString(),
                                    value.get("teacherPhoto").toString(),
                                    value.get("teacherDepartment").toString(),
                                    value.get("taskText").toString(),
                                    value.get("taskImage").toString(),
                                    value.get("document").toString(),
                                    value.get("date").toString())
                                tasks.add(task)
                            }
                        }
                    }catch (e :Exception){
                        Log.e("getTaskList",e.printStackTrace().toString())
                    }
                    taskList.value=tasks
                }
        }
    }

}