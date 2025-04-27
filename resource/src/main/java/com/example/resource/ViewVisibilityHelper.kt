package com.example.resource

import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.view.isVisible



fun View.showWithAnimation(
    viewToHide: View,
    animationDuration: Long = 300,
    onTransitionComplete: (() -> Unit)? = null
) {
    val fadeIn = AnimationUtils.loadAnimation(this.context, R.anim.fade_in).apply {
        duration = animationDuration
    }

    val fadeOut = AnimationUtils.loadAnimation(this.context, R.anim.fade_out).apply {
        duration = animationDuration
    }

    fadeOut.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationStart(animation: Animation?) {}

        override fun onAnimationEnd(animation: Animation?) {
            viewToHide.isVisible = false
            this@showWithAnimation.isVisible = true
            this@showWithAnimation.startAnimation(fadeIn)

            onTransitionComplete?.invoke()
        }

        override fun onAnimationRepeat(animation: Animation?) {}
    })

    if (viewToHide.isVisible) {
        viewToHide.startAnimation(fadeOut)
    } else {
        viewToHide.isVisible = false
        this.isVisible = true
        this.startAnimation(fadeIn)

        onTransitionComplete?.invoke()
    }
}


fun View.crossfadeWith(
    from: View,
    duration: Long = 300,
    onTransitionComplete: (() -> Unit)? = null
) {
    this.alpha = 0f
    this.visibility = View.VISIBLE

    from.animate()
        .alpha(0f)
        .setDuration(duration)
        .withEndAction {
            from.visibility = View.GONE
        }

    this.animate()
        .alpha(1f)
        .setDuration(duration)
        .withEndAction {
            onTransitionComplete?.invoke()
        }
}