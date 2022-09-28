package com.skapps.eys.View.homeTeacher

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.skapps.eys.R

class HomeTeacherFragment : Fragment() {

    companion object {
        fun newInstance() = HomeTeacherFragment()
    }

    private lateinit var viewModel: HomeTeacherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home_teacher, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeTeacherViewModel::class.java)
        // TODO: Use the ViewModel
    }

}