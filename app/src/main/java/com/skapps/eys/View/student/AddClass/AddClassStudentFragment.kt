package com.skapps.eys.View.student.AddClass

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.skapps.eys.R
import com.skapps.eys.databinding.FragmentAddClassStudentBinding

class AddClassStudentFragment : BottomSheetDialogFragment() {

    private lateinit var _binding:FragmentAddClassStudentBinding
    private val binding get() = _binding
    private lateinit var viewModel: AddClassStudentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL,R.style.AppBottomSheetDialogTheme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       _binding= FragmentAddClassStudentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddClassStudentViewModel::class.java)
        // TODO: Use the ViewModel
    }

}