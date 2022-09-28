package com.skapps.eys.View.classesTeacher

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.skapps.eys.R

class ClassesTeacherFragment : Fragment() {

    companion object {
        fun newInstance() = ClassesTeacherFragment()
    }

    private lateinit var viewModel: ClassesTeacherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_classes_teacher, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ClassesTeacherViewModel::class.java)
        // TODO: Use the ViewModel
    }

}