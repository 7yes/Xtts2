package com.jesse.xtts2.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VictoryVM @Inject constructor():ViewModel() {

    fun sendData(text:String){
        Log.i("TAJ", " in vm sendData:  $text")
       val response = Server.getData(text)
        Log.i("TAJ", "in vm getUi:  $response")
    }
}