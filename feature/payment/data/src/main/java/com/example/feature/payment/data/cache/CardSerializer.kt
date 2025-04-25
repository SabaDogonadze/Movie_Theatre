package com.example.feature.payment.data.cache

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.example.feature.payment.CardsList
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream


class CardsListSerializer : Serializer<CardsList> {
    override val defaultValue: CardsList = CardsList.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): CardsList {
        try {
            return CardsList.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: CardsList, output: OutputStream) = t.writeTo(output)
}