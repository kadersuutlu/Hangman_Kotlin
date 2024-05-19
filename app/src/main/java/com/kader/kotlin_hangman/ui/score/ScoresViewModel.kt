package com.kader.kotlin_hangman.ui.score

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.kader.kotlin_hangman.db.ScoreDao
import com.kader.kotlin_hangman.entity.Score
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ScoresViewModel @Inject constructor(scoreDao: ScoreDao) : ViewModel() {
    val allScores: LiveData<List<Score>> = scoreDao.getAllScores()
}