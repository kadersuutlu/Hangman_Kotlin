package com.kader.kotlin_hangman.ui.game


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor() : ViewModel() {

    private val _selectedWord = MutableLiveData<String>()
    val selectedWord: LiveData<String> = _selectedWord

    private val _description = MutableLiveData<String>()
    val description: LiveData<String> = _description

    private val databaseReference = FirebaseDatabase.getInstance().reference.child("kelimeler")

    fun init() {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val randomIndex = (0 until dataSnapshot.childrenCount).random()
                    val randomWord = dataSnapshot.children.elementAt(randomIndex.toInt())

                    _selectedWord.value = randomWord.child("kelime").getValue(String::class.java)
                    _description.value = randomWord.child("aciklama").getValue(String::class.java)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("Database read error: ${databaseError.message}")
            }
        })
    }
}