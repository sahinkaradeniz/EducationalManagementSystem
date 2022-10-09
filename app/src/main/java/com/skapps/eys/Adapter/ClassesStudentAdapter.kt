package com.skapps.eys.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.skapps.eys.Model.Classes
import com.skapps.eys.databinding.RowClassStudentBinding
import com.skapps.eys.databinding.RowTaskStudentBinding

class ClassesStudentAdapter(val classList:ArrayList<Classes>):RecyclerView.Adapter<ClassesStudentAdapter.ViewHolder>(){
    class ViewHolder(val rowClassStudentBinding:RowClassStudentBinding):RecyclerView.ViewHolder(rowClassStudentBinding.root){
        fun bind(classes:Classes){
            rowClassStudentBinding.apply {
                tClassName.text=classes.className
                tclassDepartment.text=classes.department
                tClassDate.text=classes.teacherName
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val rowClassStudentBinding=RowClassStudentBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return  ViewHolder(rowClassStudentBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(classList.get(position))
    }

    override fun getItemCount(): Int {
       return classList.size
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateClassList(newClassList:List<Classes>){
        classList.clear()
        classList.addAll(newClassList)
        notifyDataSetChanged()
    }
}