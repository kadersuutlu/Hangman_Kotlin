package com.kader.kotlin_hangman.ui.adapter

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.kader.kotlin_hangman.R

class AlphabetAdapter(private val context: Context, private val alphabet: List<String>) :
    BaseAdapter() {

    override fun getCount(): Int {
        return alphabet.size
    }

    override fun getItem(position: Int): Any {
        return alphabet[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val letter = alphabet[position]
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val customView = inflater.inflate(R.layout.custom_letter_item, null)

        val textView = customView.findViewById<TextView>(R.id.customTextView)

        textView.text = letter
        textView.gravity = Gravity.CENTER

        return customView
    }
}

