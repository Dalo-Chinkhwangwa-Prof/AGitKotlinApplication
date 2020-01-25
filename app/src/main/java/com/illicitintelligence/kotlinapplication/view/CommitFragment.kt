package com.illicitintelligence.kotlinapplication.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.illicitintelligence.kotlinapplication.R
import com.illicitintelligence.kotlinapplication.adapter.CommitsAdapter
import com.illicitintelligence.kotlinapplication.model.commit.CommitResult
import com.illicitintelligence.kotlinapplication.viewmodel.GitViewModel
import kotlinx.android.synthetic.main.commits_fragment_layout.*

class CommitFragment: Fragment() {

    companion object{
        val FULL_NAME_KEY = "FULL_NAME"
    }

    private lateinit var viewModel: GitViewModel
    private lateinit var commitObserver: Observer<List<CommitResult>>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.commits_fragment_layout, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(GitViewModel::class.java)

        commitObserver = Observer{ commits ->
            val commitsAdapter = CommitsAdapter(commits)
            commits_recyclerview.adapter = commitsAdapter
            commits_recyclerview.layoutManager = LinearLayoutManager(context?.applicationContext)
        }

        arguments?.getStringArray(FULL_NAME_KEY)?.let { fullName ->
            viewModel.getCommits(fullName[0], fullName[1]).observe(this, commitObserver)
        }

    }
}