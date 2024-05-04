package com.kader.kotlin_hangman.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kader.kotlin_hangman.entity.Score

@Dao
interface ScoreDao {
    @Query("SELECT * FROM scores")
    fun getAllScores(): LiveData<List<Score>>

    @Insert(entity = Score::class)
    fun insertScore(score: Score)
}
