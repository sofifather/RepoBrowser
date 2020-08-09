package com.example.repobrowser.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.repobrowser.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val repositoryAdapter = RepositoryAdapter()
        list.apply {
            adapter = repositoryAdapter
        }
        val viewModel = ViewModelProvider(this, viewModelFactory)[MainActivityViewModel::class.java]
        viewModel.pagedList.observe(this@MainActivity, Observer {
            repositoryAdapter.submitList(it)
        })
        viewModel.refresh.observe(this@MainActivity, Observer { refreshing->
            refreshContainer.isRefreshing = refreshing
        })
        viewModel.error.observe(this@MainActivity, Observer { failure->
            if (failure) {
                Toast.makeText(this@MainActivity, "Error", Toast.LENGTH_LONG).show()
            }
        })
        refreshContainer.setOnRefreshListener {
            viewModel.refresh()
        }
    }

}