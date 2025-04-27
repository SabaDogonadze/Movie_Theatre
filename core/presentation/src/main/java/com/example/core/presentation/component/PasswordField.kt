package com.example.core.presentation.component

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core.presentation.R

@Composable
fun PasswordField(
    value: String,
    onValueChange: (String) -> Unit,
    @StringRes labelResId: Int,
    isVisible: Boolean,
    onVisibilityChange: () -> Unit,
    isError: Boolean = false,
    errorMessage: String? = null,
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
            visualTransformation = if (isVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = onVisibilityChange) {
                    Icon(
                        imageVector = if (isVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = stringResource(R.string.toggle_password_visibility),
                        tint = when {
                            isError -> Color.Red
                            value.isNotEmpty() -> Color.White
                            else -> colorResource(R.color.light_gray)
                        }

                    )
                }
            },
            isError = isError,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    onFocusChanged(focusState.isFocused)
                },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = colorResource(R.color.light_gray),
                focusedLabelColor = Color.White,
                unfocusedLabelColor = colorResource(R.color.light_gray)
            ),
            singleLine = true
        )

        // Error text
        AnimatedVisibility(visible = errorMessage != null) {
            errorMessage?.let {
                Text(
                    text = it,
                    color = Color.Red,
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

@Preview(showBackground = true, backgroundColor = 0xFF161659)
@Composable
fun PasswordFieldPreview() {
    PasswordField(
        value = "asdstrd",
        onValueChange = { },
        labelResId = R.string.enter_password,
        isVisible = true,
        onVisibilityChange = {},
        isError = true,
        errorMessage = "",
        onFocusChanged = { true }
    )
}