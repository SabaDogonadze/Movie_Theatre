package com.example.movietheatre.feature_login.presentation

import com.example.movietheatre.core.presentation.BaseFragment
import com.example.movietheatre.databinding.FragmentLoginBinding
import com.example.movietheatre.feauture_home.presentation.Dato


class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {
    override fun setUp() {
        println(Dato.name)
    }

    override fun clickListeners() {
        TODO("Not yet implemented")
    }

}