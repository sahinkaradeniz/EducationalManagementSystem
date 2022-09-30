package com.skapps.eys.View.teacher.settings

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.skapps.eys.R
import com.skapps.eys.databinding.FragmentSettingsTeacherBinding

class SettingsTeacherFragment :Fragment(){
    private lateinit var _binding:FragmentSettingsTeacherBinding
    private val binding get() = _binding
    private lateinit var viewModel: SettingsTeacherViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       _binding=FragmentSettingsTeacherBinding.inflate(inflater,container,false)
        binding.apply {
            editProfile.setOnClickListener {
                findNavController().navigate(R.id.action_settingsTeacherFragment_to_teacherProfileFragment)
            }
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SettingsTeacherViewModel::class.java)

    }

}