package com.example.feature.movie_detail.presentation.screen

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.feature.movie_detail.presentation.databinding.ViewholderScreeningDateBinding
import com.example.feature.movie_detail.presentation.model.ScreeningDateChooser
import com.example.resource.R

class ScreeningChooserAdapter(
    private val onClick: (ScreeningDateChooser) -> Unit,
) :
    ListAdapter<ScreeningDateChooser, ScreeningChooserAdapter.ScreeningDateViewHolder>(
        object : DiffUtil.ItemCallback<ScreeningDateChooser>() {
            override fun areItemsTheSame(
                oldItem: ScreeningDateChooser,
                newItem: ScreeningDateChooser,
            ): Boolean {
                return oldItem.number == newItem.number
            }

            override fun areContentsTheSame(
                oldItem: ScreeningDateChooser,
                newItem: ScreeningDateChooser,
            ): Boolean {
                return oldItem == newItem
            }

        }
    ) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScreeningDateViewHolder {
        val binding =
            ViewholderScreeningDateBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ScreeningDateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ScreeningDateViewHolder, position: Int) {
        holder.onBind()
    }

    inner class ScreeningDateViewHolder(private val binding: ViewholderScreeningDateBinding) :
        ViewHolder(binding.root) {
        fun onBind() {

            val screeningDate = getItem(adapterPosition)
            binding.apply {
                txtDateNumber.text = screeningDate.number.toString()
                txtDateName.text = screeningDate.title

                binding.root.setOnClickListener {
                    onClick(screeningDate)
                }

                if (screeningDate.isSelected) {
                    binding.cwMain.setCardBackgroundColor(
                        ContextCompat.getColor(
                            binding.root.context,
                            R.color.purple
                        )
                    )
                } else {
                    binding.cwMain.setCardBackgroundColor(Color.TRANSPARENT)
                }
            }
        }
    }
}