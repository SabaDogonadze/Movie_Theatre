package com.example.feature.profile.domain.use_case

import com.example.core.domain.util.Resource
import com.example.core.domain.util.error.NetworkError
import com.example.feature.profile.domain.model.GetUserOrder
import com.example.feature.profile.domain.repository.UserOrderRepository
import javax.inject.Inject

class GetUserOrderUseCase @Inject constructor(
    private val userOrderRepository: UserOrderRepository,
) {

    suspend operator fun invoke(): Resource<List<GetUserOrder>, NetworkError> {
        return userOrderRepository.getUserOrder()
    }
}