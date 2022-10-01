package com.skapps.eys.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Teacher(
    @ColumnInfo(name = "uid")
    var uid:String,
    @ColumnInfo(name = "name")
    var name:String,
    @ColumnInfo(name = "department")
    var department:String,
    @ColumnInfo(name = "photo")
    var photo:String) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}