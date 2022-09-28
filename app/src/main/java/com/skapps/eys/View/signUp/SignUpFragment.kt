package com.skapps.eys.View.signUp

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.skapps.eys.R
import com.skapps.eys.Util.warningToast
import com.skapps.eys.databinding.FragmentSingUpBinding

class SignUpFragment : Fragment() {

    private lateinit var viewModel: SignUpViewModel
    private lateinit var _binding:FragmentSingUpBinding
    private val binding get() = _binding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSingUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)
        observeLiveData()
        binding.sinupButton.setOnClickListener {
            val password = binding.signpasswordText.editText?.text.toString()
            val email = binding.signemailtext.editText?.text.toString()
            val name =binding.signupName.editText?.text.toString()
            if (name.isEmpty()){
                requireContext().warningToast("Lütfen isminizi yazınız")
            }else if (password.length<6){
                requireContext().warningToast("Şifre uzunluğu 6 karakterden kısa olamaz")
            }else if(viewModel.isValidEmail(email)){
                requireContext().warningToast("Email biçimi hatalı")
            }else{
                //  viewModel.emailControl(emailList,email,requireContext())
                //  requireContext().warningToast("Bu email başka bir hesap tarafından kullanılmakta")
                viewModel.userRegister(email,password,name,requireContext())
            }
        }
    }
    private fun observeLiveData(){
        viewModel.isSuccesful.observe(viewLifecycleOwner){
            if (it){
                requireContext().warningToast("işlem gercelşersti")
            }
        }
    }

}