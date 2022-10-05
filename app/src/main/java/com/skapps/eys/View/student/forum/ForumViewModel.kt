package com.skapps.eys.View.student.forum

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.skapps.eys.Base.BaseViewModel
import com.skapps.eys.Database.TeacherDatabase
import com.skapps.eys.Model.Forum
import com.skapps.eys.Model.Teacher
import com.skapps.eys.Util.succesToast
import kotlinx.coroutines.launch

class ForumViewModel(application: Application):BaseViewModel(application){
    var forumlist = MutableLiveData<ArrayList<Forum>>()
    private var list = ArrayList<Forum>(arrayListOf())
    val dao = TeacherDatabase(application).teacherDao()
    private val dbFirestore = Firebase.firestore

    fun getAllPost() {
        launch {
            dbFirestore.collection("marun").document("posts").collection("post")
                .addSnapshotListener { document, error ->
                    try {
                        if (error != null) {
                            Log.e("getAllPost", "Listen failed.", error)
                            return@addSnapshotListener
                        }
                        if (document != null) {
                            list.clear()
                            for (value in document) {
                                val post = Forum(
                                    value.get("userid").toString(),
                                    value.get("username").toString(),
                                    value.get("text").toString(),
                                    value.get("userphoto").toString(),
                                    value.get("title").toString(),
                                    value.get("image").toString(),
                                    value.get("department").toString()
                                )
                                list.add(post)
                            }
                          forumlist.value=list
                        }
                    } catch (e: Exception) {
                        Log.e("getTaskList",e.printStackTrace().toString())
                    }
                }
        }
    }


    fun addTeacher(context: Context){
        launch {
            val teacher= Teacher("null","null","null","null")
            dao.insert(teacher)
            context.succesToast("eklendi")
        }
    }
}