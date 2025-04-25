package com.example.feature.payment.data.cache

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.example.core.domain.util.Resource
import com.example.feature.payment.Card
import com.example.feature.payment.CardsList
import com.example.feature.payment.domain.manager.CardManager
import com.example.feature.payment.domain.model.CardType
import com.example.feature.payment.domain.model.GetCard
import com.example.feature.payment.domain.util.AddCardError
import com.example.feature.payment.domain.util.DatastoreError
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CardManagerImpl @Inject constructor(@ApplicationContext context: Context) : CardManager {

    private val dataStore: DataStore<CardsList> = DataStoreFactory.create(
        serializer = CardsListSerializer(),
        produceFile = { context.dataStoreFile("cards.pb") }
    )

    override suspend fun addCard(card: GetCard): Resource<Unit, AddCardError> {
        return try {
            val currentCards = dataStore.data.first()
            if (currentCards.cardsList.any { it.cardNumber == card.cardNumber }) {
                return Resource.Error(AddCardError.ALREADY_EXISTS)
            }

            dataStore.updateData { curCards ->
                val cardProto = Card.newBuilder()
                    .setCardNumber(card.cardNumber)
                    .setCardHolderName(card.cardHolderName)
                    .setExpiryDate(card.expiryDate)
                    .setCvv(card.cvv)
                    .setCardType(card.cardType.name)
                    .build()

                curCards.toBuilder().addCards(cardProto).build()
            }
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(AddCardError.UNKNOWN)
        }
    }

    override suspend fun getCards(): Flow<Resource<List<GetCard>, DatastoreError>> {
        return dataStore.data
            .map { cardsListProto ->
                val cards = cardsListProto.cardsList.map { cardProto ->
                    GetCard(
                        cardNumber = cardProto.cardNumber,
                        cardHolderName = cardProto.cardHolderName,
                        expiryDate = cardProto.expiryDate,
                        cvv = cardProto.cvv,
                        cardType = CardType.valueOf(cardProto.cardType)
                    )
                }
                Resource.Success<List<GetCard>, DatastoreError>(cards) as Resource<List<GetCard>, DatastoreError>
            }
            .catch { exception ->
                emit(Resource.Error<List<GetCard>, DatastoreError>(DatastoreError.UNKNOWN) as Resource<List<GetCard>, DatastoreError>)
            }
    }

    override suspend fun deleteCard(cardNumber: String): Resource<Unit, DatastoreError> {
        return try {
            dataStore.updateData { currentCards ->
                val updatedCards = currentCards.cardsList.filterNot { it.cardNumber == cardNumber }
                currentCards.toBuilder()
                    .clearCards()
                    .addAllCards(updatedCards)
                    .build()
            }
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(DatastoreError.UNKNOWN)
        }
    }
}