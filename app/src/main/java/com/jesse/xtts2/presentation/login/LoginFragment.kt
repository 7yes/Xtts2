package com.jesse.xtts2.presentation.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.jesse.xtts2.databinding.FragmentLoginBinding
import com.jesse.xtts2.presentation.VictoryVM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewmodel:VictoryVM by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        binding.etLogin.setOnEditorActionListener { input, actionId, event ->
            Log.d("TAJ", "onCreateView: from  btn: ${input.text}")
           viewmodel.sendData("${input.text} okay")
           // Victory.updateState(Server.getData("${input.text} okay"))
            false
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}