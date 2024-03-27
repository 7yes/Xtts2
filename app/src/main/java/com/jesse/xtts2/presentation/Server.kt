package com.jesse.xtts2.presentation

import android.util.Log
import com.jesse.xtts2.core.Screen
import com.jesse.xtts2.core.UiVictoryState

object Server {
    private var isFree = true

    fun getData(text: String): UiVictoryState? {
        Log.i("TAJ", "getData in the server: $text ")
        return if (isFree) checkInput(text) else null
    }

    private fun checkInput(text: String): UiVictoryState? {
        isFree = false
        Log.i("TAJ", "checkInput: $text")
        val response = when (text) {
            "405 okay" -> UiVictoryState.MainMenu(Screen.A0,"Select a Task", listOf("Repack Pick","Cart Picking", "Break"))
            else -> null
        }
        isFree = true
        //TODO()
        return response
    }
}


