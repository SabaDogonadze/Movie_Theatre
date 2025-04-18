package com.example.movietheatre.feature_shop.presentation.screen.category_product_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movietheatre.databinding.ViewholderShopItemTitleBinding
import com.example.movietheatre.feature_shop.presentation.model.CategoryProducts
import com.example.movietheatre.feature_shop.presentation.model.Product
import com.example.movietheatre.feature_shop.presentation.screen.product_adapter.ProductAdapter

class CategoryProductAdapter(
    private val onAdd: (Product) -> Unit,
    private val onRemove: (Product) -> Unit,
) :
    ListAdapter<CategoryProducts, CategoryProductAdapter.CategoryViewHolder>(
        object : DiffUtil.ItemCallback<CategoryProducts>() {
            override fun areItemsTheSame(
                oldItem: CategoryProducts,
                newItem: CategoryProducts,
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: CategoryProducts,
                newItem: CategoryProducts,
            ): Boolean {
                return oldItem == newItem
            }

        }
    ) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding =
            ViewholderShopItemTitleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.onBind()
    }

    inner class CategoryViewHolder(val binding: ViewholderShopItemTitleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind() {
            val categoryProduct = getItem(adapterPosition)

            binding.txtCategoryTitle.text = categoryProduct.category

            val productAdapter = ProductAdapter(onAdd, onRemove)

            binding.rvProducts.adapter = productAdapter

            productAdapter.submitList(categoryProduct.products)
        }
    }

}