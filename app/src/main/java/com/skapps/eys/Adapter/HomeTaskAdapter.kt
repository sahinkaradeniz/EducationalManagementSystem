package com.skapps.eys.Adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.skapps.eys.Model.Task
import com.skapps.eys.databinding.HomeRcRowBinding

class HomeTaskAdapter(var taskList:ArrayList<Task>):RecyclerView.Adapter<HomeTaskAdapter.Viewholder>() {
    class Viewholder(val homeRcRowBinding: HomeRcRowBinding):RecyclerView.ViewHolder(homeRcRowBinding.root) {
            fun Bind(task: Task){
                homeRcRowBinding.taskText.text=task.taskText
                homeRcRowBinding.teacherName.text=task.teacheName
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val recyclerRowBinding=HomeRcRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Viewholder(recyclerRowBinding)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        Log.e("onbind",taskList.get(position).teacheName)
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