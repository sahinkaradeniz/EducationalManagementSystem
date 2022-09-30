package com.skapps.eys.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.skapps.eys.Model.Task
import com.skapps.eys.databinding.RowTeacherHistoryBinding

class HistoryTaskAdapter(var historyList:ArrayList<Task>):RecyclerView.Adapter<HistoryTaskAdapter.HistoryViewHolder>(){
    class HistoryViewHolder(val rowTeacherHistoryBinding:RowTeacherHistoryBinding):RecyclerView.ViewHolder(rowTeacherHistoryBinding.root) {
        fun bind(task:Task){

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val recyclerRowBinding=RowTeacherHistoryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return HistoryViewHolder(recyclerRowBinding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
      holder.bind(historyList.get(position))
    }

    override fun getItemCount(): Int {
      return historyList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateHistory(newTaskList:List<Task>){
        historyList.clear()
        historyList.addAll(newTaskList)
        notifyDataSetChanged()
    }
}