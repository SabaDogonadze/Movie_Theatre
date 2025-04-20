package com.example.movietheatre.feature_movie_quiz.presentation.screen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movietheatre.R
import com.example.movietheatre.databinding.QuizAnswerViewholderBinding
import com.example.movietheatre.feature_movie_quiz.presentation.model.AnswerOptionPresenter


class QuizAnswerAdapter :
    ListAdapter<AnswerOptionPresenter, QuizAnswerAdapter.AnswerViewHolder>(AnswerDiffCallback()) {

    private var onItemClick: ((String) -> Unit)? = null
    private var selectedAnswerId: String? = null
    private var correctAnswerId: String? = null
    private var showResult = false

    fun setOnItemClickListener(listener: (String) -> Unit) {
        onItemClick = listener
    }

    fun updateSelection(selectedId: String?, correctId: String?, showResult: Boolean) {
        this.selectedAnswerId = selectedId
        this.correctAnswerId = correctId
        this.showResult = showResult
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnswerViewHolder {
        val binding = QuizAnswerViewholderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AnswerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AnswerViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class AnswerViewHolder(
        private val binding: QuizAnswerViewholderBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val pos = adapterPosition
                if (pos != RecyclerView.NO_POSITION && !showResult) {
                    val option = getItem(pos)
                    onItemClick?.invoke(option.id)
                }
            }
        }

        fun bind(option: AnswerOptionPresenter) {
            binding.tvAnswerOption.text = option.letter
            binding.tvAnswerText.text = option.text

            // Update visual state based on selection
            val isSelected = option.id == selectedAnswerId
            val isCorrect = option.id == correctAnswerId

            // Set background colors based on selection state
            when {
                showResult && isCorrect -> {
                    // Show correct answer
                    binding.root.setBackgroundResource(R.drawable.bg_correct_answer)
                }
                showResult && isSelected && !isCorrect -> {
                    // Show incorrect selection
                    binding.root.setBackgroundResource(R.drawable.bg_incorrect_answer)
                }
                isSelected -> {
                    // Just show selected state (before showing result)
                    binding.root.setBackgroundResource(R.drawable.bg_correct_answer)
                }
                else -> {
                    // Unselected items - no special background
                    binding.root.setBackgroundResource(R.drawable.bg_question_answer) // This removes any background resource
                }
            }
        }
    }
}

    private class AnswerDiffCallback : DiffUtil.ItemCallback<AnswerOptionPresenter>() {
        override fun areItemsTheSame(
            oldItem: AnswerOptionPresenter,
            newItem: AnswerOptionPresenter,
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: AnswerOptionPresenter,
            newItem: AnswerOptionPresenter,
        ): Boolean = oldItem == newItem
    }

