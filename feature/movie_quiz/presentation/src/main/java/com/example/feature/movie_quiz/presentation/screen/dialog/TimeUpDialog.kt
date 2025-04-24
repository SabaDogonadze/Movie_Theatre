package com.example.feature.movie_quiz.presentation.screen.dialog

import android.view.LayoutInflater
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.resource.R
import com.example.presentation.databinding.FragmentTimeUpDialogBinding

class TimeUpDialog : BaseQuizDialog() {

    override fun createDialogView(): View {
        val binding = FragmentTimeUpDialogBinding.inflate(LayoutInflater.from(context))


        binding.btnContinue.setOnClickListener {
            dismissAndContinue {

                requireParentFragment().findNavController()
                    .popBackStack(R.id.quizCategoryFragment, false)
            }
        }

        return binding.root
    }

    companion object {
        fun newInstance() = TimeUpDialog()
    }
}