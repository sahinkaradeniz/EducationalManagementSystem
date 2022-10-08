package com.skapps.eys.View.teacher.addTask

import android.app.Application
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.skapps.eys.Base.BaseViewModel
import com.skapps.eys.Model.Classes
import com.skapps.eys.Model.TestTeacher
import com.skapps.eys.Util.*
import com.skapps.eys.databinding.FragmentAddTaskBinding
import kotlinx.coroutines.launch
import java.util.*


class AddTaskViewModel(application: Application) : BaseViewModel(application) {
    var closeAlert=MutableLiveData(false)
    private val dbFirestore = Firebase.firestore
    val classList=MutableLiveData<ArrayList<Classes>>()
    val classNameList=MutableLiveData<ArrayList<String>>()
    val storage=Firebase.storage

     fun sendImageOrDocTask(taskText: String, className: String, classID: String, taskImage: Uri?, taskDocument: Uri?,context: Context,binding: FragmentAddTaskBinding) {
        try { launch {
            dbFirestore.collection("marun").document("teachers").collection("teacher")
                .addSnapshotListener { document, error ->
                    if (document != null) {
                        for (value in document){
                            val teacherData = TestTeacher(
                                value.get("teacherid").toString(),
                                value.get("name").toString(),
                                value.get("department").toString(),
                                value.get("photo").toString()
                            )
                            if (taskImage!=null && taskDocument==null){
                                progressViewShow(binding)
                                sendTaskImage(teacherData,taskText,taskImage,classID,className,context,binding)
                            }else if (taskImage==null && taskDocument!=null){
                                progressViewShow(binding)
                                sendTaskDocument(teacherData,taskText,taskDocument,classID,className,context,binding)
                            }else if (taskImage!=null && taskDocument!=null){
                                progressViewShow(binding)
                                sendImageAndDocTask(taskText,context,teacherData,classID,className,taskImage,taskDocument)
                            }else{
                                progressViewShow(binding)
                                addTask(taskText,context,teacherData,classID,className)
                            }
                        }
                    }
                }
        }
        }catch (e:Exception){
            Log.w(ContentValues.TAG, "getTeacher Exception", e)
            context.warningAlert("Bir sorun oluştu","Kapat")
        }

    }
    private fun getTeacher(){

    }
    private fun sendTaskDocument(teacher: TestTeacher, taskText: String, taskDocument:Uri, classID: String, className: String, context: Context, binding: FragmentAddTaskBinding) {
        val storage=Firebase.storage
        val reference=storage.reference
        val mReference = taskDocument.lastPathSegment?.let { reference.child("marun").child(classID) }
        launch {
            try {
                mReference!!.putFile(taskDocument).addOnSuccessListener { task ->
                    mReference.downloadUrl.addOnSuccessListener { uri ->
                        addTask(taskText,context,teacher,classID,className,"null",uri.toString())
                        context.succesAlert("Ödev Gönderildi","Tamam")
                        progressViewHide()
                    }
                }
            }catch (e: Exception) {
                context.succesAlert("Bir Sroun Oluştu","Tamam")
                Log.e("sendTaskDocument",e.message.toString())
                progressViewHide()
            }
        }
    }

   private fun addTask(
       taskText:String,
       context: Context,
       teacher: TestTeacher,
       classID:String,
       className: String,
       taskImage:String="null",
       taskDocument:String="null"){
        launch {
            try {
                val newUUID = UUID.randomUUID().toString()
                val task= hashMapOf( "taskID" to newUUID,
                    "classID" to classID,
                    "className" to className,
                    "teacherID" to teacher.uid,
                    "teacherName" to teacher.name,
                    "teacherPhoto" to teacher.photo,
                    "teacherDepartment" to teacher.department,
                    "taskText" to taskText,
                    "taskImage" to taskImage,
                    "document" to taskDocument,
                    "date" to System.currentTimeMillis())
                dbFirestore.collection("marun").document("tasks").collection("task").add(task).addOnSuccessListener { documentReference ->
                    Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ${documentReference}")
                    context.succesAlert("Ödev Paylaşıldı","Tamam")
                    closeAlert.value=true
                }.addOnFailureListener { e ->
                    Log.e(ContentValues.TAG, "Error adding document", e)
                    context.warningAlert("İnternet ayarlarınızı kontrol ediniz.","Tamam")
                }
            }catch (e :Exception){
                Log.e(ContentValues.TAG, "addTask Exception", e)
                context.warningAlert("Bir sorun oluştu","Kapat")
            }
        }
    }


