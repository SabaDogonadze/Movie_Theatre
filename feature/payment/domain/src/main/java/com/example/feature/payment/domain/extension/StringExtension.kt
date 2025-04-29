package com.example.feature.payment.domain.extension


fun String.luhnCheck(): Boolean {
    var sum = 0
    val reverseDigits = this.reversed().map { it.toString().toInt() }
    reverseDigits.forEachIndexed { index, digit ->
        var calcDigit = digit
        if (index % 2 == 1) {
            calcDigit *= 2
            if (calcDigit > 9) {
                calcDigit -= 9
            }
        }
        sum += calcDigit
    }
    return (sum % 10 == 0)

}