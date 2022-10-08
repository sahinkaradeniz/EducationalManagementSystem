package com.skapps.eys.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.skapps.eys.Model.Teacher

@Dao
interface TeacherDao{
    @Insert
    suspend fun insert(teacher: Teacher)
    @Query("Select * from teacher")
    suspend fun getAllTeacher():List<Teacher>
    @Delete
    suspend fun deleteTeacher(teacher: Teacher)
    @Query("Select * From teacher Where teacherid=:userID")
    suspend fun getTeacher(userID:String):Teacher
}