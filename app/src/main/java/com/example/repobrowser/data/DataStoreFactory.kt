package com.example.repobrowser.data

import javax.inject.Inject

class DataStoreFactory @Inject constructor(private val remoteDataStore: RemoteDataStore) {
    fun getDataStore() : DataStore {
        // For simplicity we only use remote data store,
        // but for an advanced project we should add local data store as well
        return remoteDataStore
    }
}