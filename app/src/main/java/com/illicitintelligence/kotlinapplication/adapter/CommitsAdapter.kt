package com.illicitintelligence.kotlinapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.illicitintelligence.kotlinapplication.R
import com.illicitintelligence.kotlinapplication.model.commit.CommitResult
import com.illicitintelligence.kotlinapplication.model.repository.RepoResult
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class CommitsAdapter(
    var commits: List<CommitResult>
) :
    RecyclerView.Adapter<CommitsAdapter.CommitViewHolder>() {
    private lateinit var slideAnimation: Animation
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommitViewHolder {

        slideAnimation = AnimationUtils.loadAnimation(
            parent.context.applicationContext,
            R.anim.slide_in_animation
        )

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.commits_item_layout, parent, false);

        return CommitViewHolder(view)
    }

    override fun getItemCount(): Int = commits.size


    override fun onBindViewHolder(holder: CommitViewHolder, position: Int) {

        val simpleDataFormat = SimpleDateFormat("MM/dd/yyyy")

        holder.apply {
            itemView.animation = slideAnimation
            committerNameTextView.text = commits[position].commit.committer.name
            commitMessageTextView.text = commits[position].commit.message
            try {
                dateTextView.text =
                    simpleDataFormat.format(commits[position].commit.committer.date)?.toString()
                        ?: "date unknown"
            } catch (e: Exception){

            }


        }
    }

    inner class CommitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val committerNameTextView = itemView.findViewById(R.id.committer_name_textview) as TextView
        val commitMessageTextView = itemView.findViewById<TextView>(R.id.commit_message_textview)
        val dateTextView: TextView = itemView.findViewById(R.id.date_textview)
    }
}