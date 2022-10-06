package com.skapps.eys.Model

import android.media.Image
import com.google.firebase.firestore.FieldValue

data class Task(
    var taskID: String,
    var classID: String,
    var teacherID: String,
    var teacherName: String,
    var teacherPhoto: String,
    var teacherDepartment: String,
    var taskText: String,
    var taskImage: String,
    var document: String,
    var date:String
) {
}
