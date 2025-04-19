package com.example.movietheatre.feature_profile.domain.use_case

import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.core.domain.util.error.NetworkError
import com.example.movietheatre.feature_profile.domain.model.GetUserOrder
import com.example.movietheatre.feature_profile.domain.repository.UserOrderRepository
import javax.inject.Inject

class GetUserOrderUseCase@Inject constructor(
    private val userOrderRepository: UserOrderRepository
) {

    suspend operator fun invoke():Resource<List<GetUserOrder>,NetworkError>{
        return userOrderRepository.getUserOrder()
    }
}