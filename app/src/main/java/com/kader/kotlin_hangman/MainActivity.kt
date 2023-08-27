package com.kader.kotlin_hangman

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.GridLayout

class MainActivity : AppCompatActivity() {

    private val alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val keyBoardGridLayout=findViewById<GridLayout>(R.id.keyboardGridLayout)

        for (letter in alphabet){
            val button=Button(this)
            button.text=letter.toString()

            button.setBackgroundColor(getColor(R.color.green))

            button.setOnClickListener {
                if (!button.isEnabled){
                    return@setOnClickListener
                }

                makeGuess(letter)

                button.isEnabled=false

                button.postDelayed({
                    button.setBackgroundColor(getColor(R.color.purple))
                    button.isEnabled=true
                },500)
            }
            keyBoardGridLayout.addView(button)
        }
    }

    private fun makeGuess(letter: Char) {

    }
}