package com.example.movietheatre.feature_movie_quiz.presentation.screen

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.movietheatre.core.presentation.BaseFragment
import com.example.movietheatre.core.presentation.extension.collectLatestFlow
import com.example.movietheatre.databinding.FragmentQuizBinding
import com.example.movietheatre.feature_movie_quiz.presentation.event.QuizEvent
import com.example.movietheatre.feature_movie_quiz.presentation.event.QuizSideEffect
import com.example.movietheatre.feature_movie_quiz.presentation.screen.dialog.QuizResultDialog
import com.example.movietheatre.feature_movie_quiz.presentation.screen.dialog.TimeUpDialog
import com.example.movietheatre.feature_movie_quiz.presentation.screen.dialog.WrongAnswerDialog
import com.example.movietheatre.feature_movie_quiz.presentation.state.QuizState
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuizFragment : BaseFragment<FragmentQuizBinding>(FragmentQuizBinding::inflate) {

    private val viewModel: QuizViewModel by viewModels()
    private val args: QuizFragmentArgs by navArgs()
    private lateinit var quizAnswerAdapter: QuizAnswerAdapter


    override fun setUp() {
        viewModel.onEvent(QuizEvent.LoadQuestions(args.categoryId))
        setupRecyclerView()
        observeState()
        observeEffects()
    }

    override fun clickListeners() {
        binding.btnNext.setOnClickListener {
            viewModel.onEvent(QuizEvent.NextQuestion)
        }
        quizAnswerAdapter.setOnItemClickListener { answerId ->
            viewModel.onEvent(QuizEvent.SelectAnswer(answerId))
        }
    }

    private fun setupRecyclerView() {
        quizAnswerAdapter = QuizAnswerAdapter()
        binding.recyclerAnswers.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = quizAnswerAdapter
            itemAnimator = null
        }
    }

    private fun observeState() {
        collectLatestFlow(viewModel.state) { state ->
            updateUIWithState(state)
        }
    }

    private fun observeEffects() {
        collectLatestFlow(viewModel.effect) { effect ->
            handleEffect(effect)
        }
    }

    private fun updateUIWithState(state: QuizState) {

        quizAnswerAdapter.updateSelection(
            selectedId = state.selectedAnswerId,
            correctId = state.currentQuestion?.correctOptionId,
            showResult = state.hasAnswered
        )

        if (state.error != null) {
            Snackbar.make(binding.root, state.error, Snackbar.LENGTH_LONG).show()
            return
        }


        state.currentQuestion?.let { question ->
            binding.tvQuestionNumber.text =
                "Question ${state.currentQuestionIndex + 1}/${state.totalQuestions}"
            binding.linearIndicator.max = state.totalQuestions
            binding.linearIndicator.progress = state.currentQuestionIndex + 1

            binding.tvQuestion.text = question.question
             Glide.with(this).load(question.imageUrl).into(binding.ivQuizImage)

            // Update answer options
            quizAnswerAdapter.submitList(question.options.toList())

            if (!state.hasAnswered) {
                quizAnswerAdapter.updateSelection(null, null, false)
            }
        }

        binding.tvTimer.text = "${state.timeRemaining}s"

        binding.btnNext.isEnabled = state.hasAnswered
    }

    private fun handleEffect(effect: QuizSideEffect) {
        when (effect) {
            is QuizSideEffect.ShowMessage -> {
                Snackbar.make(binding.root, effect.message, Snackbar.LENGTH_SHORT).show()
            }

            is QuizSideEffect.ShowWrongAnswerDialog -> {
                showWrongAnswerDialog()
            }

            is QuizSideEffect.ShowTimeUpDialog -> {
                showTimeUpDialog()
            }

            is QuizSideEffect.NavigateToResults -> {
                showQuizResultDialog()
            }
        }
    }

    private fun showWrongAnswerDialog() {
        WrongAnswerDialog().show(childFragmentManager, "wrong_answer_dialog")
    }

    private fun showTimeUpDialog() {
        TimeUpDialog().show(childFragmentManager, "time_up_dialog")
    }

    private fun showQuizResultDialog() {
        val state = viewModel.state.value
        QuizResultDialog.newInstance(
            correctAnswers = state.correctAnswersCount,
            totalQuestions = state.totalQuestions
        ).show(childFragmentManager, "quiz_result_dialog")
    }
}