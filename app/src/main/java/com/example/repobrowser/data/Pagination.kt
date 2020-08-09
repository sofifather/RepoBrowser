package com.example.repobrowser.data

import com.example.repobrowser.data.model.ListPage

interface Pagination {
    suspend fun next(): ListPage?
    suspend fun prev(): ListPage?
    suspend fun last(): ListPage?
    suspend fun first(): ListPage?
}