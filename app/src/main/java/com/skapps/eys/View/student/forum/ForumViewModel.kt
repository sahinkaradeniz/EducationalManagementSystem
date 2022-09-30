package com.skapps.eys.View.student.forum

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skapps.eys.Model.Forum

class ForumViewModel : ViewModel() {
    var forumlist = MutableLiveData<ArrayList<Forum>>()
    private var listf = ArrayList<Forum>(arrayListOf())
    val f1 = Forum(
        "Kazım Yıldız",
        "Programlama chapter 2-5 Arası Vizede çıkacak arkadaşlar",
        "null",
        "Programlama - 2"
    )
    val f2 = Forum(
        "Kazım Yıldız",
        "Programlama chapter 2-5 Arası Vizede çıkacak arkadaşlar",
        "null",
        "Programlama - 2"
    )
    val f3 = Forum(
        "E.Emre Ülkü",
        "Bu hafta ödev yok arkadaşlar",
        "null",
        "Object O. Programing"
    )
    val f4 = Forum(
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
}