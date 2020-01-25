package com.illicitintelligence.kotlinapplication.network

import com.illicitintelligence.kotlinapplication.model.commit.CommitResult
import com.illicitintelligence.kotlinapplication.model.repository.RepoResult
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GitRetrofitInstance {
    private val BASE_URL = "https://api.github.com"
    var gitService: GitService

    init {
        gitService = createService(createRetrofit())
    }

    private fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }

    private fun createService(retrofit: Retrofit): GitService {
        return retrofit.create(GitService::class.java)
    }

    fun getRepositories(userName: String): Call<List<RepoResult>>{
        return gitService.getRepositories(userName)
    }

    fun getCommits(userName: String, repositoryName: String): Call<List<CommitResult>>{
        return gitService.getCommits(userName, repositoryName)
    }

}