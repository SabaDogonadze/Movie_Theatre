package com.example.feature.movie_quiz.presentation.screen.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.example.core.presentation.extension.collectLatestFlow
import com.example.core.presentation.extension.showSnackBar
import com.example.presentation.databinding.FragmentQuizResultDialogBinding
import com.example.resource.R

class QuizResultDialog : BaseQuizDialog() {


    private val viewModel: QuizResultViewModel by activityViewModels()
    private var correctAnswers: Int = 0
    private var totalQuestions: Int = 0
    private var coins: Int = 0

    private var _binding: FragmentQuizResultDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            correctAnswers = it.getInt(ARG_CORRECT_ANSWERS)
            totalQuestions = it.getInt(ARG_TOTAL_QUESTIONS)
            coins = it.getInt(COINS)

        }
    }

    override fun createDialogView(): View {
        _binding = FragmentQuizResultDialogBinding.inflate(LayoutInflater.from(context))

        val passThreshold = (totalQuestions * 0.7).toInt()
        val passed = correctAnswers >= passThreshold

        if (passed) {
            binding.tvResultTitle.text = getString(R.string.congratulations)
            binding.tvResultMessage.text = getString(R.string.you_passed_the_quiz)
            binding.ivResultImage.setImageResource(R.drawable.ic_coin)

            viewModel.onEvent(
                com.example.feature.movie_quiz.presentation.event.QuizResultEvent.UpdateUserCoin(
                    coins = coins
                )
            )


        } else {
            binding.tvResultTitle.text = getString(R.string.better_luck_next_time)
            binding.tvResultMessage.text = getString(R.string.you_didn_t_pass_this_quiz)
            binding.ivResultImage.setImageResource(R.drawable.ic_home)
        }

        binding.tvScore.text =
            getString(R.string.out_of, correctAnswers.toString(), totalQuestions.toString())


        binding.btnHome.setOnClickListener {
            setFragmentResult("quiz_result", bundleOf())
            dismissAndContinue {
                requireParentFragment().findNavController()
                    .popBackStack(R.id.quiz_category_fragment, false)
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collectLatestFlow(viewModel.uiState) {
            binding.progressBar.root.isVisible = it.isLoading
        }

        collectLatestFlow(viewModel.sideEffects) {
            when (it) {
                is com.example.feature.movie_quiz.presentation.event.QuizResultSideEffect.ShowError -> binding.root.showSnackBar(
                    getString(it.message),
                    backgroundColor = R.color.red
                )
            }
        }

    }

    override fun onStart() {
        super.onStart()
        dialog?.setCanceledOnTouchOutside(false)
        dialog?.setCancelable(false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }

    companion object {
        private const val ARG_CORRECT_ANSWERS = "correct_answers"
        private const val ARG_TOTAL_QUESTIONS = "total_questions"
        private const val COINS = "coins"

        fun newInstance(correctAnswers: Int, totalQuestions: Int, coins: Int): QuizResultDialog {
            return QuizResultDialog().apply {
                arguments = Bundle().apply {
                    putInt(ARG_CORRECT_ANSWERS, correctAnswers)
                    putInt(ARG_TOTAL_QUESTIONS, totalQuestions)
                    putInt(COINS, coins)

                }
            }
        }
    }
}