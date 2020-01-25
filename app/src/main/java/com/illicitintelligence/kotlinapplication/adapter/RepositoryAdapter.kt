package com.illicitintelligence.kotlinapplication.adapter

import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.illicitintelligence.kotlinapplication.R
import com.illicitintelligence.kotlinapplication.model.repository.RepoResult

class RepositoryAdapter(var repositories: List<RepoResult>/*, var repositoryDelegate: RepositoryDelegate*/) :
    RecyclerView.Adapter<RepositoryAdapter.RepositoryViewHolder>() {
    companion object {
        val REPO_FILTER = "from.repo.adapter"
        val VALUE_KEY = "from.repo"
    }


    private lateinit var slideAnimation: Animation
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {

        slideAnimation = AnimationUtils.loadAnimation(
            parent.context.applicationContext,
            R.anim.slide_in_animation
        )

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.repository_item_layout, parent, false);

        return RepositoryViewHolder(view)
    }

    override fun getItemCount(): Int = repositories.size


    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {

        holder.apply {
            itemView.animation = slideAnimation
            fullNameTextView.text = repositories[position].fullName.addPrefix()
            descriptionTextView.text = repositories[position].description?.addDescriptionPrefix()?:"No Descrioption".addDescriptionPrefix()
            urlTextView.text = repositories[position].url
            itemView.setOnClickListener {
                //                repositoryDelegate.getCommits(repositories[position].owner.login, repositories[position].name)
                val intent = Intent(REPO_FILTER)
                intent.putExtra(
                    VALUE_KEY,
                    arrayOf(repositories[position].owner.login, repositories[position].name)
                )
                itemView.context.sendBroadcast(intent)
            }
            Log.d("TAG_X", "Cube of $position is ${position.cubeNumber()}")

        }

    }


    fun String.addPrefix(): String {
        return "RE: $this"
    }

    fun String.addDescriptionPrefix(): String {
        return "Description: $this"
    }

    fun Int.cubeNumber(): Int {
        return (this * this * this)
    }

//    interface RepositoryDelegate {
//        fun getCommits(userName: String, repositoryName: String)
//    }

    inner class RepositoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val fullNameTextView = itemView.findViewById(R.id.full_name_textview) as TextView
        val descriptionTextView = itemView.findViewById<TextView>(R.id.description_textview)
        val urlTextView: TextView = itemView.findViewById(R.id.url_textview)
    }
}