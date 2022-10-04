package com.skapps.eys.Model

import com.google.firebase.firestore.FieldValue

data class Task(var userID:String,var uid:String,var teacheName:String,var teacherPhoto: String,var teacherDepartment:String,var taskText:String,var document:String,var date:Any) {
}
