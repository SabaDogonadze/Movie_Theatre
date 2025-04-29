package com.example.core.data.mapper

import com.example.core.data.remote.response.CoinResponse
import com.example.core.domain.model.GetCoin


fun CoinResponse.toDomain(): GetCoin = GetCoin(userUid = this.userUid, coins = this.coins)