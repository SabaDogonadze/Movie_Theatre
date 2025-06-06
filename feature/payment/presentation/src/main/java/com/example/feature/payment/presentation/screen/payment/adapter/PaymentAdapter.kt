package com.example.feature.payment.presentation.screen.payment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.feature.payment.presentation.databinding.ItemCardBinding
import com.example.feature.payment.presentation.model.Card
import com.example.core.presentation.R

object GetCardDiffUtil : DiffUtil.ItemCallback<Card>() {
    override fun areItemsTheSame(oldItem: Card, newItem: Card): Boolean {
        return oldItem.cardNumber == newItem.cardNumber
    }

    override fun areContentsTheSame(oldItem: Card, newItem: Card): Boolean {
        return oldItem == newItem
    }
}

class PaymentPagerAdapter(val onClick: (String) -> Unit) :
    ListAdapter<Card, PaymentPagerAdapter.CardViewHolder>(GetCardDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCardBinding.inflate(inflater, parent, false)
        return CardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind()
    }


    inner class CardViewHolder(private val binding: ItemCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            val card = getItem(adapterPosition)

            val maskedNumber = card.maskedNumber
            binding.txtCardNumberDisplay.text = maskedNumber
            binding.txtCardHolderNameDisplay.text = card.cardHolderName
            binding.txtCardDateDisplay.text = card.expiryDate

            binding.imgDelete.setOnClickListener {
                onClick(card.cardNumber)
            }

            val img =
                if (card.cardType == com.example.feature.payment.domain.model.CardType.MASTERCARD) R.drawable.ic_master_card else R.drawable.ic_visa_card
            binding.imgCard.setImageResource(img)
        }
    }

}