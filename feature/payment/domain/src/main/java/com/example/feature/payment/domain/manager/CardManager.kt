package com.example.feature.payment.domain.manager

import com.example.core.domain.util.Resource
import com.example.feature.payment.domain.model.GetCard
import com.example.feature.payment.domain.util.AddCardError
import com.example.feature.payment.domain.util.DatastoreError
import kotlinx.coroutines.flow.Flow

interface CardManager {
    suspend fun addCard(card: GetCard): Resource<Unit, AddCardError>
    suspend fun getCards(): Flow<Resource<List<GetCard>, DatastoreError>>
    suspend fun deleteCard(cardNumber: String): Resource<Unit, DatastoreError>
}