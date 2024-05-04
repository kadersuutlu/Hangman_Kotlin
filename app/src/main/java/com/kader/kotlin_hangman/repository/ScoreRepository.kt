package com.kader.kotlin_hangman.repository

import androidx.lifecycle.LiveData
import com.kader.kotlin_hangman.db.ScoreDao
import com.kader.kotlin_hangman.entity.Score
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ScoreRepository(private val scoreDao: ScoreDao) {

    val allScores: LiveData<List<Score>> = scoreDao.getAllScores()
    suspend fun insertScore(score: Score) {
        withContext(Dispatchers.IO) {
            scoreDao.insertScore(score)
        }
    }
}