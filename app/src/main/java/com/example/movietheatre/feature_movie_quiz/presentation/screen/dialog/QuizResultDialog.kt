package com.example.movietheatre.feature_movie_quiz.presentation.screen.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.movietheatre.R
import com.example.movietheatre.databinding.FragmentQuizResultDialogBinding

class QuizResultDialog : BaseQuizDialog() {

    private var correctAnswers: Int = 0
    private var totalQuestions: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            correctAnswers = it.getInt(ARG_CORRECT_ANSWERS)
            totalQuestions = it.getInt(ARG_TOTAL_QUESTIONS)
        }
    }

    override fun createDialogView(): View {
        val binding = FragmentQuizResultDialogBinding.inflate(LayoutInflater.from(context))

        // Calculate pass threshold (e.g., 70%)
        val passThreshold = (totalQuestions * 0.7).toInt()
        val passed = correctAnswers >= passThreshold

        // Set result message and image based on pass/fail
        if (passed) {
            binding.tvResultTitle.text = "Congratulations!"
            binding.tvResultMessage.text = "You passed the quiz!"
            binding.ivResultImage.setImageResource(R.drawable.ic_coin)
        } else {
            binding.tvResultTitle.text = "Better luck next time!"
            binding.tvResultMessage.text = "You didn't pass this quiz"
            binding.ivResultImage.setImageResource(R.drawable.ic_home)
        }

        // Display score
        binding.tvScore.text = "$correctAnswers out of $totalQuestions"

        binding.btnHome.setOnClickListener {
            dismissAndContinue {
                val navController = requireParentFragment().findNavController()
                navController.popBackStack()
            }
        }

        return binding.root
    }

    companion object {
        private const val ARG_CORRECT_ANSWERS = "correct_answers"
        private const val ARG_TOTAL_QUESTIONS = "total_questions"

        fun newInstance(correctAnswers: Int, totalQuestions: Int): QuizResultDialog {
            return QuizResultDialog().apply {
                arguments = Bundle().apply {
                    putInt(ARG_CORRECT_ANSWERS, correctAnswers)
                    putInt(ARG_TOTAL_QUESTIONS, totalQuestions)
                }
            }
        }
    }
}