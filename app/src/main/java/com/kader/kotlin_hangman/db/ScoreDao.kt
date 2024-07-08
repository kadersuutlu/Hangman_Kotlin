package com.kader.kotlin_hangman.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kader.kotlin_hangman.entity.Score

@Dao
interface ScoreDao {
    @Query("SELECT * FROM scores")
    fun getAllScores(): LiveData<List<Score>>

    @Insert(entity = Score::class)
    fun insertScore(score: Score)

    @Query("SELECT MAX(value) FROM scores")
    fun getMaxScore(): Int?

    @Query("SELECT * FROM scores ORDER BY id DESC LIMIT 1")
    fun getLastScore(): Score?
}
