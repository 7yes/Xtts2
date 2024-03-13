package com.jesse.xtts2.presentation.login

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginVM @Inject constructor(): ViewModel() {

    fun login(input:String):Boolean{
        return input.toInt()==605
    }
}