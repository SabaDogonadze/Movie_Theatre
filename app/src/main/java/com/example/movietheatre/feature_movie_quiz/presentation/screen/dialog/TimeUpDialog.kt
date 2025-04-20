package com.example.movietheatre.feature_movie_quiz.presentation.screen.dialog

import android.view.LayoutInflater
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.movietheatre.databinding.FragmentTimeUpDialogBinding

class TimeUpDialog : BaseQuizDialog() {

    override fun createDialogView(): View {
        val binding = FragmentTimeUpDialogBinding.inflate(LayoutInflater.from(context))

        binding.btnContinue.setOnClickListener {
            dismissAndContinue {
                // Navigate back to categories or restart quiz
                findNavController().navigateUp()
            }
        }

        return binding.root
    }

    companion object {
        fun newInstance() = TimeUpDialog()
    }
}