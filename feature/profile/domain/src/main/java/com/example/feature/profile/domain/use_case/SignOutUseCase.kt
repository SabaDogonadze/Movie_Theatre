package com.example.feature.profile.domain.use_case

import com.example.core.domain.util.Resource
import com.example.feature.profile.domain.repository.SignOutRepository
import com.example.feature.profile.domain.util.LogOutError
import javax.inject.Inject

class SignOutUseCase @Inject constructor(private val signOutRepository: SignOutRepository) {

    suspend operator fun invoke(): Resource<Unit, LogOutError> {
        return signOutRepository.signOut()
    }
}