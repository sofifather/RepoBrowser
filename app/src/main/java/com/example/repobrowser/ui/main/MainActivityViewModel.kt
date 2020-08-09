package com.example.repobrowser.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.repobrowser.data.model.Status
import com.example.repobrowser.data.repository.GithubRepositoryRepository
import com.example.repobrowser.ui.model.RepositoryView
import com.example.repobrowser.ui.model.toView
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor
import kotlinx.coroutines.launch
import okhttp3.internal.platform.Platform
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class MainActivityViewModel(private val githubRepositoryRepository: GithubRepositoryRepository,
                            private val mainThreadExecutor: Executor) : ViewModel() {
    private var _refresh = MutableLiveData<Boolean>(true)
    private var _error = MutableLiveData<Boolean>(false)
    private var _pagedList = MutableLiveData<PagedList<RepositoryView>>().apply {
        value = buildPagedList()
    }


    val pagedList = _pagedList
    val refresh = _refresh
    val error = _error

    fun refresh() {
        _pagedList.postValue(buildPagedList())
    }

    private fun buildPagedList(): PagedList<RepositoryView> {
        val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            _refresh.postValue(false)
        }
        return PagedList.Builder (
            object : PageKeyedDataSource<String, RepositoryView>(){
                override fun loadInitial(
                    params: LoadInitialParams<String>,
                    callback: LoadInitialCallback<String, RepositoryView>
                ) {
                    viewModelScope.launch(Dispatchers.IO + exceptionHandler){
                        githubRepositoryRepository.getOrgRepositoriesListPage("square").also { listPage ->
                            refresh.postValue(false)
                            if (listPage.status == Status.FAILURE) {
                                error.postValue(true)
                                return@also
                            } else {
                                error.postValue(false)
                            }
                            callback.onResult(
                                listPage.repos.map { it.toView() },
                                listPage.paginationInfo?.prev,
                                listPage.paginationInfo?.next
                            )
                        }
                    }
                }

                override fun loadAfter(
                    params: LoadParams<String>,
                    callback: LoadCallback<String, RepositoryView>
                ) {
                    refresh.postValue(true)
                    viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
                        githubRepositoryRepository.getRepositoriesListPageUrl(params.key).let { lp->
                            refresh.postValue(false)
                            if (lp.status == Status.FAILURE) {
                                error.postValue(true)
                                return@let
                            }else {
                                error.postValue(false)
                            }
                            callback.onResult(lp.repos.map { it.toView() }, lp.paginationInfo?.next)
                        }
                    }
                }

                override fun loadBefore(
                    params: LoadParams<String>,
                    callback: LoadCallback<String, RepositoryView>
                ) {
                    viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
                        githubRepositoryRepository.getRepositoriesListPageUrl(params.key).let { lp->
                            if (lp.status == Status.FAILURE) {
                                error.postValue(true)
                                return@let
                            }else {
                                error.postValue(false)
                            }
                            callback.onResult(lp.repos.map { it.toView() }, lp.paginationInfo?.prev)
                        }
                    }
                }
            }, 30
        )
            .setNotifyExecutor(mainThreadExecutor)
            .setFetchExecutor(mainThreadExecutor)
            .build()
    }
}