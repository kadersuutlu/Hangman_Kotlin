package com.kader.kotlin_hangman

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import androidx.activity.OnBackPressedCallback

class FragmentGame : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gridView = view.findViewById<GridView>(R.id.gridView)
        val alphabet = listOf("A", "B", "C", "Ç", "D", "E", "F", "G", "Ğ", "H", "I", "İ", "J", "K", "L", "M", "N", "O", "Ö", "P", "R", "S", "Ş", "T", "U", "Ü", "V", "Y", "Z")

        val adapter = AlphabetAdapter(requireContext(), alphabet)
        gridView.adapter = adapter

        gridView.setOnItemClickListener { _, gridViewItem, _, _ ->
            gridViewItem.setBackgroundResource(R.drawable.custom_success_background)

        }

        requireActivity()
            .onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    Log.d(ContentValues.TAG, "Back button pressed ")
                    if (isEnabled) {
                        isEnabled = false
                        activity?.supportFragmentManager?.beginTransaction()
                            ?.remove(this@FragmentGame)?.commit()
                    }
                }
            })
    }


}