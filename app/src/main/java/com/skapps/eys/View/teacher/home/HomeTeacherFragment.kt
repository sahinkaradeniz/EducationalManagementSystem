package com.skapps.eys.View.teacher.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.skapps.eys.Adapter.HistoryTaskAdapter
import com.skapps.eys.Adapter.HomeTaskAdapter
import com.skapps.eys.R
import com.skapps.eys.databinding.FragmentHomeTeacherBinding
import com.stone.vega.library.VegaLayoutManager

class HomeTeacherFragment : Fragment() {

    private lateinit var viewModel: HomeTeacherViewModel
    private lateinit var _binding:FragmentHomeTeacherBinding
    private val binding get() = _binding
    private var homeTaskAdapter=HomeTaskAdapter(arrayListOf())
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding=FragmentHomeTeacherBinding.inflate(inflater,container,false)
        viewModel = ViewModelProvider(this).get(HomeTeacherViewModel::class.java)
        viewModel.getTaskList()
        observeLiveData()
        binding.addTask.setOnClickListener {
            findNavController().navigate(R.id.addTaskFragment)
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        binding.teacherHistoryRc.apply {
           layoutManager=VegaLayoutManager()
            setHasFixedSize(true)
            adapter=homeTaskAdapter
        }
        super.onActivityCreated(savedInstanceState)
    }
    private fun observeLiveData(){
        viewModel.taskList.observe(viewLifecycleOwner){
            homeTaskAdapter.updateTaskList(it)
        }
    }

}