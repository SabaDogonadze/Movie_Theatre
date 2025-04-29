package com.example.core.presentation.component

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core.presentation.R


@Composable
fun FormField(
    value: String,
    onValueChange: (String) -> Unit,
    @StringRes labelResId: Int,
    icon: ImageVector,
    isError: Boolean = false,
    errorMessage: String? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    onFocusChanged: (Boolean) -> Unit = {},
) {
    Column {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = {
                Text(
                    stringResource(id = labelResId),
                    color = if (isError) colorResource(R.color.red) else colorResource(R.color.light_gray)
                )
            },
            textStyle = TextStyle(color = Color.White),
            trailingIcon = {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = when {
                        isError -> Color.Red
                        value.isNotEmpty() -> Color.White
                        else -> colorResource(R.color.light_gray)
                    }
                )
            },
            isError = isError,
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    onFocusChanged(focusState.isFocused)
                },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(R.color.white),
                unfocusedBorderColor = colorResource(R.color.light_gray),
                focusedLabelColor = colorResource(R.color.white),
                unfocusedLabelColor = colorResource(R.color.light_gray)
            ),
            singleLine = true
        )

        /**
        Error text below of filed
         */
        AnimatedVisibility(visible = errorMessage != null) {
            errorMessage?.let {
                Text(
                    text = it,
                    color = colorResource(R.color.red),
                    fontSize = 12.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 4.dp, top = 4.dp),
                    textAlign = TextAlign.Start
                )
            }
        }
    }
}