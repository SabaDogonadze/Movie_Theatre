package com.example.movietheatre.feature_payment.data.cache

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.example.movietheatre.Card
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

class CardsListSerializer : Serializer<Card.CardsList> {
    override val defaultValue: Card.CardsList = Card.CardsList.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): Card.CardsList {
        try {
            return Card.CardsList.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: Card.CardsList, output: OutputStream) = t.writeTo(output)
}