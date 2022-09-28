package com.skapps.eys.View.login

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.skapps.eys.R
import com.skapps.eys.Util.warningToast
import com.skapps.eys.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var viewModel: LoginViewModel
    private var _binding: FragmentLoginBinding?=null
    private val binding get()=_binding
    private var student=true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        auth = Firebase.auth
        viewModel= ViewModelProvider(this).get(LoginViewModel::class.java)
        _binding= FragmentLoginBinding.inflate(inflater,container,false)

        binding!!.loginButton.setOnClickListener {
            val password=binding!!.passwordText.editText!!.text.toString()
            val email=binding!!.emailtext.editText!!.text.toString()
            if (student){
                viewModel.loginUser(password,email,requireContext())
            }else{
                requireContext().warningToast("hoca girişi")
            }
           // Toast.makeText(requireContext(), email, Toast.LENGTH_SHORT).show()
          // viewModel.loginUser(password,email,requireContext())
        }
        binding!!.signupButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }
        binding!!.buttonToggleGroup.addOnButtonCheckedListener { group, _, isChecked ->
            if (group.checkedButtonId==R.id.btn_student){
                student=true
            }else if (group.checkedButtonId==R.id.btn_teacher){
                student=false
            }
        }
        checkUser()
        observeLiveData()
        return  binding?.root
    }

    private fun checkUser(){
        val currentUser = auth.currentUser
        if(currentUser != null){
            requireContext().warningToast("user var")

        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
    }
    private fun observeLiveData(){
        viewModel.loginsuccesful.observe(viewLifecycleOwner){
            if (it){
                checkUser()
                requireContext().warningToast("yess")
                //  showDialog()
            }
        }
    }

}