package com.example.movietheatre.core.presentation.extension

fun Float.asMoneyFormat(): String {
    return "%.2f ₾".format(this)
}