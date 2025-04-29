package com.example.feature.register.presentation.screen

sealed interface RegisterEvent {
    data class Register(val email: String, val password: String):
        RegisterEvent
    data class ValidateEmail(val email: String) : RegisterEvent
    data class ValidatePassword(val password: String) : RegisterEvent
    data class ValidateRepeatedPassword(val password: String, val repeatedPassword: String) :
        RegisterEvent
}