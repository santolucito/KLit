package com.example.klit

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.text.method.LinkMovementMethod

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val poemTextView = findViewById<TextView>(R.id.poem)

        var ss = genClickables(loadPoem(), findViewById<TextView>(R.id.translation))

        poemTextView.text = ss
        poemTextView.movementMethod = LinkMovementMethod.getInstance()


    }

    private fun readFileDirectlyAsText(fileName: String): String {
        return application.assets.open(fileName).bufferedReader().use{
            it.readText()
        }
    }

    private fun loadPoem(): String {
        return readFileDirectlyAsText("hometown.txt")
    }


}