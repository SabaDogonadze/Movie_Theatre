package com.example.feature.movie_quiz.presentation.screen

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.presentation.BaseFragment
import com.example.core.presentation.R
import com.example.core.presentation.extension.collectLatestFlow
import com.example.core.presentation.extension.loadImg
import com.example.feature.movie_quiz.presentation.databinding.FragmentQuizBinding
import com.example.feature.movie_quiz.presentation.event.QuizEvent
import com.example.feature.movie_quiz.presentation.screen.dialog.OneTimeWarningDialog
import com.example.feature.movie_quiz.presentation.screen.dialog.QuizResultDialog
import com.example.feature.movie_quiz.presentation.screen.dialog.TimeUpDialog
import com.example.feature.movie_quiz.presentation.screen.dialog.WrongAnswerDialog
import com.example.feature.movie_quiz.presentation.state.QuizState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class QuizFragment : BaseFragment<FragmentQuizBinding>(FragmentQuizBinding::inflate) {

    private val viewModel: QuizViewModel by viewModels()
    private val args: QuizFragmentArgs by navArgs()
    private lateinit var quizAnswerAdapter: AnswerRecyclerAdapter

    override fun setUp() {
        viewModel.onEvent(QuizEvent.LoadQuestions(args.categoryId))
        setupRecyclerView()
        observeState()
        observeDialogStates()
    }

    override fun clickListeners() {
        quizAnswerAdapter.setOnItemClickListener { answerId ->
            viewModel.onEvent(QuizEvent.SelectAnswer(answerId))
        }
        binding.btnArrowBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupRecyclerView() {
        quizAnswerAdapter =
            AnswerRecyclerAdapter()
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

    private fun observeDialogStates() {

        collectLatestFlow(viewModel.showWarningDialog) { shouldShow ->
            if (shouldShow) {
                showOneTimeWarningDialog()
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.showWrongAnswerDialog.collect { shouldShow ->
                    if (shouldShow) {
                        showWrongAnswerDialog()
                        viewModel.onEvent(QuizEvent.DialogDismissed)
                    }
                }
            }
        }



        collectLatestFlow(viewModel.showTimeUpDialog) { shouldShow ->
            if (shouldShow) {
                showTimeUpDialog()
                viewModel.onEvent(QuizEvent.DialogDismissed)
            }
        }
        collectLatestFlow(viewModel.showResultsDialog) { shouldShow ->
            if (shouldShow) {
                showQuizResultDialog()
                viewModel.onEvent(QuizEvent.DialogDismissed)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().popBackStack(
                        com.example.feature.movie_quiz.presentation.R.id.quizCategoryFragment,
                        false
                    )
                }
            })
    }


    private fun updateUIWithState(state: QuizState) {
        quizAnswerAdapter.updateSelection(
            selectedId = state.selectedAnswerId,
            correctId = state.currentQuestion?.correctOptionId,
            showResult = state.hasAnswered
        )

        state.currentQuestion?.let { question ->
            binding.tvQuestionNumber.text = getString(
                R.string.question,
                (state.currentQuestionIndex + 1).toString(),
                state.totalQuestions.toString()
            )
            binding.linearIndicator.max = state.totalQuestions
            binding.linearIndicator.progress = state.currentQuestionIndex + 1

            binding.tvQuestion.text = question.question
            binding.ivQuizImage.loadImg(question.imageUrl)
            quizAnswerAdapter.submitList(question.options.toList())

            if (!state.hasAnswered) {
                quizAnswerAdapter.updateSelection(null, null, false)
            }
        }

        binding.tvTimer.text = "${state.timeRemaining}s"

    }

    private fun showWrongAnswerDialog() {
        val dialog = WrongAnswerDialog.newInstance()
        dialog.show(childFragmentManager, "wrong_answer_dialog")
    }

    private fun showTimeUpDialog() {
        val dialog = TimeUpDialog.newInstance()
        dialog.show(childFragmentManager, "time_up_dialog")
    }

    private fun showQuizResultDialog() {
        val state = viewModel.state.value
        val dialog = QuizResultDialog.newInstance(
            correctAnswers = state.correctAnswersCount,
            totalQuestions = state.totalQuestions,
            coins = args.coins
        )
        dialog.show(childFragmentManager, "quiz_result_dialog")

    }


    private fun showOneTimeWarningDialog() {
        val dialog = OneTimeWarningDialog.newInstance()
        dialog.setOnDismissCallback {
            viewModel.onEvent(QuizEvent.WarningDialogDismissed)
        }
        dialog.show(childFragmentManager, "one_time_warning_dialog")
    }
}
