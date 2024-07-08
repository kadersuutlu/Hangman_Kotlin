package com.kader.kotlin_hangman.ui.dialog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kader.kotlin_hangman.entity.Score
import com.kader.kotlin_hangman.repository.ScoreRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class DialogViewModel @Inject constructor(private val scoreRepository: ScoreRepository) :
    ViewModel() {
    private val _maxScore = MutableLiveData<Int?>()
    val maxScore: LiveData<Int?> = _maxScore

    private val _lastScore = MutableLiveData<Score?>()
    val lastScore: LiveData<Score?> = _lastScore

    fun maxAndLastScores() {
        viewModelScope.launch {
            _maxScore.value = scoreRepository.getMaxScore()
            _lastScore.value = scoreRepository.getLastScore()
        }
    }
}