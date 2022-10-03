package com.skapps.eys.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.skapps.eys.Model.Classes
import com.skapps.eys.databinding.RowClassTeacherBinding

class ClassesTeacherAdapter(val classesList:ArrayList<Classes>):RecyclerView.Adapter<ClassesTeacherAdapter.ClassesViewHolder>(){
    class ClassesViewHolder(val rowClassTeacherBinding: RowClassTeacherBinding):RecyclerView.ViewHolder(rowClassTeacherBinding.root){
        fun bind(classes:Classes){
            rowClassTeacherBinding.tClassName.text=classes.className
            rowClassTeacherBinding.tClassDate.text=classes.date
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassesViewHolder {
        val recyclerRowBinding=RowClassTeacherBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ClassesViewHolder(recyclerRowBinding)
    }

    override fun onBindViewHolder(holder: ClassesViewHolder, position: Int) {
       holder.bind(classesList.get(position))
    }


    override fun getItemCount(): Int {
      return  classesList.size
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateClassList(newClassList:List<Classes>){
        classesList.clear()
        classesList.addAll(newClassList)
        notifyDataSetChanged()
    }
}