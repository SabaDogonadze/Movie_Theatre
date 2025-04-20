package com.example.movietheatre.feature_movie_quiz.presentation.screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movietheatre.R
import com.example.movietheatre.core.presentation.BaseFragment
import com.example.movietheatre.core.presentation.extension.collectLatestFlow
import com.example.movietheatre.databinding.FragmentQuizCategoryBinding
import com.example.movietheatre.feature_movie_quiz.presentation.event.QuizCategoryEvent
import com.example.movietheatre.feature_movie_quiz.presentation.event.QuizCategorySideEffect
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuizCategoryFragment :
    BaseFragment<FragmentQuizCategoryBinding>(FragmentQuizCategoryBinding::inflate) {

    private val viewModel: QuizCategoryViewModel by viewModels()
    private val categoryAdapter = QuizCategoryAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp()
        clickListeners()
    }

    override fun setUp() {
        // Initialize RecyclerView with blur-enabled adapter
        binding.recyclerCategories.apply {
            adapter = categoryAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
            setHasFixedSize(true)
        }
        observeState()
        observeEvents()
    }

    override fun clickListeners() {
        // Let the adapter handle clicks, but pass the event to the ViewModel
        categoryAdapter.setOnCategoryClickListener { category ->
            //viewModel.event(QuizCategoryEvent.SelectCategory(category.id))
            findNavController().navigate(QuizCategoryFragmentDirections.actionQuizCategoryFragmentToQuizFragment(category.id))
        }
    }

    private fun observeState() {
        collectLatestFlow(viewModel.state) { state ->
            with(binding) {
                // Show loading spinner when loading
                progressBar.visibility =
                    if (state.isLoading) View.VISIBLE else View.GONE

                // Only toggle RecyclerView and error layout
                recyclerCategories.visibility =
                    if (!state.isLoading && state.error == null) View.VISIBLE
                    else View.GONE

                // Submit updated list (with blur flags) whenever state changes
                categoryAdapter.submitList(state.categories)
            }
        }
    }

    private fun observeEvents() {
        collectLatestFlow(viewModel.uiEvents) { event ->
            when (event) {
                is QuizCategorySideEffect.NavigateToQuiz ->
                    navigateToQuiz(event.categoryId)
                is QuizCategorySideEffect.ShowError ->
                    showErrorSnackbar(event.message)
            }
        }
    }

    private fun navigateToQuiz(categoryId: Int) {
        findNavController().navigate(QuizCategoryFragmentDirections.actionQuizCategoryFragmentToQuizFragment(categoryId))
    }

    private fun showErrorSnackbar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    override fun onResume() {
        super.onResume()
        // Refresh completed quizzes when returning to this screen
        viewModel.event(QuizCategoryEvent.RefreshCompletedQuizzes)
    }
}