package com.kader.kotlin_hangman

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button

class Splash_Screen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val startButton:Button=findViewById(R.id.startButton)
        val continueButton:Button=findViewById(R.id.continueButton)


        // Start the main activity when "Oyunu Başlat" button is clicked
        startButton.setOnClickListener {
            startMainActivity()
        }

        // Start the main activity when "Devam Et" button is clicked
        continueButton.setOnClickListener {
            startMainActivity()
        }
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}