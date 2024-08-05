package com.kader.kotlin_hangman.ui.game


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kader.kotlin_hangman.entity.Score
import com.kader.kotlin_hangman.repository.ScoreRepository
import com.kader.kotlin_hangman.repository.WordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val scoreRepository: ScoreRepository,
    private val wordRepository: WordRepository
) : ViewModel() {

    private val _remainingAttempts = MutableLiveData<Int>()
    val remainingAttempts: LiveData<Int> = _remainingAttempts

    private val _incrementScore = MutableLiveData<Int>()
    val incrementScore: LiveData<Int> = _incrementScore

    val selectedWord: LiveData<String?> = wordRepository.selectedWord
    val description: LiveData<String?> = wordRepository.description

    private val _level = MutableLiveData<Int>(1)
    val level: LiveData<Int> get() = _level

    init {
        _remainingAttempts.value = 6
        _incrementScore.value = 0
        loadInitialScores()
        wordRepository.fetchRandomWord()
    }

    fun updateRemainingAttempts(newAttempts: Int) {
        _remainingAttempts.value = newAttempts
    }

    fun addScore(points: Int) {
        _incrementScore.value = (_incrementScore.value ?: 0) + points
        saveScore(_incrementScore.value ?: 0)
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
                    _incrementScore.value = scores.last().value
                }
            }
        }
    }
}