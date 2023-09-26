package com.kader.kotlin_hangman

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.GridView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gridView = findViewById<GridView>(R.id.gridView)
        val alphabet = listOf("A", "B", "C", "Ç", "D", "E", "F", "G", "Ğ", "H", "I", "İ", "J", "K", "L", "M", "N", "O", "Ö", "P", "R", "S", "Ş", "T", "U", "Ü", "V", "Y", "Z")

        val adapter = AlphabetAdapter(this, alphabet)
        gridView.adapter = adapter

        gridView.setOnItemClickListener { _, view, _, _ ->
            // Tıklanan öğenin arka plan rengini değiştir
            view.setBackgroundResource(R.drawable.custom_success_background)
        }


    }
}