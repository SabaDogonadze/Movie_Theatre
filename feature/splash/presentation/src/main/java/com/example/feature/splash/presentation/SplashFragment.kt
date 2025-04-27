package com.example.feature.splash.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.core.presentation.extension.collectLatestFlow
import com.example.feature.splash.presentation.databinding.FragmentSplashBinding
import com.example.navigation.NavigationCommands
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay


@AndroidEntryPoint
class SplashFragment : Fragment() {

    private val viewmodel: SplashViewModel by viewModels()

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        collectLatestFlow(viewmodel.rememberMeFlow) { rememberMe ->
            delay(1500L)
            if (rememberMe && viewmodel.currentUser != null) {
                NavigationCommands.navigateToHomeGraph(findNavController())
            } else {
                NavigationCommands.navigateToLoginGraph(findNavController())
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}