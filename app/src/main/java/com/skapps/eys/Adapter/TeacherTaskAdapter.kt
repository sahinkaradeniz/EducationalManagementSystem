package com.skapps.eys.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.skapps.eys.Model.Task
import com.skapps.eys.Util.downloadFromUrl
import com.skapps.eys.Util.hide
import com.skapps.eys.Util.placeholderProgressBar
import com.skapps.eys.Util.show
import com.skapps.eys.databinding.RowTaskTeacherBinding

class TeacherTaskAdapter(var taskList:ArrayList<Task>):RecyclerView.Adapter<TeacherTaskAdapter.ViewHolder>() {
    class ViewHolder(val taskRowBinding:RowTaskTeacherBinding):RecyclerView.ViewHolder(taskRowBinding.root){
        private fun defaultBind(task: Task){
            taskRowBinding.apply {
                taskText.text=task.taskText
                teacherName.text=task.teacherName
                classNamee.text=task.className
                teacherImage.downloadFromUrl(task.teacherPhoto, placeholderProgressBar(taskRowBinding.root.context))
            }
        }
       fun bind(task: Task){
        //    holder.view.imageView.downloadFromUrl(countryList[position].imageUrl, placeholderProgressBar(holder.view.context))
            if (task.taskImage=="null" && task.document=="null"){
                taskRowBinding.apply {
                    imageTask.hide()
                    constraintDocument.hide()
                    defaultBind(task)
                }
            }else if (task.taskImage!="null" && task.document=="null"){
                taskRowBinding.apply {
                    imageTask.show()
                    imageTask.downloadFromUrl(task.taskImage, placeholderProgressBar(taskRowBinding.root.context))
                    constraintDocument.hide()
                    defaultBind(task)
                }
            }else if (task.taskImage=="null" && task.document!="null"){
                taskRowBinding.apply {
                    imageTask.hide()
                    constraintDocument.show()
                   defaultBind(task)
                }
            }else if (task.taskImage!="null" && task.document!="null"){
                taskRowBinding.apply {
                    imageTask.show()
                    imageTask.downloadFromUrl(task.taskImage, placeholderProgressBar(taskRowBinding.root.context))
                    constraintDocument.show()
                   defaultBind(task)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val rcvBinding=RowTaskTeacherBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(rcvBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(taskList.get(position))
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