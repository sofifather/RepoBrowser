package com.example.repobrowser.data.model

import com.example.repobrowser.data.model.GithubRepository
import com.example.repobrowser.remote.GithubRemoteRepo

fun GithubRemoteRepo.toGithubRepository(): GithubRepository {
    return GithubRepository(
        id,
        node_id,
        name,
        private,
        owner.avatar_url,
        owner.login,
        html_url,
        description,
        fork,
        url,
        created_at,
        updated_at,
        pushed_at,
        homepage,
        size,
        watchers,
        default_branch
    )
}