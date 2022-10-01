package com.skapps.eys.Database

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.skapps.eys.Model.Task
import com.skapps.eys.Model.Teacher
import com.skapps.eys.Util.succesToast
import com.skapps.eys.Util.warningToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.delay as delay

class LocalFirebase(override val coroutineContext: CoroutineContext) :CoroutineScope{
    private var auth: FirebaseAuth = Firebase.auth
    private val localDatabase=LocalDatabase()
    private val dbFirestore = Firebase.firestore

    fun addTask(userID:String,teacheName:String,teacherPhoto: String,teacherDepartment:String,taskText:String,document:String,context: Context){
        launch {
            try {
                val newUUID = UUID.randomUUID().toString()
                val task=Task(userID,newUUID,teacheName, teacherPhoto, teacherDepartment, taskText, document)
                dbFirestore.collection(userID).document(newUUID).set(task).addOnSuccessListener { documentReference ->
                    Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ${documentReference}")
                    context.succesToast("Ödev Paylaşıldı")
                }.addOnFailureListener { e ->
                    Log.w(ContentValues.TAG, "Error adding document", e)
                    context.warningToast("İnternet ayarlarınızı kontrol ediniz.")
                }
            }catch (e :Exception){
                Log.w(ContentValues.TAG, "addTask Exception", e)
                context.warningToast("Bir sorun oluştu.")
            }
        }
    }
    fun getTeacher(userID: String,context: Context):Teacher{
        var teacher=Teacher("null","null","null","null")
        try {
                dbFirestore.collection("teacher").document(userID)
                    .addSnapshotListener { value, error ->
                        launch {
                            if (value != null) {
                                val teacherData = Teacher(
                                    value.get("uid").toString(),
                                    value.get("name").toString(),
                                    value.get("department").toString(),
                                    value.get("photo").toString()
                                )
                                delay(2000)
                                teacher = teacherData
                            }
                        }
                    }
             }catch (e:Exception){
            Log.w(ContentValues.TAG, "getTeacher Exception", e)
            context.warningToast("Bir sorun oluştu.")
             }
        return teacher
    }

}