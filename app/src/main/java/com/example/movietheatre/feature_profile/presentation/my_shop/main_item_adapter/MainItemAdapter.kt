package com.example.movietheatre.feature_profile.presentation.my_shop.main_item_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movietheatre.databinding.ViewholderMyShopMainItemBinding
import com.example.movietheatre.feature_profile.presentation.model.UserOrder
import com.example.movietheatre.feature_profile.presentation.my_shop.my_shop_card_adapter.MyShopCardAdapter

class MainItemAdapter :
    ListAdapter<UserOrder, MainItemAdapter.UserOrderViewHolder>(
        object : DiffUtil.ItemCallback<UserOrder>() {
            override fun areItemsTheSame(
                oldItem: UserOrder,
                newItem: UserOrder,
            ): Boolean {
                return oldItem.orderId == newItem.orderId
            }

            override fun areContentsTheSame(
                oldItem: UserOrder,
                newItem: UserOrder,
            ): Boolean {
                return oldItem == newItem
            }

        }
    ) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserOrderViewHolder {
        val binding =
            ViewholderMyShopMainItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return UserOrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserOrderViewHolder, position: Int) {
        holder.onBind()
    }

    inner class UserOrderViewHolder(val binding: ViewholderMyShopMainItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind() {
            val categoryProduct = getItem(adapterPosition)

            binding.txtTrackingCode.text = categoryProduct.trackingCode

            binding.txtDate.text = categoryProduct.createdAt

            val productAdapter = MyShopCardAdapter()

            binding.rvProducts.adapter = productAdapter

            productAdapter.submitList(categoryProduct.items)
        }
    }

}