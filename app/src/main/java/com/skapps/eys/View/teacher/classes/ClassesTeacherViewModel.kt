package com.skapps.eys.View.teacher.classes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skapps.eys.Model.Classes

class ClassesTeacherViewModel : ViewModel() {
    val classList=MutableLiveData<ArrayList<Classes>>()
    private val listclass=ArrayList<Classes>(arrayListOf())
    val c1=Classes("Sınıf 1","29.09.1999","29.09.1999","29.09.1999","29.09.1999")
    val c4=Classes("Test Sınıfı","29.09.1999","29.09.1999","29.09.1999","29.09.1999")

    fun getAllList(){
        listclass.add(c1)
        listclass.add(c4)
        classList.value=listclass
    }
}