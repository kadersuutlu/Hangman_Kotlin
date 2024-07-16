package com.kader.kotlin_hangman.repository

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject

class WordRepository @Inject constructor() {

    private val databaseReference = FirebaseDatabase.getInstance().reference.child("words")

    private val _selectedWord = MutableLiveData<String?>()
    val selectedWord: MutableLiveData<String?> = _selectedWord

    private val _description = MutableLiveData<String?>()
    val description: MutableLiveData<String?> = _description

    fun fetchRandomWord() {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val randomIndex = (0 until dataSnapshot.childrenCount).random()
                    val randomWordSnapshot = dataSnapshot.children.elementAt(randomIndex.toInt())

                    val rawWord = randomWordSnapshot.child("word").getValue(String::class.java)
                    val rawDescription =
                        randomWordSnapshot.child("description").getValue(String::class.java)

                    val processedWord = rawWord?.trim()?.replace("\\s+".toRegex(), "")
                    val processedDescription = rawDescription?.trim()

                    _selectedWord.value = processedWord
                    _description.value = processedDescription
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("Database read error: ${databaseError.message}")
            }
        })
    }
}