package com.skapps.eys.View.settingsTeacher

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.skapps.eys.R

class SettingsTeacherFragment : Fragment() {

    companion object {
        fun newInstance() = SettingsTeacherFragment()
    }

    private lateinit var viewModel: SettingsTeacherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings_teacher, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SettingsTeacherViewModel::class.java)
        // TODO: Use the ViewModel
    }

}