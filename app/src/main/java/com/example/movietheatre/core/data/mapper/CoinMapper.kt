package com.example.movietheatre.core.data.mapper

import com.example.movietheatre.core.data.remote.response.CoinResponse
import com.example.movietheatre.core.domain.model.GetCoin


fun CoinResponse.toDomain(): GetCoin = GetCoin(userUid = this.userUid, coins = this.coins)