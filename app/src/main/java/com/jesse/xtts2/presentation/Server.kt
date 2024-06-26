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
        Log.d("TAJ", "checkInput: X$text,XX")
        val response = when (text.trim()) {
            "405 okay" -> {
                UiVictoryState.TemplateA0(Screen.A0,"Select a Task", listOf("Repack Pick","Cart Picking","Shubba","pizza","apple","whatever","lalssagna"))
            }
            else -> null
        }
        isFree = true
        //TODO()
        Log.i("TAJ", "response send it in server: $response ", )
        return response
    }
}


