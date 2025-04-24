package com.example.core.domain.manager

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

interface UserSessionManager {
    suspend fun <T> saveValue(key: Preferences.Key<T>, value: T)
    fun <T> readValue(key: Preferences.Key<T>, defaultValue: T): Flow<T>
    suspend fun clear()
}