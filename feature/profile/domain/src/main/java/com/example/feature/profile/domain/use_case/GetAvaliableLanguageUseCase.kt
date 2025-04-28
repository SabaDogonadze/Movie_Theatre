package com.example.feature.profile.domain.use_case

import com.example.feature.profile.domain.model.Language
import javax.inject.Inject

class GetAvailableLanguagesUseCase @Inject constructor() {
    operator fun invoke(): List<Language> {
        return listOf(
            Language("en", "English"),
            Language("ka", "ქართული"),
        )
    }
}