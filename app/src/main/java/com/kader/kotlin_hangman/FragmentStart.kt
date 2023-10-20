package com.kader.kotlin_hangman

import FragmentGame
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.OnBackPressedCallback

class FragmentStart : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_start, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val startButton = view.findViewById<Button>(R.id.startButton)
        //startButton.setBackgroundResource(R.drawable.button_background)
        startButton.setOnClickListener {

            val fragmentGame = FragmentGame()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragmentGame)
                .commit()
        }

        val scoreButton = view.findViewById<Button>(R.id.scoreButton)
        //scoreButton.setBackgroundResource(R.drawable.button_background)
        scoreButton.setOnClickListener {

            val fragmentScore = FragmentScores()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragmentScore)
                .commit()

            Log.d(TAG, "Score button clicked")
        }



    }
}
