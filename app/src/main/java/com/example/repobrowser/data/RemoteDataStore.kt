package com.example.repobrowser.data

import com.example.repobrowser.data.model.ListPage
import com.example.repobrowser.data.model.Status
import com.example.repobrowser.data.model.toGithubRepository
import com.example.repobrowser.remote.ApiService
import com.example.repobrowser.remote.GithubRemoteRepo
import com.example.repobrowser.remote.PaginationInfo
import retrofit2.Response
import javax.inject.Inject

class RemoteDataStore @Inject constructor(private val apiService: ApiService) : DataStore {
    override suspend fun getOrgRepos(org: String): ListPage {
        return buildListPage(apiService.getRepos(org))
    }

    override suspend fun getOrgReposPage(url: String): ListPage {
        return buildListPage(apiService.getReposByUrl(url))
    }

    private fun buildListPage(response: Response<List<GithubRemoteRepo>>) : ListPage {
        val remoteRepoList = response.body() ?: emptyList()
        var paginationInfo: PaginationInfo? = null
        response.headers()["link"]?.also {
            paginationInfo = PaginationInfo.createFromHeaderValue(it)
        }
        val repoList = remoteRepoList.map {
            it.toGithubRepository()
        }
        return ListPage(
            repoList,
            paginationInfo,
            when(response.code()) {
                200 -> Status.SUCCESS
                else -> Status.FAILURE
            }
        )
    }


}