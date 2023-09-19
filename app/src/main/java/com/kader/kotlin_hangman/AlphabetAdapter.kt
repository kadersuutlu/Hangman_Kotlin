package com.kader.kotlin_hangman

import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class AlphabetAdapter(private val context: Context, private val alphabet: List<String>) : BaseAdapter() {

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
        val textView = TextView(context)
        textView.text = alphabet[position]
        textView.gravity = Gravity.CENTER
        return textView
    }
}
