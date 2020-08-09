package com.example.repobrowser.remote

import retrofit2.Response
import retrofit2.http.*

/**
 * Accessed from https://api.github.com
 */
interface ApiService {

    @Headers("Accept: application/vnd.github.v3+json")
    @GET("/orgs/{org}/repos")
    suspend fun getRepos(
        @Path("org") org: String
    ): Response<List<GithubRemoteRepo>>

    @GET
    @Headers("Accept: application/vnd.github.v3+json")
    suspend fun getReposByUrl(@Url url: String) : Response<List<GithubRemoteRepo>>
}