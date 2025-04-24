package com.example.core.presentation.extension

import android.content.Context
import android.content.res.ColorStateList
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import com.bumptech.glide.Glide
import com.example.resource.R
import com.google.android.material.snackbar.Snackbar


/**
 * to show snackbar with different background color and text color
 * */
fun View.showSnackBar(
    message: String,
    @ColorRes backgroundColor: Int = R.color.black,
    @ColorRes textColor: Int = R.color.white,
) {
    val snackBar = Snackbar.make(this, message, Snackbar.LENGTH_SHORT)
    val bgColorValue = ContextCompat.getColor(context, backgroundColor)
    val textColorValue = ContextCompat.getColor(context, textColor)
    snackBar.view.apply {
        setBackgroundColor(bgColorValue)
        backgroundTintList = ColorStateList.valueOf(bgColorValue)

        findViewById<TextView>(com.google.android.material.R.id.snackbar_text)?.let { textView ->
            textView.setTextColor(textColorValue)
            textView.setBackgroundColor(bgColorValue)
        }
    }

    snackBar.show()
}

/**
 * hides already open keyboard
 * */
fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun ImageView.setTint(@ColorRes colorRes: Int) {
    ImageViewCompat.setImageTintList(
        this,
        ColorStateList.valueOf(ContextCompat.getColor(context, colorRes))
    )
}

fun ImageView.loadImg(url: String?) {
    Glide.with(this.context)
        .load(url)
        .placeholder(R.drawable.ic_launcher_background)
        .error(R.drawable.ic_launcher_background)
        .into(this)
}
