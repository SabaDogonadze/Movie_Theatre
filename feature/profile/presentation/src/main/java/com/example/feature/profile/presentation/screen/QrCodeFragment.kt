package com.example.feature.profile.presentation.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.feature.profile.presentation.databinding.FragmentQrCodeBinding


class QrCodeFragment : Fragment() {

    private var _binding: FragmentQrCodeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentQrCodeBinding.inflate(inflater, container, false)
        return binding.root
    }

      override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
          super.onViewCreated(view, savedInstanceState)

          binding.btnArrowBack.setOnClickListener {
              findNavController().navigateUp()
          }
      }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}