package com.example.core.presentation.mapper

import com.example.core.domain.model.GetCoin
import com.example.core.presentation.model.Coin


fun GetCoin.toPresentation() = Coin(userUid = this.userUid, coins = this.coins)