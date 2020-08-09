package com.example.repobrowser.ui.model

import com.example.repobrowser.data.model.GithubRepository
import com.example.repobrowser.ui.model.RepositoryView

fun GithubRepository.toView(): RepositoryView {
    return RepositoryView(name, description)
}