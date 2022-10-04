package com.skapps.eys.View.teacher.addTask

import android.app.Application
import android.content.ContentValues
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ServerTimestamp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.options
import com.google.firestore.v1.DocumentTransform
import com.skapps.eys.Base.BaseViewModel
import com.skapps.eys.Database.FirebaseDatabase
import com.skapps.eys.Database.LocalDatabase
import com.skapps.eys.Model.Task
import com.skapps.eys.Model.Teacher
import com.skapps.eys.Util.succesAlert
import com.skapps.eys.Util.warningAlert
import com.skapps.eys.Util.warningToast
import kotlinx.coroutines.launch
import java.util.*
class AddTaskViewModel(application: Application) : BaseViewModel(application) {
    var closeAlert=MutableLiveData(false)
    private val dbFirestore = Firebase.firestore
    private val  firebaseDatabase=FirebaseDatabase(coroutineContext, application)

    fun addTask(taskText:String,document:String,context: Context,teacher: Teacher){
        launch {
            try {
                val newUUID = UUID.randomUUID().toString()
                val task= hashMapOf( "teacherid" to teacher.id
                    ,"taskid" to newUUID,
                    "teachername" to teacher.name,
                    "teacherphoto" to teacher.photo,
                    "teacherdepartment" to teacher.department,
                    "tasktext" to taskText,
                    "document" to document,
                    "date " to FieldValue.serverTimestamp())
                dbFirestore.collection("marun").document("tasks").collection("task").add(task).addOnSuccessListener { documentReference ->
                    Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ${documentReference}")
                    context.succesAlert("Ödev Paylaşıldı","Tamam")
                    closeAlert.value=true
                }.addOnFailureListener { e ->
                    Log.e(ContentValues.TAG, "Error adding document", e)
                    context.warningAlert("İnternet ayarlarınızı kontrol ediniz.","Tamam")
                }
            }catch (e :Exception){
                Log.e(ContentValues.TAG, "addTask Exception", e)
                context.warningAlert("Bir sorun oluştu","Kapat")
            }
        }
    }
    fun sendTask(taskText:String,document:String,context: Context){
        try {
            dbFirestore.collection("marun").document("teachers")
                .addSnapshotListener { value, error ->
                    if (value != null) {
                        val teacherData = Teacher(
                            value.get("uid").toString(),
                            value.get("name").toString(),
                            value.get("department").toString(),
                            value.get("photo").toString()
                        )
                        addTask(taskText, document, context, teacherData)
                    }
                }
        }catch (e:Exception){
            Log.w(ContentValues.TAG, "getTeacher Exception", e)
            context.warningAlert("Bir sorun oluştu","Kapat")
        }
    }


}