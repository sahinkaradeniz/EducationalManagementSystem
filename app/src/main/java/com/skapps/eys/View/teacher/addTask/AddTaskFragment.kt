package com.skapps.eys.View.teacher.addTask

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.skapps.eys.Database.FirebaseDatabase
import com.skapps.eys.Model.Classes
import com.skapps.eys.R
import com.skapps.eys.Util.addItemList
import com.skapps.eys.Util.succesAlert
import com.skapps.eys.Util.toast
import com.skapps.eys.Util.warningAlert
import com.skapps.eys.databinding.FragmentAddTaskBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class AddTaskFragment : DialogFragment() {
    private lateinit var _binding:FragmentAddTaskBinding
    private val binding get() = _binding
    private lateinit var viewModel: AddTaskViewModel
    private lateinit var auth: FirebaseAuth
    private var selectClasses=Classes("null","null","null","null","null","null")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding= FragmentAddTaskBinding.inflate(inflater,container,false)
        isCancelable=false
        binding.addTaskSend.setOnClickListener {
            try {
                val text=binding.addTaskText.editText?.text.toString()
                if (text.isEmpty()){
                    requireContext().toast("Lütfen metin giriniz.")
                }else{
                    if (selectClasses.className=="null"){
                        requireContext().toast("Lütfen bir sınıf seçiniz.")
                    }else{
                        requireContext().toast("Gönderiliyor...")
                        GlobalScope.launch {
                            viewModel.sendTask( binding.addTaskText.editText?.text.toString(),
                                "no doc",
                                requireContext(),selectClasses.classID)
                        }
                    }
                }
             }catch (e : Exception){
                 Log.e("Add Task Fragment AddTask()",e.toString())
                 requireContext().warningAlert("Bir sorun oluştu","Kapat")
            }

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
        observeLiveData()
        viewModel.getAllClasses()

        binding.autoCompleteTextView.setOnItemClickListener { parent, view, position, id ->
               selectClasses= viewModel.classItemID(position)
        }

    }


    private fun observeLiveData(){
        viewModel.closeAlert.observe(viewLifecycleOwner){
            if (it) dismiss()
        }
        viewModel.classNameList.observe(viewLifecycleOwner){
            binding.autoCompleteTextView.addItemList(it,requireContext())
        }
    }


}