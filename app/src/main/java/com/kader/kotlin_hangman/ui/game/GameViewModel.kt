package com.kader.kotlin_hangman.ui.game


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.kader.kotlin_hangman.entity.Score
import com.kader.kotlin_hangman.repository.ScoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val scoreRepository: ScoreRepository
) : ViewModel() {

    private val _selectedWord = MutableLiveData<String>()
    val selectedWord: LiveData<String> = _selectedWord

    private val _description = MutableLiveData<String>()
    val description: LiveData<String> = _description

    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int> = _score

    private val databaseReference = FirebaseDatabase.getInstance().reference.child("words")

    init {
        _score.value = 0
        loadInitialScores()
    }

    fun init() {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val randomIndex = (0 until dataSnapshot.childrenCount).random()
                    val randomWord = dataSnapshot.children.elementAt(randomIndex.toInt())

                    _selectedWord.value = randomWord.child("word").getValue(String::class.java)
                    _description.value = randomWord.child("description").getValue(String::class.java)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("Database read error: ${databaseError.message}")
            }
        })
    }

    fun incrementScore(points: Int) {
        val newScore = (_score.value ?: 0) + points
        _score.value = newScore
        saveScore(newScore)
    }

    private fun saveScore(score: Int) {
        viewModelScope.launch {
            scoreRepository.insertScore(Score(value = score))
        }
    }

    private fun loadInitialScores() {
        viewModelScope.launch {
            scoreRepository.allScores.observeForever { scores ->
                if (scores.isNotEmpty()) {
                    _score.value = scores.last().value
                }
            }
        }
    }
}
