package com.example.movietheatre.core.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

typealias Inflater<VB> = (LayoutInflater,ViewGroup?,Boolean) -> VB
abstract class BaseFragment<VB: ViewBinding>(private var inflate: Inflater<VB>) : Fragment() {
    private var _binding:VB? = null
    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp()
        clickListeners()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    abstract fun setUp()
    abstract fun clickListeners()
}