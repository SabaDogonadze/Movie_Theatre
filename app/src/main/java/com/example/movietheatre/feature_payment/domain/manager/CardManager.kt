package com.example.movietheatre.feature_payment.domain.manager

import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.feature_payment.domain.model.GetCard
import com.example.movietheatre.feature_payment.domain.util.AddCardError
import com.example.movietheatre.feature_payment.domain.util.DatastoreError
import kotlinx.coroutines.flow.Flow

interface CardManager {
    suspend fun addCard(card: GetCard): Resource<Unit, AddCardError>
    suspend fun getCards(): Flow<Resource<List<GetCard>, DatastoreError>>
    suspend fun deleteCard(cardNumber: String): Resource<Unit, DatastoreError>
}