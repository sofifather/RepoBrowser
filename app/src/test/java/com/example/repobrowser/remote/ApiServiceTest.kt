package com.example.repobrowser.remote

import com.example.repobrowser.di.modules.NetworkModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.lang.IllegalArgumentException
import java.net.URL

class ApiServiceTest {

    lateinit var apiService: ApiService

    @Before
    fun setUp() {
        apiService = NetworkModule.apiService(
            NetworkModule.retrofit(
                NetworkModule.gson(), NetworkModule.okHttpClient()
            )
        )
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testApiService() {
        runBlocking {
            var next = ""

            apiService.getRepos("square").apply {
                Assert.assertTrue(code() == 200)

                val paginationInfo = PaginationInfo.createFromHeaderValue(
                    headers()["link"] ?: throw IllegalArgumentException()
                )
                println("First page:")
                URL(paginationInfo.next).toURI().also { uri->
                    println("Next page: $uri")
                    next = uri.toString()
                }
                URL(paginationInfo.last).toURI().also { uri->
                    println("Last page: $uri")
                }
            }


            apiService.getReposByUrl(next).apply {
                val pagInfo = PaginationInfo.createFromHeaderValue(
                    headers()["link"] ?: throw IllegalArgumentException()
                )
                println("Next page:")
                URL(pagInfo.prev).toURI().also { uri->
                    println("Prev page: $uri")
                }
                URL(pagInfo.first).toURI().also { uri->
                    println("First page: $uri")
                }
            }
        }
    }
}