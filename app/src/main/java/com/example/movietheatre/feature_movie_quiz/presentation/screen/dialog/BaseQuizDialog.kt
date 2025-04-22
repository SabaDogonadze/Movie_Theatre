package com.example.movietheatre.feature_movie_quiz.presentation.screen.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

abstract class BaseQuizDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setView(createDialogView())
            .setCancelable(false)
            .create()
            .apply {
                window?.setBackgroundDrawableResource(android.R.color.transparent)
            }
    }

    abstract fun createDialogView(): View

    protected fun dismissAndContinue(action: () -> Unit) {
        dismiss()
        action()
    }

    override fun onDestroy() {
        super.onDestroy() }
}