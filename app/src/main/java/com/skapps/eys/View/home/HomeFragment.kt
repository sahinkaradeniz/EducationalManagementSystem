package com.skapps.eys.View.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.skapps.eys.Adapter.HomeTaskAdapter
import com.skapps.eys.R
import com.skapps.eys.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding:FragmentHomeBinding?=null
    private val binding get() = _binding
    private lateinit var viewModel: HomeViewModel
    private var homeTaskAdapter=HomeTaskAdapter(arrayListOf())
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding=FragmentHomeBinding.inflate(inflater,container,false)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        viewModel.getTaskList()
        observeLiveData()

        binding?.taskRecyclerView?.apply{
           setHasFixedSize(true)
           adapter=homeTaskAdapter
           layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        }

        return binding?.root
    }


    private fun observeLiveData(){
        viewModel.taskList.observe(viewLifecycleOwner){
            homeTaskAdapter.updateTaskList(it)
        }
    }

}