package com.example.core.presentation.extension

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalView

@Composable
fun hideKeyboard(): () -> Unit {
    val focusManager = LocalFocusManager.current
    val view = LocalView.current
    val context = LocalContext.current

    return {
        focusManager.clearFocus()
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}