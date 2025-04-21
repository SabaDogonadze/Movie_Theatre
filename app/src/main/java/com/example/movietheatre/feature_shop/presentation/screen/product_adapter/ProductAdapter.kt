package com.example.movietheatre.feature_shop.presentation.screen.product_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movietheatre.core.presentation.extension.asMoneyFormat
import com.example.movietheatre.core.presentation.extension.loadImg
import com.example.movietheatre.databinding.ViewholderShopCardBinding
import com.example.movietheatre.feature_shop.presentation.model.Product

class ProductAdapter(
    private val onAdd: (Product) -> Unit,
    private val onRemove: (Product) -> Unit,
) :
    ListAdapter<Product, ProductAdapter.ProductViewHolder>(
        object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem == newItem
            }

        }
    ) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding =
            ViewholderShopCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.onBind()
    }

    inner class ProductViewHolder(val binding: ViewholderShopCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind() {

            val product = getItem(adapterPosition)
            binding.apply {
                imgProduct.loadImg(product.imageUrl)

                txtProductName.text = product.name
                txtProductPrice.text = product.price.toFloat().asMoneyFormat()
                txtProductDescription.text = product.description
                btnAddToCartButton.setOnClickListener {
                    onAdd(product)
                }
                btnRemoveToCartButton.setOnClickListener {
                    onRemove(product)
                }
            }
        }
    }
}