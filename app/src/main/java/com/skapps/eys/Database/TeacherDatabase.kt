package com.skapps.eys.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.skapps.eys.Dao.TeacherDao
import com.skapps.eys.Model.Teacher

@Database(entities = arrayOf(Teacher::class),version = 1, exportSchema = false)
abstract class TeacherDatabase:RoomDatabase(){
    abstract fun teacherDao():TeacherDao

    companion object{

        @Volatile private var instance :TeacherDatabase? = null

        private val lock = Any()

        operator fun invoke(context : Context) = instance ?: synchronized(lock) {
            instance ?: makeDatabase(context).also {
                instance = it
            }
        }


        private fun makeDatabase(context : Context) = Room.databaseBuilder(
            context.applicationContext,TeacherDatabase::class.java,"teachers"
        ).build()
    }
}