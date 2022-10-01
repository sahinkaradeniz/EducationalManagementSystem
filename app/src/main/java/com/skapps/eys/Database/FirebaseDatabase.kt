package com.skapps.eys.Database

import android.app.Application
import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.skapps.eys.Model.Classes
import com.skapps.eys.Model.Task
import com.skapps.eys.Model.Teacher
import com.skapps.eys.Util.succesToast
import com.skapps.eys.Util.warningToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.util.*
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.delay as delay

class FirebaseDatabase(override val coroutineContext: CoroutineContext, application: Application) :CoroutineScope{
    private var auth: FirebaseAuth = Firebase.auth
    private val localDatabase=LocalDatabase()
    private val dbFirestore = Firebase.firestore
    private val roomDatabase=TeacherDatabase(application).teacherDao()


    fun addTask(userID:String,taskText:String,document:String,context: Context){
        launch {
            try {
                val teacher=getTeacher(userID)
                val newUUID = UUID.randomUUID().toString()
                val task=Task(teacher.uid,newUUID,teacher.name,teacher.photo,teacher.department, taskText, document)
                dbFirestore.collection(userID).document(newUUID).set(task).addOnSuccessListener { documentReference ->
                    Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ${documentReference}")
                    context.succesToast("Ödev Paylaşıldı")
                }.addOnFailureListener { e ->
                    Log.e(ContentValues.TAG, "Error adding document", e)
                    context.warningToast("İnternet ayarlarınızı kontrol ediniz.")
                }
            }catch (e :Exception){
                Log.e(ContentValues.TAG, "addTask Exception", e)
                context.warningToast("Bir sorun oluştu.")
            }

        }
    }

    suspend fun getTeacher(userID: String):Teacher{
        return roomDatabase.getTeacher(userID)
    }

    fun addClass(userID: String,name:String,department:String,context: Context){
        try {
            launch {
                val classID=getRandUid(10)
                 val classes=Classes(name,department,userID,Calendar.DATE.toString(),classID)
                 dbFirestore.collection(userID).document(classID).set(classes).addOnSuccessListener {documentReference ->
                  context.succesToast("Sınıf Oluşturuldu")
                 }.addOnFailureListener {
                     Log.e(ContentValues.TAG, "Error adding document addClass", it)
                     context.warningToast("İnternet ayarlarınızı kontrol ediniz.")
                 }
              }
            }catch (e:Exception){
            Log.e(ContentValues.TAG, "addTask Exception", e)
            context.warningToast("Bir sorun oluştu.")
        }
    }

    fun getTeacherw(userID: String,context: Context):Teacher{
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
    fun getRandUid(n: Int): String
    {
        val characterSet = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"

        val random = Random(System.nanoTime())
        val password = StringBuilder()

        for (i in 0 until n)
        {
            val rIndex = random.nextInt(characterSet.length)
            password.append(characterSet[rIndex])
        }

        return password.toString()
    }

}