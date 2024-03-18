package com.jesse.xtts2.core

import android.speech.SpeechRecognizer
import android.util.Log

const val LOG_TAG = "tts"
const val PERMISSIONS_REQUEST_RECORD_AUDIO = 100
const val RESULTS_LIMIT = 1

var IS_CONTINUES_LISTEN = true

object Victory{
    private var grammar = listOf("605","okay")
    private var state :UiVictoryState = UiVictoryState.Login
    fun checkGrammar(text:String, screenNow: UiVictoryState):UiVictoryState{
       val data = text.split(" ")
        var screen = screenNow
        if(data.size==3)
        {
            if(data[0]== grammar[0] && data[1]== grammar[1])
                screen = UiVictoryState.MainMenu
        }
        return screen
    }
}

sealed class UiVictoryState{
    data object Login:UiVictoryState()
    data object MainMenu:UiVictoryState()
}
fun errorLog(msg: String?) {
    Log.e(LOG_TAG, msg!!)
}

fun getErrorText(errorCode: Int): String {
    val message: String = when (errorCode) {
        SpeechRecognizer.ERROR_AUDIO -> "Audio recording error"
        SpeechRecognizer.ERROR_CLIENT -> "Client side error"
        SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> "Insufficient permissions"
        SpeechRecognizer.ERROR_NETWORK -> "Network error"
        SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> "Network timeout"
        SpeechRecognizer.ERROR_NO_MATCH -> "No match"
        SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> "RecognitionService busy"
        SpeechRecognizer.ERROR_SERVER -> "error from server"
        SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> "No speech input"
        SpeechRecognizer.ERROR_LANGUAGE_NOT_SUPPORTED -> "Language Not supported"
        SpeechRecognizer.ERROR_LANGUAGE_UNAVAILABLE -> "Language Unavailable"
        else -> "Didn't understand, please try again."
    }
    return message
}