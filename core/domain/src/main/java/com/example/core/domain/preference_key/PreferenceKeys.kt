package com.example.core.domain.preference_key

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object PreferenceKeys {
    val REMEMBER_ME = booleanPreferencesKey("remember_me")
    val SELECTED_LANGUAGE = stringPreferencesKey("selected_language")

}