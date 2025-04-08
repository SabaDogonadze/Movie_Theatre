package com.example.movietheatre.feature_payment.data.cache

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.example.movietheatre.Card
import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.feature_payment.domain.manager.CardManager
import com.example.movietheatre.feature_payment.domain.model.CardType
import com.example.movietheatre.feature_payment.domain.model.GetCard
import com.example.movietheatre.feature_payment.domain.util.AddCardError
import com.example.movietheatre.feature_payment.domain.util.DatastoreError
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class CardManagerImpl @Inject constructor(@ApplicationContext context: Context) : CardManager {

    private val dataStore: DataStore<Card.CardsList> = DataStoreFactory.create(
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
                val cardProto = Card.CardProto.newBuilder()
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

    override suspend fun getCards(): Resource<List<GetCard>, DatastoreError> {
        return try {
            val cardsList = dataStore.data.first()
            val cards = cardsList.cardsList.map { proto ->
                GetCard(
                    cardNumber = proto.cardNumber,
                    cardHolderName = proto.cardHolderName,
                    expiryDate = proto.expiryDate,
                    cvv = proto.cvv,
                    cardType = CardType.valueOf(proto.cardType)
                )
            }
            Resource.Success(cards)
        } catch (e: Exception) {
            Resource.Error(DatastoreError.UNKNOWN)
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