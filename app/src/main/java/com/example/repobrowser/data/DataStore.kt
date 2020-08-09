package com.example.repobrowser.data

import com.example.repobrowser.data.model.ListPage

interface DataStore {
    suspend fun getOrgRepos(org: String): ListPage
    suspend fun getOrgReposPage(url: String): ListPage
}