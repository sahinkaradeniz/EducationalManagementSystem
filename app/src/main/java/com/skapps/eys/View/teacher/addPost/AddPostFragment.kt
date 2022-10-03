package com.skapps.eys.View.teacher.addPost

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.skapps.eys.R

class AddPostFragment : Fragment() {

    companion object {
        fun newInstance() = AddPostFragment()
    }

    private lateinit var viewModel: AddPostViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_post, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddPostViewModel::class.java)
        // TODO: Use the ViewModel
    }

}