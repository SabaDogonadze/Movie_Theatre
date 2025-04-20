package com.example.movietheatre.feature_movie_quiz.presentation.screen.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.movietheatre.feature_movie_quiz.presentation.event.QuizEvent
import com.example.movietheatre.feature_movie_quiz.presentation.screen.QuizViewModel

abstract class BaseQuizDialog : DialogFragment() {

  //  protected val viewModel: QuizViewModel by activityViewModels()

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
       // viewModel.onEvent(QuizEvent.DialogDismissed)
        action()
    }

    override fun onDestroy() {
        super.onDestroy()
  //viewModel.onEvent(QuizEvent.DialogDismissed)
    }
}