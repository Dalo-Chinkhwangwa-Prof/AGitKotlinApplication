package com.illicitintelligence.kotlinapplication.network

import com.illicitintelligence.kotlinapplication.model.commit.CommitResult
import com.illicitintelligence.kotlinapplication.model.repository.RepoResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GitService {
    @GET("/users/{userName}/repos")
    fun getRepositories(@Path("userName") userName: String): Call<List<RepoResult>>

    @GET("/repos/{userName}/{repositoryName}/commits")
    fun getCommits(
        @Path("userName") userName: String,
        @Path("repositoryName") repositoryName: String
    ): Call<List<CommitResult>>

}