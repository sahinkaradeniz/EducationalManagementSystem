package com.skapps.eys.View.teacher.addClass

import android.app.Application
import android.content.ContentValues
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.skapps.eys.Base.BaseViewModel
import com.skapps.eys.Database.FirebaseDatabase
import com.skapps.eys.Model.Classes
import com.skapps.eys.Model.Teacher
import com.skapps.eys.Util.succesAlert
import com.skapps.eys.Util.warningAlert
import kotlinx.coroutines.launch
import java.util.*

class AddClassViewModel(application: Application) : BaseViewModel(application) {
    private val firebaseDatabase=FirebaseDatabase(coroutineContext,application)
    private var auth: FirebaseAuth = Firebase.auth
    private val dbFirestore = Firebase.firestore
    var closeAlert= MutableLiveData(false)
    fun addClass2(name:String,department:String,context: Context){
        launch {
            firebaseDatabase.addClass(auth.currentUser!!.uid,name,department, context)
        }
    }
    fun addClass(teacher: Teacher,department: String,name: String,context: Context){
        try {
            launch {
                val classID=getRandUid(10)
                val classValue= hashMapOf(
                    "teacherid" to teacher.id
                    ,"classid" to classID,
                    "teachername" to teacher.name,
                    "teacherphoto" to teacher.photo,
                    "department" to department ,
                    "name" to name,
                    "date " to FieldValue.serverTimestamp())
                dbFirestore.collection("marun").document("classes").collection(classID).add(classValue).addOnSuccessListener {documentReference ->
                    context.succesAlert("Sınıf Oluşturuldu!","Tamam")
                    closeAlert.value=true
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
    fun createClass(department: String,name: String,context: Context){
        try {
            dbFirestore.collection("marun").document("teachers")
                .addSnapshotListener { value, error ->
                    if (value != null) {
                        val teacherData = Teacher(
                            value.get("teacherid").toString(),
                            value.get("name").toString(),
                            value.get("department").toString(),
                            value.get("photo").toString()
                        )
                        addClass(teacherData,department, name, context)
                    }
                }
        }catch (e:Exception){
            Log.w(ContentValues.TAG, "getTeacher Exception", e)
            context.warningAlert("Bir sorun oluştu","Kapat")
        }
    }
}