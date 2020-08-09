package com.example.repobrowser.di.modules

import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.repobrowser.data.repository.GithubRepositoryRepository
import com.example.repobrowser.ui.main.MainActivityViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import java.util.concurrent.Executor

@Module
@InstallIn(ActivityComponent::class)
object ActivityModule {


    @Provides
    fun mainThreadExecutor(@ActivityContext c: Context): Executor {
        return object : Executor {
            private val handler = Handler(Looper.getMainLooper())
            override fun execute(command: Runnable) {
                handler.post(command)
            }
        }
    }

    @Provides
    fun viewModelFactory(githubRepositoryRepository: GithubRepositoryRepository,
                         mainThreadExecutor: Executor) : ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)){
                    return MainActivityViewModel(
                        githubRepositoryRepository,
                        mainThreadExecutor
                    ) as T
                } else {
                    throw ClassNotFoundException()
                }
            }
        }
    }
}