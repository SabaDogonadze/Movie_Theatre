package com.example.movietheatre.feature_movie_quiz.presentation.screen

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.movietheatre.R
import com.example.movietheatre.databinding.QuizCategoryItemViewholderBinding
import com.example.movietheatre.feature_movie_quiz.presentation.model.QuizCategoryPresenter

class QuizCategoryAdapter :
    ListAdapter<QuizCategoryPresenter, QuizCategoryAdapter.CategoryViewHolder>(DIFF_CALLBACK) {

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
                    onCategoryClickListener?.invoke(getItem(pos))
                }
            }
        }

        fun bind(category: QuizCategoryPresenter) {
            binding.apply {
                // Texts
                tvCategoryTitle.text = category.title
                tvReward.text = "${category.rewardCoins} coins"

                // Image
                Glide.with(itemView)
                    .load(category.imageUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_launcher_foreground)
                    .centerCrop()
                    .into(ivCategoryImage)

                if (category.isCompleted) {
                    // --- COMPLETED: BLUR (API 31+) OR DIM (older) ---
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        // Apply RenderEffect blur to entire card
                        val radius = 20f
                        val blurEffect = RenderEffect.createBlurEffect(
                            radius, radius, Shader.TileMode.CLAMP
                        )
                        root.setRenderEffect(blurEffect)
                    } else {
                        // Fallback: semi‐transparent overlay + reduced alpha
                        root.alpha = 0.7f
                        lightOverlay.setBackgroundColor(
                            ContextCompat.getColor(itemView.context, R.color.completed_quiz_overlay)
                        )
                    }
                    // Title color for completed
                    tvCategoryTitle.setTextColor(
                        ContextCompat.getColor(itemView.context, R.color.completed_quiz_text)
                    )
                } else {
                    // --- AVAILABLE: CLEAR RENDER + DEFAULT STYLING ---
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

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<QuizCategoryPresenter>() {
            override fun areItemsTheSame(
                oldItem: QuizCategoryPresenter,
                newItem: QuizCategoryPresenter,
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: QuizCategoryPresenter,
                newItem: QuizCategoryPresenter,
            ): Boolean = oldItem == newItem
        }
    }
}