    fun getAllClasses(){
        launch {
            val classes=ArrayList<Classes>(arrayListOf())
            val className=ArrayList<String>(arrayListOf())
            try {
                dbFirestore.collection("marun").document("classes").collection("class").addSnapshotListener { document, error ->
                    if (error!=null){
                        Log.e("getClassList", "Listen failed.",error)
                        return@addSnapshotListener
                    }
                    if (document!=null){
                        classes.clear()
                        for (value in document){
                            //   val time=  value.get("date") as Timestamp
                            //   val date = SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(Date(time.nanoseconds.toLong()))
                            val clas= Classes(
                                value.get("teacherid").toString(),
                                value.get("classid").toString(),
                                value.get("teachername").toString(),
                                value.get("teacherphoto").toString(),
                                value.get("name").toString(),
                                value.get("department").toString()
                            )
                            classes.add(clas)
                            className.add(value.get("name").toString())
                        }
                        classList.value=classes
                        classNameList.value=className
                    }
                }
                  }catch (e:Exception){
                      Log.e("getAllClasses",e.toString())
            }

        }
    }

    fun classItemID(id:Int):Classes{
        return classList.value!!.get(id)
    }

    private fun sendTaskImage(
        teacher: TestTeacher,
        taskText:String,
        bitmap:Uri,
        classID:String,
        className: String,
        context: Context,
        binding:FragmentAddTaskBinding){
        val reference=storage.reference
        val imageReference=reference.child("marun").child(classID)
        try {
            launch {
                    imageReference.putFile(bitmap).addOnSuccessListener { task ->
                        imageReference.downloadUrl.addOnSuccessListener { imageuri ->
                            addTask(taskText,context,teacher,classID,className,imageuri.toString())
                            context.succesAlert("Ödev Gönderildi","Tamam")
                            progressViewHide()
                        }
                    }.addOnFailureListener{
                        it.localizedMessage?.let { Log.e("sendTaskImage", it) }
                        context.warningAlert("Bir sorun oluştu.","Kapat")
                        progressViewHide()
                    }
             }
        }catch (e:Exception){
            Log.e("AddTaskViewModel add Image ",e.toString())
            context.warningAlert("Bir sorun oluştu.","Kapat")
            progressViewHide()
        }
    }
    private fun sendImageAndDocTask(
        taskText:String,
        context: Context,
        teacher: TestTeacher,
        classID:String,
        className: String,
        taskImage:Uri,
        taskDocument:Uri) {
        val ireference=storage.reference
        val imageReference=ireference.child("marun").child(getRandUid(12))
        val dreference=storage.reference
        val docReference=dreference.child("marun").child(getRandUid(12))
        try {
            launch {
                imageReference.putFile(taskImage).addOnSuccessListener { task ->
                    imageReference.downloadUrl.addOnSuccessListener { uri ->
                        docReference.putFile(taskDocument).addOnSuccessListener {
                            docReference.downloadUrl.addOnSuccessListener { docUri ->
                                    addTask(taskText,context,teacher, classID, className,uri.toString(),docUri.toString())
                            }
                        }.addOnFailureListener {
                            it.localizedMessage?.let { Log.e("img and doc", it) }
                            context.warningAlert("Bir sorun oluştu.","Kapat")
                            progressViewHide()
                        }
                    }
                }.addOnFailureListener{
                    it.localizedMessage?.let { Log.e("img and doc ", it) }
                    context.warningAlert("Bir sorun oluştu.","Kapat")
                    progressViewHide()
                }
            }
        }catch (e:Exception){
            Log.e("AddTaskViewModel add Image ",e.toString())
        }

    }


    private fun progressViewShow(binding: FragmentAddTaskBinding){
        binding.spinProggres.show()
        binding.constraintLayoutTask.maxWidth=0
        binding.constraintLayoutTask.maxHeight=0
    }
    private fun progressViewHide(){
        closeAlert.value=true
    }

}

