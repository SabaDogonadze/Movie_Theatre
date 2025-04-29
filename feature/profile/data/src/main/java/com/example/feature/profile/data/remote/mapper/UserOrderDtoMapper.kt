package com.example.feature.profile.data.remote.mapper

import com.example.feature.profile.data.remote.model.UserOrderDto
import com.example.feature.profile.data.remote.model.UserOrderItemsDto
import com.example.feature.profile.domain.model.GetUserOrder
import com.example.feature.profile.domain.model.GetUserOrderItems


fun List<UserOrderDto>.toDomain(): List<GetUserOrder> {
    return this.map {
        GetUserOrder(
            orderId = it.orderId,
            totalAmount = it.totalAmount,
            trackingCode = it.trackingCode,
            createdAt = it.createdAt,
            items = it.items.toItemsDomain()
        )
    }

}

fun List<UserOrderItemsDto>.toItemsDomain(): List<GetUserOrderItems> {

    return this.map {
        GetUserOrderItems(
            productId = it.productId,
            productName = it.productName,
            quantity = it.quantity,
            subtotal = it.subtotal,
            imgUrl = it.imgUrl
        )
    }
}


