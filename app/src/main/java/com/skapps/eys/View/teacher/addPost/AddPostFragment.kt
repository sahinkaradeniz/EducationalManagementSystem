package com.skapps.eys.View.teacher.addPost

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
import com.skapps.eys.Util.toast
import com.skapps.eys.databinding.FragmentAddPostBinding

class AddPostFragment : DialogFragment() {
    private lateinit var _binding:FragmentAddPostBinding
    private val binding get() = _binding
    private lateinit var viewModel: AddPostViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View{
        dialog?.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        isCancelable=false
        _binding= FragmentAddPostBinding.inflate(inflater,container,false)
         return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddPostViewModel::class.java)
        observeLiveData()
       binding.apply {
           sendPost.setOnClickListener {
               requireContext().toast("Gönderiliyor..")
               viewModel.addPost(postText.editText?.text.toString(),postTitle.editText?.text.toString(),"no image",requireContext())
           }
       }
    }
    private fun observeLiveData(){
        viewModel.closeAlert.observe(viewLifecycleOwner){
            if (it) dismiss()
        }
    }

}