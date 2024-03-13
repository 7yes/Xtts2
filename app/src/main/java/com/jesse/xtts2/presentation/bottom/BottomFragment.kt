package com.jesse.xtts2.presentation.bottom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jesse.xtts2.databinding.FragmentBottomBinding

class BottomFragment : Fragment() {
    private var _binding: FragmentBottomBinding? = null
       private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomBinding.inflate(layoutInflater, container, false)
        return binding.root  }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}