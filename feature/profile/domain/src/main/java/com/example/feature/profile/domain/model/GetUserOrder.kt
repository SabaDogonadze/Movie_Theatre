package com.example.feature.profile.domain.model


data class GetUserOrder(
    val orderId: Int,
    val trackingCode: String,
    val totalAmount: Double,
    val items: List<GetUserOrderItems>,
    val createdAt: String,
)


data class GetUserOrderItems(
    val productId: Int,
    val productName: String,
    val quantity: Int,
    val subtotal: Double,
    val imgUrl:String,

    )
