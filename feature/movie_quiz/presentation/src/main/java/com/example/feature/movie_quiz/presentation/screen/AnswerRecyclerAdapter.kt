package com.example.feature.movie_quiz.presentation.screen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.feature.movie_quiz.presentation.model.AnswerOptionPresenter
import com.example.presentation.databinding.QuizAnswerViewholderBinding
import com.example.resource.R

class AnswerRecyclerAdapter :
    ListAdapter<AnswerOptionPresenter, AnswerRecyclerAdapter.AnswerViewHolder>(object :
        DiffUtil.ItemCallback<AnswerOptionPresenter>() {
        override fun areItemsTheSame(
            oldItem: AnswerOptionPresenter,
            newItem: AnswerOptionPresenter,
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: AnswerOptionPresenter,
            newItem: AnswerOptionPresenter,
        ): Boolean = oldItem == newItem
    }) {

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

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): AnswerRecyclerAdapter.AnswerViewHolder {
        val binding = QuizAnswerViewholderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AnswerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AnswerRecyclerAdapter.AnswerViewHolder, position: Int) {
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

            val isSelected = option.id == selectedAnswerId
            val isCorrect = option.id == correctAnswerId

            when {
                showResult && isCorrect -> {
                    binding.root.setBackgroundResource(R.drawable.bg_correct_answer)
                }

                showResult && isSelected && !isCorrect -> {
                    binding.root.setBackgroundResource(R.drawable.bg_incorrect_answer)
                }

                isSelected -> {
                    binding.root.setBackgroundResource(R.drawable.bg_correct_answer)
                }

                else -> {
                    binding.root.setBackgroundResource(R.drawable.bg_question_answer)
                }
            }
        }
    }
}

