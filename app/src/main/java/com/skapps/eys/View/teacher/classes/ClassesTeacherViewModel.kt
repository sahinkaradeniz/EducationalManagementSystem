package com.skapps.eys.View.teacher.classes

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.skapps.eys.Base.BaseViewModel
import com.skapps.eys.Model.Classes
import kotlinx.coroutines.launch
import java.util.*

class ClassesTeacherViewModel(application: Application) :BaseViewModel(application) {
    val classList=MutableLiveData<ArrayList<Classes>>()
    private val dbFireStore= Firebase.firestore
    fun getAllClasses(){
        launch {
            val classes=ArrayList<Classes>(arrayListOf())
            try {
                dbFireStore.collection("marun").document("classes").collection("class").addSnapshotListener { document, error ->
                   if (error!=null){
                       Log.e("getClassList", "Listen failed.",error)
                       return@addSnapshotListener
                   }
                   if (document!=null){
                       classes.clear()
                       for (value in document){
                        //   val time=  value.get("date") as Timestamp
                        //   val date = SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(Date(time.nanoseconds.toLong()))
                           val clas=Classes(
                               value.get("teacherid").toString(),
                               value.get("classid").toString(),
                               value.get("teachername").toString(),
                               value.get("teacherphoto").toString(),
                               value.get("name").toString(),
                               value.get("department").toString()
                           )
                           classes.add(clas)
                       }
                       classList.value=classes
                   }
                }

            }catch (e:Exception){
                Log.e("getAllClasses",e.toString())
            }

        }
    }
}