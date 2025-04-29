package com.example.feature.profile.presentation.mapper

import com.example.feature.profile.domain.model.GetUserOrder
import com.example.feature.profile.domain.model.GetUserOrderItems
import com.example.feature.profile.presentation.model.UserOrder
import com.example.feature.profile.presentation.model.UserOrderItems
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun List<GetUserOrder>.toPresentation(): List<UserOrder> {


    return this.map {

        val dateTime = LocalDateTime.parse(it.createdAt)

        val formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm")
        val formattedDateTime = dateTime.format(formatter)

        UserOrder(
            orderId = it.orderId,
            totalAmount = it.totalAmount,
            trackingCode = it.trackingCode,
            createdAt = formattedDateTime,
            items = it.items.toItemsPresentation()
        )
    }

}

fun List<GetUserOrderItems>.toItemsPresentation(): List<UserOrderItems> {

    return this.map {
        UserOrderItems(
            productId = it.productId,
            productName = it.productName,
            quantity = it.quantity,
            subtotal = it.subtotal,
            imgUrl = it.imgUrl
        )
    }
}
