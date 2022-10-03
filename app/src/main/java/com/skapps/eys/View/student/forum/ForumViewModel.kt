package com.skapps.eys.View.student.forum

import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skapps.eys.Base.BaseViewModel
import com.skapps.eys.Database.TeacherDatabase
import com.skapps.eys.Model.Forum
import com.skapps.eys.Model.Teacher
import com.skapps.eys.Util.succesToast
import kotlinx.coroutines.launch

class ForumViewModel(application: Application):BaseViewModel(application){
    var forumlist = MutableLiveData<ArrayList<Forum>>()
    private var listf = ArrayList<Forum>(arrayListOf())
    val dao = TeacherDatabase(application).teacherDao()
    val f1 = Forum("1",
        "Kazım Yıldız",
        "Programlama chapter 2-5 Arası Vizede çıkacak arkadaşlar",
        "null",
        "Programlama - 2"
    )
    val f2 = Forum(
        "1",
        "Kazım Yıldız",
        "Programlama chapter 2-5 Arası Vizede çıkacak arkadaşlar",
        "null",
        "Programlama - 2"
    )
    val f3 = Forum("1",
        "E.Emre Ülkü",
        "Bu hafta ödev yok arkadaşlar",
        "null",
        "Object O. Programing"
    )
    val f4 = Forum("1",
        "Kazım Yıldız",
        "Programlama chapter 2-5 Arası Vizede çıkacak arkadaşlar",
        "null",
        "Programlama - 2"

    )
    fun getAllList(){
        listf.add(f1)
        listf.add(f2)
        listf.add(f3)
        listf.add(f4)
        listf.add(f1)
        listf.add(f2)
        listf.add(f3)
        listf.add(f4)
        listf.add(f1)
        listf.add(f2)
        listf.add(f3)
        listf.add(f4)
        forumlist.value=listf
    }

    fun addTeacher(context: Context){
        launch {
            val teacher= Teacher("null","null","null","null")
            dao.insert(teacher)
            context.succesToast("eklendi")
        }
    }
}