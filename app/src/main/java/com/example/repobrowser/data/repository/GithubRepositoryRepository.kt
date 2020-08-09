package com.example.repobrowser.data.repository

import com.example.repobrowser.data.DataStoreFactory
import com.example.repobrowser.data.model.ListPage
import javax.inject.Inject

class GithubRepositoryRepository @Inject constructor(private val dataStoreFactory: DataStoreFactory) {

    suspend fun getOrgRepositoriesListPage(org: String): ListPage {
        return dataStoreFactory.getDataStore().getOrgRepos(org)
    }

    suspend fun getRepositoriesListPageUrl(url: String): ListPage {
        return dataStoreFactory.getDataStore().getOrgReposPage(url)
    }

}