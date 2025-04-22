package com.example.movietheatre.core.presentation.mapper

import com.example.movietheatre.core.domain.model.GetCoin
import com.example.movietheatre.core.presentation.model.Coin


fun GetCoin.toPresentation() = Coin(userUid = this.userUid, coins = this.coins)