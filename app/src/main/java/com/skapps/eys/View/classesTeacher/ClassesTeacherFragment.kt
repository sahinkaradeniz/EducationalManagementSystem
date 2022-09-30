package com.skapps.eys.View.classesTeacher

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.skapps.eys.Adapter.ClassesTeacherAdapter
import com.skapps.eys.R
import com.skapps.eys.databinding.FragmentClassesTeacherBinding

class ClassesTeacherFragment : Fragment() {
    private lateinit var _binding:FragmentClassesTeacherBinding
    private val binding get() = _binding
    private lateinit var viewModel: ClassesTeacherViewModel
    private  var classesTeacherAdapter= ClassesTeacherAdapter(arrayListOf())
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding=FragmentClassesTeacherBinding.inflate(inflater,container,false)
        binding.classesTeacherRcv.apply {
            adapter=classesTeacherAdapter
            setHasFixedSize(true)
            layoutManager=GridLayoutManager(this@ClassesTeacherFragment.requireContext(),2)
        }

        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ClassesTeacherViewModel::class.java)
        observeLiveData()
         viewModel.getAllList()
    }

    private fun observeLiveData(){
        viewModel.classList.observe(viewLifecycleOwner){
            classesTeacherAdapter.updateClassList(it)
        }
    }
}