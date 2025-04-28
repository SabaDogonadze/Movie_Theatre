package com.example.movietheatre

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.example.core.domain.preference_key.PreferenceKeys
import com.example.core.domain.use_case.GetValueFromLocalStorageUseCase
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    @Inject
    lateinit var getValueFromLocalStorageUseCase: GetValueFromLocalStorageUseCase

    override fun onCreate() {
        super.onCreate()
        val langCode = runBlocking {
            getValueFromLocalStorageUseCase(PreferenceKeys.SELECTED_LANGUAGE, "en")
                .first()
        }

        val appLocales = LocaleListCompat.forLanguageTags(langCode)
        AppCompatDelegate.setApplicationLocales(appLocales)
    }

}
