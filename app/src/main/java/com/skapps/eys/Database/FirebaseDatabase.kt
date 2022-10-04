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
import com.skapps.eys.Util.succesAlert
import com.skapps.eys.Util.succesToast
import com.skapps.eys.Util.warningAlert
import com.skapps.eys.Util.warningToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.util.*
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.delay as delay

class FirebaseDatabase(override val coroutineContext: CoroutineContext, application: Application) :CoroutineScope{

    private val dbFirestore = Firebase.firestore
    private val roomDatabase=TeacherDatabase(application).teacherDao()

    suspend fun getTeacher(userID: String):Teacher{
        return roomDatabase.getTeacher(userID)
    }

    fun addClass(userID: String,name:String,department:String,context: Context){
        try {
            launch {
                val classID=getRandUid(10)
                 val classes=Classes(name,department,userID,Calendar.DATE.toString(),classID)
                 dbFirestore.collection(userID).document(classID).set(classes).addOnSuccessListener {documentReference ->
                     context.succesAlert("Sınıf Oluşturuldu!","Tamam")
                 }.addOnFailureListener {
                     Log.e(ContentValues.TAG, "Error adding document addClass", it)
                     context.warningAlert("İnternet Ayarlarınızı Kontol Ediniz","Tamam")
                 }
              }
            }catch (e:Exception){
            Log.e(ContentValues.TAG, "addTask Exception", e)
            context.warningAlert("Bir sorun oluştu.","Kapat")
        }
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