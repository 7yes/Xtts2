package com.jesse.xtts2.presentation.templates

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jesse.xtts2.databinding.FragmentTemplaA0Binding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TemplaA0Fragment : Fragment() {
    private var _binding: FragmentTemplaA0Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTemplaA0Binding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}