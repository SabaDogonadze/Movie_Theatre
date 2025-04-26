package com.example.feature.movie_quiz.presentation.screen

import android.annotation.SuppressLint
import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.core.presentation.extension.loadImg
import com.example.feature.movie_quiz.presentation.databinding.QuizCategoryItemViewholderBinding
import com.example.feature.movie_quiz.presentation.model.QuizCategoryPresenter
import com.example.resource.R

class QuizCategoryAdapter :
    ListAdapter<QuizCategoryPresenter, QuizCategoryAdapter.CategoryViewHolder>(object :
        DiffUtil.ItemCallback<QuizCategoryPresenter>() {
        override fun areItemsTheSame(
            oldItem: QuizCategoryPresenter,
            newItem: QuizCategoryPresenter,
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: QuizCategoryPresenter,
            newItem: QuizCategoryPresenter,
        ): Boolean {
            return oldItem == newItem
        }

    }
    ) {

    private var onCategoryClickListener: ((QuizCategoryPresenter) -> Unit)? = null

    fun setOnCategoryClickListener(listener: (QuizCategoryPresenter) -> Unit) {
        onCategoryClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = QuizCategoryItemViewholderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CategoryViewHolder(
        private val binding: QuizCategoryItemViewholderBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val pos = adapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    val item = getItem(pos)
                    if (!item.isCompleted) {
                        onCategoryClickListener?.invoke(item)
                    }
                }
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(category: QuizCategoryPresenter) {
            binding.apply {
                tvCategoryTitle.text = category.title
                tvReward.text = "${category.rewardCoins} coins"

                ivCategoryImage.loadImg(category.imageUrl)

                if (category.isCompleted) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        val radius = 20f
                        val blurEffect = RenderEffect.createBlurEffect(
                            radius, radius, Shader.TileMode.CLAMP
                        )
                        root.setRenderEffect(blurEffect)
                    } else {
                        root.alpha = 0.7f
                        lightOverlay.setBackgroundColor(
                            ContextCompat.getColor(itemView.context, R.color.completed_quiz_overlay)
                        )
                    }
                    tvCategoryTitle.setTextColor(
                        ContextCompat.getColor(itemView.context, R.color.completed_quiz_text)
                    )
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        root.setRenderEffect(null)
                    }
                    root.alpha = 1f
                    lightOverlay.background = ContextCompat.getDrawable(
                        itemView.context, R.drawable.light_gradient
                    )
                    tvCategoryTitle.setTextColor(
                        ContextCompat.getColor(itemView.context, R.color.available_quiz_text)
                    )
                }
            }
        }
    }
}


