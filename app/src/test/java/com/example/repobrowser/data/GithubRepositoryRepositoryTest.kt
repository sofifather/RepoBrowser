package com.example.repobrowser.data

import com.example.repobrowser.data.repository.GithubRepositoryRepository
import com.example.repobrowser.di.modules.NetworkModule
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GithubRepositoryRepositoryTest {

    lateinit var githubRepositoryRepository: GithubRepositoryRepository

    @Before
    fun setUp() {
        githubRepositoryRepository =
            GithubRepositoryRepository(
                DataStoreFactory(
                    RemoteDataStore(
                        NetworkModule.apiService(
                            NetworkModule.retrofit(
                                NetworkModule.gson(), NetworkModule.okHttpClient()
                            )
                        )
                    )
                )
            )
    }

    @Test
    fun testRepository() {
        runBlocking {
            val listPage = githubRepositoryRepository.getOrgRepositoriesListPage("square")
            Assert.assertTrue(listPage.repos.isNotEmpty())
        }
    }
}