package com.skapps.eys.View.student.forum

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.skapps.eys.Adapter.ForumAdapter
import com.skapps.eys.Database.TeacherDatabase
import com.skapps.eys.R
import com.skapps.eys.databinding.FragmentForumBinding

class ForumFragment : Fragment() {
    private var forumAdapter=ForumAdapter(arrayListOf())
    private lateinit var viewModel: ForumViewModel
    private lateinit var _binding:FragmentForumBinding
    private val binding get() = _binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding=FragmentForumBinding.inflate(inflater,container,false)
        binding.forumRcv.apply {
            setHasFixedSize(true)
            layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
            adapter=forumAdapter
        }
       return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ForumViewModel::class.java)
        viewModel.getAllList()
        observeLiveData()
        binding.addComment.setOnClickListener {
           // viewModel.addTeacher(requireContext())
            findNavController().navigate(R.id.addPostFragment)
        }
    }
    private fun observeLiveData(){
        viewModel.forumlist.observe(viewLifecycleOwner){
            forumAdapter.updateForumList(it)
        }
    }

}