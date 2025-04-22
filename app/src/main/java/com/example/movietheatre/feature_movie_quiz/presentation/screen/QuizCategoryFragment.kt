package com.example.movietheatre.feature_movie_quiz.presentation.screen

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movietheatre.core.presentation.BaseFragment
import com.example.movietheatre.core.presentation.extension.collectLatestFlow
import com.example.movietheatre.core.presentation.extension.showSnackBar
import com.example.movietheatre.databinding.FragmentQuizCategoryBinding
import com.example.movietheatre.feature_movie_quiz.presentation.event.QuizCategoryEvent
import com.example.movietheatre.feature_movie_quiz.presentation.event.QuizCategorySideEffect
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuizCategoryFragment :
    BaseFragment<FragmentQuizCategoryBinding>(FragmentQuizCategoryBinding::inflate) {

    private val viewModel: QuizCategoryViewModel by viewModels()
    private lateinit var categoryAdapter: QuizCategoryAdapter

    override fun setUp() {
        viewModel.event(QuizCategoryEvent.RefreshCompletedQuizzes)
        observeState()
        observeEvents()
        viewModel.event(QuizCategoryEvent.LoadCategories)
        setUpCategoryRecycler()
    }

    override fun clickListeners() {
        categoryAdapter.setOnCategoryClickListener { category ->
            viewModel.event(QuizCategoryEvent.SelectCategory(category.id))
        }
    }

    private fun setUpCategoryRecycler() {
        categoryAdapter = QuizCategoryAdapter()
        binding.recyclerCategories.apply {
            adapter = categoryAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun observeState() {
        collectLatestFlow(viewModel.state) { state ->
            with(binding) {
                progressBar.isVisible = state.isLoading
                recyclerCategories.isVisible = !state.isLoading
                categoryAdapter.submitList(state.categories)
            }
        }
    }

    private fun observeEvents() {
        collectLatestFlow(viewModel.uiEvents) { event ->
            when (event) {
                is QuizCategorySideEffect.NavigateToQuiz -> navigateToQuiz(
                    categoryId = event.categoryId,
                    coins = event.coins
                )

                is QuizCategorySideEffect.ShowError -> binding.root.showSnackBar(event.message)
            }
        }
    }

    private fun navigateToQuiz(categoryId: Int, coins: Int) {
        findNavController().navigate(
            QuizCategoryFragmentDirections.actionQuizCategoryFragmentToQuizFragment(
                categoryId = categoryId,
                coins = coins
            )
        )
    }
}