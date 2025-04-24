package com.example.feature.register.presentation.extension

import com.example.feature.register.domain.util.RepeatPasswordError
import com.example.resource.R

fun RepeatPasswordError.asStringResource(): Int {
    return when (this) {
        RepeatPasswordError.NO_MATCH -> R.string.password_should_be_same
        RepeatPasswordError.BLANK_FIELD -> R.string.field_can_not_be_blank
    }
}