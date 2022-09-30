package com.skapps.eys.View.classesTeacher

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skapps.eys.Model.Classes

class ClassesTeacherViewModel : ViewModel() {
    val classList=MutableLiveData<ArrayList<Classes>>()
    private val listclass=ArrayList<Classes>(arrayListOf())
    val c1=Classes("Sınıf 1","29.09.1999")
    val c4=Classes("Test Sınıfı","29.09.1999")
    val c5=Classes("Edebiyat","29.09.1999")
    val c6=Classes("Programlama ","29.09.1999")
    val c7=Classes("Sınıf 2302","29.09.1999")
    fun getAllList(){
        listclass.add(c1)
        listclass.add(c6)
        listclass.add(c7)
        listclass.add(c4)
        listclass.add(c5)
        classList.value=listclass
    }
}