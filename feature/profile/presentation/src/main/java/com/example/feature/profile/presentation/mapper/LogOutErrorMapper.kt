package com.example.feature.profile.presentation.mapper

import com.example.feature.profile.domain.util.LogOutError
import com.example.core.presentation.R


fun LogOutError.asStringResource(): Int {
    return when (this) {
        LogOutError.SOMETHING_WENT_WRONG -> R.string.something_went_wrong_try_again
    }
}