package com.example.klit

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val poemTextView = findViewById<TextView>(R.id.poem)
        poemTextView.text = loadPoem()

        GlobalScope.launch {
            updateTranslation()
        }
    }

    suspend fun updateTranslation() = coroutineScope {
        launch {
            val translationTextView: TextView = findViewById<TextView>(R.id.translation)
            val testKoreanWord = "나무"
            val translatedKoreanWord =
                callAPI(testKoreanWord)[0]
            translationTextView.text = testKoreanWord + "\n" + translatedKoreanWord
        }
    }

    private fun readFileDirectlyAsText(fileName: String): String {
        return application.assets.open(fileName).bufferedReader().use{
            it.readText()
        }
    }

    private fun loadPoem(): String {
        return readFileDirectlyAsText("testpoem.txt")
    }


}