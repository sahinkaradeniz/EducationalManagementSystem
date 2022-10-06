package com.skapps.eys.Adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.skapps.eys.Model.Task
import com.skapps.eys.databinding.RowTaskHomeBinding

class HomeTaskAdapter(var taskList:ArrayList<Task>):RecyclerView.Adapter<HomeTaskAdapter.Viewholder>() {
    class Viewholder(val rowTaskHomeBinding: RowTaskHomeBinding):RecyclerView.ViewHolder(rowTaskHomeBinding.root) {
            fun Bind(task: Task){
                rowTaskHomeBinding.teacherDepartment.text=task.teacherDepartment
                rowTaskHomeBinding.taskText.text=task.taskText
                rowTaskHomeBinding.teacherName.text=task.teacherName
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val recyclerRowBinding=RowTaskHomeBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Viewholder(recyclerRowBinding)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        holder.Bind(taskList.get(position))
    }

    override fun getItemCount(): Int {
           return taskList.size
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateTaskList(newTaskList: List<Task>){
        taskList.clear()
        taskList.addAll(newTaskList)
        notifyDataSetChanged()
    }
}