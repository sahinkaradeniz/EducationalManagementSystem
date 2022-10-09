package com.skapps.eys.View.student.classes

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.skapps.eys.Adapter.ClassesStudentAdapter
import com.skapps.eys.databinding.FragmentClassesBinding

class ClassesFragment : Fragment() {
    private lateinit var _binding:FragmentClassesBinding
    private val binding get() = _binding
    private lateinit var viewModel: ClassesViewModel
    private val adapterClasses=ClassesStudentAdapter(arrayListOf())
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding=FragmentClassesBinding.inflate(inflater,container,false)
        binding.rcvClassStudet.apply {
            adapter=adapterClasses
            setHasFixedSize(true)
            layoutManager=GridLayoutManager(requireContext(),2)
        }
       return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ClassesViewModel::class.java)
        observeLivedata()
        viewModel.getAllClasses()
    }
    private fun observeLivedata(){
        viewModel.classList.observe(viewLifecycleOwner){
            adapterClasses.updateClassList(it)
        }
    }
}