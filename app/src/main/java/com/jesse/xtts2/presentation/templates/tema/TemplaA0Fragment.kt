package com.jesse.xtts2.presentation.templates.tema

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.jesse.xtts2.core.Victory
import com.jesse.xtts2.databinding.FragmentTemplaA0Binding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TemplaA0Fragment : Fragment() {
    private var _binding: FragmentTemplaA0Binding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: TemAAdapter
    private var listAdapter = mutableListOf<String>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTemplaA0Binding.inflate(layoutInflater, container, false)
        initRV()

        return binding.root
    }

    private fun initRV() {
        adapter = TemAAdapter(listAdapter){
            Toast.makeText(requireContext(), "$it", Toast.LENGTH_SHORT).show()
        }
        binding.rvTemA.layoutManager = GridLayoutManager(requireContext(),2)
        binding.rvTemA.adapter = adapter

        listAdapter.clear()
        listAdapter.addAll(Victory.currentOptions)
        adapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}