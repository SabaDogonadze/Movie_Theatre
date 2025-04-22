package com.example.movietheatre.feature_movie_quiz.presentation.screen.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
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

        val passThreshold = (totalQuestions * 0.7).toInt()
        val passed = correctAnswers >= passThreshold

        if (passed) {
            binding.tvResultTitle.text = getString(R.string.congratulations)
            binding.tvResultMessage.text = getString(R.string.you_passed_the_quiz)
            binding.ivResultImage.setImageResource(R.drawable.ic_coin)

        } else {
            binding.tvResultTitle.text = getString(R.string.better_luck_next_time)
            binding.tvResultMessage.text = getString(R.string.you_didn_t_pass_this_quiz)
            binding.ivResultImage.setImageResource(R.drawable.ic_home)
        }

         binding.tvScore.text = getString(R.string.out_of, correctAnswers.toString(), totalQuestions.toString())

        binding.btnHome.setOnClickListener {
            setFragmentResult("quiz_result", bundleOf())
            dismissAndContinue {
                requireParentFragment().findNavController()
                    .popBackStack(R.id.quizCategoryFragment, false)
            }
        }
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.setCanceledOnTouchOutside(false)
        dialog?.setCancelable(false)
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