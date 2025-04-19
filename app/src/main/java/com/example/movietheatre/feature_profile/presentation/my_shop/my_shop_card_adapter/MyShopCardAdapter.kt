package com.example.movietheatre.feature_profile.presentation.my_shop.my_shop_card_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movietheatre.core.presentation.extension.asMoneyFormat
import com.example.movietheatre.core.presentation.extension.loadImg
import com.example.movietheatre.databinding.ViewholderMyShopCardBinding
import com.example.movietheatre.feature_profile.presentation.model.UserOrderItems

class MyShopCardAdapter :
    ListAdapter<UserOrderItems, MyShopCardAdapter.UserOrderViewHolder>(
        object : DiffUtil.ItemCallback<UserOrderItems>() {
            override fun areItemsTheSame(
                oldItem: UserOrderItems,
                newItem: UserOrderItems,
            ): Boolean {
                return oldItem.productId == newItem.productId
            }

            override fun areContentsTheSame(
                oldItem: UserOrderItems,
                newItem: UserOrderItems,
            ): Boolean {
                return oldItem == newItem
            }

        }
    ) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserOrderViewHolder {
        val binding =
            ViewholderMyShopCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserOrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserOrderViewHolder, position: Int) {
        holder.onBind()
    }

    inner class UserOrderViewHolder(val binding: ViewholderMyShopCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind() {

            val product = getItem(adapterPosition)
            binding.apply {
                imgProduct.loadImg(product.imgUrl)

                txtProductName.text =
                    product.productName + if (product.quantity > 1) "  ${product.quantity}X" else ""
                txtProductPrice.text = product.subtotal.toFloat().asMoneyFormat()

            }
        }
    }
}