package com.kader.kotlin_hangman

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if (savedInstanceState == null) { // Uygulama ilk kez açılıyorsa
            // FragmentStart'ı başlatın ve başlangıç ekranı olarak ayarlayın
            val startFragment = FragmentStart()
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, startFragment)
                .commit()
        }

    }
}