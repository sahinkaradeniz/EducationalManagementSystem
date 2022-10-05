package com.skapps.eys.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.skapps.eys.Model.Forum
import com.skapps.eys.databinding.RowForumStudentBinding

class ForumAdapter(val forumList: ArrayList<Forum>):RecyclerView.Adapter<ForumAdapter.ForumViewHolder>() {
    class ForumViewHolder(val rowForumStudentBinding: RowForumStudentBinding):RecyclerView.ViewHolder(rowForumStudentBinding.root){
        @SuppressLint("SetTextI18n")
        fun bind(forum: Forum){
            rowForumStudentBinding.forumName.text=forum.username
            rowForumStudentBinding.forumText.text=forum.text
            rowForumStudentBinding.forumDepartment.text=forum.department
            rowForumStudentBinding.hastagText.text="#${forum.title}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForumViewHolder {
       val recyclerRowBinding=RowForumStudentBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ForumViewHolder(recyclerRowBinding)
    }

    override fun onBindViewHolder(holder: ForumViewHolder, position: Int) {
        holder.bind(forumList.get(position))
    }

    override fun getItemCount(): Int {
        return forumList.size
    }

     @SuppressLint("NotifyDataSetChanged")
     fun  updateForumList(newForumList:List<Forum>){
            forumList.clear()
            forumList.addAll(newForumList)
         notifyDataSetChanged()
     }
}