package com.skapps.eys.View.teacher.addTask

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.skapps.eys.Database.FirebaseDatabase
import com.skapps.eys.Util.toast
import com.skapps.eys.databinding.FragmentAddTaskBinding


class AddTaskFragment : DialogFragment() {
    private lateinit var _binding:FragmentAddTaskBinding
    private val binding get() = _binding
    private lateinit var viewModel: AddTaskViewModel
    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseDatabase: FirebaseDatabase
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding= FragmentAddTaskBinding.inflate(inflater,container,false)
        isCancelable=false
        binding.addTaskImage.setOnClickListener {
            requireContext().toast("Gönderiliyor...")
            viewModel.addTask(
                "null",
                binding.addTaskText.editText?.text.toString(),
                "no doc",
                requireContext()
            )
        }

        binding.addtaskClose.setOnClickListener {
            dialog?.dismiss()
        }
        dialog?.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        auth = Firebase.auth
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddTaskViewModel::class.java)

    }

}