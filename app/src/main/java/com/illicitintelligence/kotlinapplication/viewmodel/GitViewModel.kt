package com.illicitintelligence.kotlinapplication.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.illicitintelligence.kotlinapplication.model.commit.CommitResult
import com.illicitintelligence.kotlinapplication.model.repository.RepoResult
import com.illicitintelligence.kotlinapplication.network.GitRetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GitViewModel(var applicationContext: Application) : AndroidViewModel(applicationContext) {

    private val gitRetrofitInstance = GitRetrofitInstance()
    private val repositoryLiveData = MutableLiveData<List<RepoResult>>()
    private val commitLiveData = MutableLiveData<List<CommitResult>>()

    fun getRepositories(userName: String): MutableLiveData<List<RepoResult>> {
        gitRetrofitInstance.getRepositories(userName)
            .enqueue(
                object : Callback<List<RepoResult>> {
                    override fun onFailure(call: Call<List<RepoResult>>, t: Throwable) {
                        Log.d("TAG_X", t.message)
                    }

                    override fun onResponse(
                        call: Call<List<RepoResult>>,
                        response: Response<List<RepoResult>>
                    ) {
                        Log.d("TAG_X", "${response.body()}\n${call.request().url()}")

                        response.body()?.let { repositories ->
                            repositoryLiveData.value = repositories
                        }
                    }
                }
            )
        return repositoryLiveData
    }

    fun getCommits(userName: String, repositoryName: String): MutableLiveData<List<CommitResult>> {

        gitRetrofitInstance.getCommits(userName, repositoryName)
            .enqueue(object : Callback<List<CommitResult>> {
                override fun onFailure(call: Call<List<CommitResult>>, t: Throwable) {
//                    TODO: report error
                    Log.d("TAG_X", t.message)
                }

                override fun onResponse(
                    call: Call<List<CommitResult>>,
                    response: Response<List<CommitResult>>
                ) {
                    Log.d("TAG_X", "${response.body()}\n${call.request().url()}")
                    response.body()?.let { commits ->
                        commitLiveData.value = commits
                    }
                }
            })

        return commitLiveData
    }

}