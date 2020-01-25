package com.illicitintelligence.kotlinapplication.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.illicitintelligence.kotlinapplication.R
import com.illicitintelligence.kotlinapplication.adapter.RepositoryAdapter
import com.illicitintelligence.kotlinapplication.model.repository.RepoResult
import com.illicitintelligence.kotlinapplication.viewmodel.GitViewModel
import kotlinx.android.synthetic.main.activity_repositories.*

class RepositoriesActivity : AppCompatActivity(), RepositoryAdapter.RepositoryDelegate {
    companion object {

        val NAME_KEY = "get_name"
    }

    private val commitFragment = CommitFragment()
    private lateinit var viewModel: GitViewModel

    private lateinit var repositoryObserver: Observer<List<RepoResult>>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repositories)

        viewModel = ViewModelProviders.of(this).get(GitViewModel::class.java)

        repositoryObserver = Observer { repoResults ->

            repoResults[0].owner?.avatarUrl?.let { avatarUrl ->

                Glide.with(this)
                    .applyDefaultRequestOptions(RequestOptions().circleCrop())
                    .load(avatarUrl)
                    .into(profile_imageview)
            }

            val repositoryAdapter = RepositoryAdapter(repoResults, this)
            repository_recyclerview.layoutManager = LinearLayoutManager(this)
            repository_recyclerview.adapter = repositoryAdapter
        }

        intent?.getStringExtra(NAME_KEY)?.let { name ->

            viewModel.getRepositories(name).observe(this, repositoryObserver)

            Toast.makeText(this, name, Toast.LENGTH_SHORT).show()
        }
    }

    override fun getCommits(userName: String, repositoryName: String) {

        val bundle = Bundle()
        bundle.putStringArray(CommitFragment.FULL_NAME_KEY, arrayOf(userName, repositoryName))
        commitFragment.arguments = bundle

        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.scale_out,
                R.anim.scale_in,
                R.anim.scale_out,
                R.anim.scale_in
            )
            .replace(R.id.main_frame, commitFragment)
            .addToBackStack(commitFragment.tag)
            .commit()
    }
}
