package com.example.feature.seat.presentation.screen.panorama

import android.animation.ValueAnimator
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator

fun View.animateAlpha(startValue: Float, endValue: Float, duration: Long) {
    val valueAnimator = ValueAnimator.ofFloat(startValue, endValue)
    valueAnimator.addUpdateListener { animator ->
        this.alpha = animator.animatedValue as Float
    }
    valueAnimator.interpolator = AccelerateDecelerateInterpolator()
    valueAnimator.duration = duration
    valueAnimator.start()
}