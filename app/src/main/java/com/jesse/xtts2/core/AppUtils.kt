package com.jesse.xtts2.core

import android.speech.SpeechRecognizer
import android.util.Log

const val LOG_TAG = "tts"
const val PERMISSIONS_REQUEST_RECORD_AUDIO = 100
const val RESULTS_LIMIT = 1

var IS_CONTINUES_LISTEN = true

object Victory {
    private var currentTemplate: Screen = Screen.A0
     var currentTitle  = "Title"
     var currentOptions= listOf("","")
     private var currentGrammar= listOf("","")

    fun updateState(newUiState: UiVictoryState) {
     when(newUiState){
         UiVictoryState.Login -> TODO()
         is UiVictoryState.TemplateA0 -> {
             currentTitle = newUiState.title
             currentTemplate = newUiState.template
             currentOptions = newUiState.options
             currentGrammar = newUiState.options
         }
     }

    }

    fun checkGrammar(text: String): Boolean {
        Log.d("TAJ", "get in checkGrammar: $text")
        val words = text.split(" ")
        val countWords = words.size - 1
        return when (countWords) {
            1 -> {
                Log.e("TAJ", "checkGrammar:  fue 1 $text", )
                true
            }

            2 -> {
                Log.d("TAJ", "checkGrammar:  fue 2 ${words[0]} + ${words[1]}")
                (words[1] == "okay")
            }

            else -> false
        }
    }
}

enum class Screen { A0, B0, C0, LOGIN }
sealed class UiVictoryState {
    data object Login : UiVictoryState()
    data class TemplateA0(val template: Screen, val title: String, val options: List<String>) :
        UiVictoryState()
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