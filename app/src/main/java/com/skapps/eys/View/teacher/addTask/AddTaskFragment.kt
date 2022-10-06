package com.skapps.eys.View.teacher.addTask

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.ImageDecoder
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.skapps.eys.Database.FirebaseDatabase
import com.skapps.eys.Model.Classes
import com.skapps.eys.R
import com.skapps.eys.Util.*
import com.skapps.eys.databinding.FragmentAddTaskBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class AddTaskFragment : DialogFragment() {
    private lateinit var _binding:FragmentAddTaskBinding
    private val binding get() = _binding
    private lateinit var viewModel: AddTaskViewModel
    private lateinit var auth: FirebaseAuth
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private var selectedBitmap: Uri?=null
    private var selectClasses=Classes("null","null","null","null","null","null")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        registerLauncher()
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
                        if (selectedBitmap!=null){
                            requireContext().toast("Gönderiliyor...")
                            GlobalScope.launch {
                                viewModel.sendImageTask( binding.addTaskText.editText?.text.toString(),
                                    selectedBitmap!!,
                                    selectClasses.classID,requireContext())
                            }
                        }else{
                            requireContext().succesToast("resin tık")
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
        binding.addClearImage.setOnClickListener {
            binding.imagewindow.visibility=View.GONE
            selectedBitmap=null
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
        binding.addTaskImage.setOnClickListener{
            selectImage(it)
        }
    }
    private fun registerLauncher(){
        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){ result ->
            if(result){
                val MediaSelect = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityResultLauncher.launch(MediaSelect)
            }else{
                requireContext().toast("İzin Alınamadı")
            }
        }
        activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            if (result.resultCode == AppCompatActivity.RESULT_OK){
                val intentFromResult = result.data
                if (intentFromResult != null){
                    val imageData = intentFromResult.data
                    if(imageData != null) {
                           try {
                                selectedBitmap=imageData
                                binding.imagewindow.visibility=View.VISIBLE
                                binding.selectImage.setImageURI(imageData)
                            } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
            }
        }
    }

    fun selectImage(view : View){
        if (ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            //rationale
            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)){
                permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)

            }else{
                // request permission
                permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }else{
            val intentToGallery=Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            activityResultLauncher.launch(intentToGallery)
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