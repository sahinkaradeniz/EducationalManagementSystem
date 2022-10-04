package com.skapps.eys.View.teacher.addPost

import android.app.Application
import android.content.ContentValues
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.skapps.eys.Base.BaseViewModel
import com.skapps.eys.Util.succesToast
import com.skapps.eys.Util.warningAlert
import kotlinx.coroutines.launch

class AddPostViewModel (application: Application): BaseViewModel(application) {
    var closeAlert= MutableLiveData(false)
    private val dbFirestore = Firebase.firestore
    private var user= Firebase.auth.currentUser

    fun addPost(text:String,title:String,image:String,context: Context){
        launch {
            //TODO("User id and photo")
            try {  val post = hashMapOf( "userid" to user?.uid,
                "username" to "Kazım Yıldız",
                "userphoto" to user?.photoUrl,
                "text" to text,
                "title" to title,
                "image" to image
                 )
                  dbFirestore.collection("marun").document("posts").collection("post").add(post) .addOnCompleteListener {
                      context.succesToast("Paylaşıldı!")
                      closeAlert.value=true
                  }.addOnFailureListener {
                      Log.e(ContentValues.TAG, "Error adding document addClass", it)
                      context.warningAlert("İnternet Ayarlarınızı Kontol Ediniz","Tamam")
                  }
                }catch (e :Exception){
                Log.e(ContentValues.TAG, "addTask Exception", e)
                context.warningAlert("Bir sorun oluştu.","Kapat")
            }
        }
    }
}