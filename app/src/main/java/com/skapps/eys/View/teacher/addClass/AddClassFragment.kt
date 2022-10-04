package com.skapps.eys.View.teacher.addClass

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.skapps.eys.R
import com.skapps.eys.Util.succesAlert
import com.skapps.eys.Util.toast
import com.skapps.eys.databinding.FragmentAddClassBinding

class AddClassFragment : DialogFragment() {

    private lateinit var viewModel: AddClassViewModel
    private lateinit var _binding:FragmentAddClassBinding
    private val binding get() = _binding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding= FragmentAddClassBinding.inflate(inflater,container,false)
        dialog?.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        isCancelable=false
         return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddClassViewModel::class.java)
        observeLiveData()
        binding.addClassClose.setOnClickListener {
            dismiss()
        }
        binding.addTaskSend2.setOnClickListener {
            requireContext().toast("Oluşturuluyorr...")
            viewModel.createClass(binding.classDepartment.editText?.text.toString(),binding.className.editText?.text.toString(),requireContext())
        }

    }
    private fun observeLiveData(){
        viewModel.closeAlert.observe(viewLifecycleOwner){
            if (it) dismiss()
        }
    }

}