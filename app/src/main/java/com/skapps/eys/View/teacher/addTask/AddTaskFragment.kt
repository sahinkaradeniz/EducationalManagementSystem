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
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.common.net.MediaType.PDF
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.ktx.storage
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
    private var selectedPdf: Uri?=null
    private var selectPDF=false
    private var selectImage=false
    private var pdfName=""
    private var selectClasses=Classes("null","null","null","null","null","null")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        registerLauncher()
        _binding= FragmentAddTaskBinding.inflate(inflater,container,false)
        isCancelable=false
        binding.addtaskClose.setOnClickListener {
            dialog?.dismiss()
        }
        binding.addClearImage.setOnClickListener {
            binding.imagewindow.hide()
            selectedBitmap=null
        }
        binding.addClearPdf.setOnClickListener {
            binding.constraintLayoutPdf.hide()
            selectedPdf=null
        }
        binding.addDocTask.setOnClickListener {
            selectPdf()
        }
        binding.addCameraTask.setOnClickListener {
            binding.constraintLayoutTask.maxHeight=0
            binding.constraintLayoutTask.maxWidth=0
            binding.spinProggres.show()
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
        binding.addTaskSend.setOnClickListener {
                        requireContext().toast("Gönderme başlatıldı")
                        val text=binding.addTaskText.editText?.text.toString()
                        viewModel.sendImageOrDocTask(
                            text,
                            selectClasses.className,
                            selectClasses.classID,
                            selectedBitmap,
                            selectedPdf,
                            requireContext(),
                            binding)
                             }

    }
    private fun registerLauncher(){
        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){ result ->
            if(result){
                val MediaSelect = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityResultLauncher.launch(MediaSelect)
                requireContext().toast("İzin Alını")
            }else{
                requireContext().toast("İzin Alınamadı")
            }
        }
        activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            if (result.resultCode == AppCompatActivity.RESULT_OK){
                val intentFromResult = result.data
                if (intentFromResult != null){
                    val Data = intentFromResult.data
                    if(Data != null) {
                           try {
                                if (selectImage){
                                    selectedBitmap=Data
                                    binding.imagewindow.show()
                                    binding.selectImage.setImageURI(Data)
                                    initialStateResult()
                                }else if (selectPDF){
                                    selectedPdf=Data
                                    pdfName=requireContext().getPdfName(Data)
                                    binding.pdfNameText.text=pdfName
                                    binding.constraintLayoutPdf.show()
                                    requireContext().toast("dosya alındı")
                                    initialStateResult()
                                }
                            } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
            }
        }
    }

    fun selectPdf(){
        selectPDF=true
        activityResultLauncher.launch(getFileChooserIntentForImageAndPdf())
    }
    fun selectImage(view : View){
        if (ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            //rationale
            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)){
                selectImage=true
                permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)

            }else{
                // request permission
                selectImage=true
                permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }else{
            val intentToGallery=Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            selectImage=true
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
    private fun initialStateResult(){
        selectImage=false
        selectPDF=false
    }


}