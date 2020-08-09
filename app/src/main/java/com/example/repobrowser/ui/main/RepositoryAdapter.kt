package com.example.repobrowser.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.repobrowser.R
import com.example.repobrowser.ui.model.RepositoryView
import org.w3c.dom.Text

class RepositoryAdapter : PagedListAdapter<RepositoryView, RepositoryItemViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryItemViewHolder {
        return LayoutInflater.from(parent.context)
            .inflate(R.layout.item_repository, parent,false).let {
                RepositoryItemViewHolder(it)
            }
    }

    override fun onBindViewHolder(holder: RepositoryItemViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it, position + 1)
        }
    }
}

class RepositoryItemViewHolder(private val v: View) : RecyclerView.ViewHolder(v) {

    fun bind(repository: RepositoryView, position: Int) {
        v.findViewById<TextView>(R.id.name).apply {
            text = repository.name
        }
        v.findViewById<TextView>(R.id.position).apply {
            text = "$position"
        }
        repository.description?.also {
            v.findViewById<TextView>(R.id.description).apply {
                text = it
            }
        }
    }
}

val DIFF_CALLBACK = object : DiffUtil.ItemCallback<RepositoryView>() {
    override fun areItemsTheSame(oldItem: RepositoryView, newItem: RepositoryView): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: RepositoryView, newItem: RepositoryView): Boolean {
        return oldItem == newItem
    }
}