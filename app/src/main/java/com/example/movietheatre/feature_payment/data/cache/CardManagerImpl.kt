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
import com.example.movietheatre.feature_payment.domain.util.DatastoreError
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class CardManagerImpl @Inject constructor(@ApplicationContext context: Context) : CardManager {

    private val dataStore: DataStore<Card.CardsList> = DataStoreFactory.create(
        serializer = CardsListSerializer(),
        produceFile = { context.dataStoreFile("cards.pb") }
    )

    override suspend fun addCard(card: GetCard): Resource<Unit, DatastoreError> {
        return try {
            dataStore.updateData { currentCards ->
                val cardProto = Card.CardProto.newBuilder()
                    .setCardNumber(card.cardNumber)
                    .setCardHolderName(card.cardHolderName)
                    .setExpiryDate(card.expiryDate)
                    .setCvv(card.cvv)
                    .setCardType(card.cardType.name)
                    .build()

                currentCards.toBuilder().addCards(cardProto).build()
            }
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(DatastoreError.UNKNOWN)
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
}