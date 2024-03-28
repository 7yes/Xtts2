package com.jesse.xtts2.presentation

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.AudioManager
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jesse.xtts2.R
import com.jesse.xtts2.core.IS_CONTINUES_LISTEN
import com.jesse.xtts2.core.PERMISSIONS_REQUEST_RECORD_AUDIO
import com.jesse.xtts2.core.RESULTS_LIMIT
import com.jesse.xtts2.core.UiVictoryState
import com.jesse.xtts2.core.Victory
import com.jesse.xtts2.core.errorLog
import com.jesse.xtts2.core.getErrorText
import com.jesse.xtts2.databinding.ActivityMainBinding
import com.jesse.xtts2.presentation.templates.tema.TemplaA0Fragment
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var speechRecognizer: SpeechRecognizer? = null
    private var recognizerIntent: Intent? = null
    private var selectedLanguage = "en" // Default "en selected"
    private lateinit var audioManager: AudioManager
    private val viewmodel: VictoryVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setListeners()
        checkPermissions()
        resetSpeechRecognizer()
        setRecogniserIntent()
        prepareLocales()
        // binding.btnStart.text = binding.btnStart.textOn
        audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, 0, 0)
        viewmodel.uiStated.observe(this) {
            navigateTo(it)
        }
    }

    private fun navigateTo(newUiState: UiVictoryState?) {
        if (newUiState != null) stopListener()
        Log.d("TAJ", "onCreate navigateTos0:  aqui $newUiState", )
        when (newUiState) {
            UiVictoryState.Login -> {
                Log.d("TAJ", "navigateTos1:  Login:", )
            }

            is UiVictoryState.TemplateA0 -> {

                Log.d("TAJ", "navigateTos2:  TemplateA0:", )
                Victory.updateState(newUiState)
                binding.title.text = Victory.currentTitle
               supportFragmentManager.beginTransaction().add(R.id.bodyContainer, TemplaA0Fragment()).commitNow()
            }

            null -> { Log.e("TAG", "navigateTos3:  null:", )}
        }
    }

    private fun setListeners() {
        binding.btnStart.setOnClickListener {
            when (binding.btnStart.text) {
                "ON" -> startListening()
                else -> {
                    resetSpeechRecognizer()
                }
            }
        }
    }

    private fun checkPermissions() {
        val permissionCheck =
            ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.RECORD_AUDIO)
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this@MainActivity,
                arrayOf(Manifest.permission.RECORD_AUDIO),
                PERMISSIONS_REQUEST_RECORD_AUDIO
            )
            return
        }
    }

    private fun resetSpeechRecognizer() {
        if (speechRecognizer != null) speechRecognizer!!.destroy()
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)
        errorLog(
            "isRecognitionAvailable: " + SpeechRecognizer.isRecognitionAvailable(this)
        )
        if (SpeechRecognizer.isRecognitionAvailable(this))
            speechRecognizer!!.setRecognitionListener(mRecognitionListener)
        else finish()
    }

    private fun setRecogniserIntent() {
        recognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
//        recognizerIntent!!.putExtra(
//            RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE,
//            selectedLanguage
//        )
        recognizerIntent!!.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE,
            selectedLanguage
        )
        recognizerIntent!!.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        recognizerIntent!!.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, RESULTS_LIMIT)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSIONS_REQUEST_RECORD_AUDIO) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startListening()
            } else {
                Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun startListening() {
        speechRecognizer!!.startListening(recognizerIntent)
        binding.progressBar1.visibility = View.VISIBLE
    }

    public override fun onResume() {
        errorLog("resume")
        super.onResume()
        resetSpeechRecognizer()
        if (IS_CONTINUES_LISTEN) {
            startListening()
        }
    }

    override fun onPause() {
        errorLog("pause")
        super.onPause()
        speechRecognizer!!.stopListening()
    }

    override fun onStop() {
        errorLog("stop")
        super.onStop()
        stopListener()
    }

    private fun stopListener() {
        if (speechRecognizer != null) {
            speechRecognizer!!.destroy()
        }
    }

    private fun prepareLocales() {
        val availableLocales =
            Locale.getAvailableLocales()
                .filter { it.toString().equals("en") || it.toString().equals("es") }
        //Alternatively you can check https://cloud.google.com/speech-to-text/docs/speech-to-text-supported-languages

        val adapterLocalization: ArrayAdapter<Any?> = ArrayAdapter<Any?>(
            this,
            android.R.layout.simple_spinner_item,
            availableLocales
        )
        adapterLocalization.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedLanguage = availableLocales[position].toString()
                resetSpeechRecognizer()
                setRecogniserIntent()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
//                TODO("Not yet implemented")
            }
        }

        binding.spinner1.adapter = adapterLocalization
        binding.spinner1.setSelection(0) // language set to "en"
    }

    private val mRecognitionListener = object : RecognitionListener {
        override fun onBeginningOfSpeech() {
            errorLog("onBeginningOfSpeech")
            binding.progressBar1.isIndeterminate = false
            binding.progressBar1.max = 10
        }

        override fun onBufferReceived(buffer: ByteArray) {
            errorLog("onBufferReceived: $buffer")
        }

        override fun onEndOfSpeech() {
            errorLog("onEndOfSpeech")
            binding.progressBar1.isIndeterminate = true
            speechRecognizer!!.stopListening()
        }

        override fun onResults(results: Bundle) {
            errorLog("onResults")
            val matches: ArrayList<String>? = results
                .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
            var text = ""
            for (result in matches!!) text += """
     $result 
     """.trimIndent()
            binding.textView1.text = text
            Log.d("TAJ", "onResults: text $text ")
            val isValidInput = Victory.checkGrammar(text)
            if (isValidInput) {
                viewmodel.sendData(text)
            }

            if (IS_CONTINUES_LISTEN) {
                startListening()
            } else {
                binding.progressBar1.visibility = View.GONE
            }
        }

        override fun onError(errorCode: Int) {
            val errorMessage = getErrorText(errorCode)
            errorLog("FAILED $errorMessage")
            binding.tvError.text = errorMessage

            // rest voice recogniser
            resetSpeechRecognizer()
            startListening()
        }

        override fun onEvent(arg0: Int, arg1: Bundle) {
            errorLog("onEvent")
        }

        override fun onPartialResults(arg0: Bundle) {
            errorLog("onPartialResults")
        }

        override fun onReadyForSpeech(arg0: Bundle) {
            errorLog("onReadyForSpeech")
        }

        override fun onRmsChanged(rmsdB: Float) {
            binding.progressBar1.progress = rmsdB.toInt()
        }
    }

}