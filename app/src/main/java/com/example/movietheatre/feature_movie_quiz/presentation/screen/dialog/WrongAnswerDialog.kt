package com.example.movietheatre.feature_movie_quiz.presentation.screen.dialog

import android.view.LayoutInflater
import android.view.View
import com.example.movietheatre.databinding.FragmentWrongAnswerDialogBinding

class WrongAnswerDialog : BaseQuizDialog() {

    override fun createDialogView(): View {
        val binding = FragmentWrongAnswerDialogBinding.inflate(LayoutInflater.from(context))

        binding.btnContinue.setOnClickListener {
            dismiss()
        }

        return binding.root
    }

    companion object {
        fun newInstance() = WrongAnswerDialog()
    }
}