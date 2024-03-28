package com.jesse.xtts2.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jesse.xtts2.core.UiVictoryState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VictoryVM @Inject constructor() : ViewModel() {

    private var _uiStated = MutableLiveData<UiVictoryState>()
    val uiStated: LiveData<UiVictoryState> = _uiStated

    private val _listening = MutableLiveData<Boolean>(true)
    val listening: LiveData<Boolean> = _listening


    fun sendData(text: String) {
        Log.i("TAJ", " in vm sendData:  $text")
        val response = Server.getData(text)
        response.let {
            _uiStated.postValue(it)
            Log.d("TAJ", "cambio $it ")
        }
        Log.i("TAJ", "in vm getUi:  $response")
    }

    fun shouldListen(b: Boolean) {
        _listening.value = b
    }
}