package com.kader.kotlin_hangman.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kader.kotlin_hangman.entity.Score

@Database(entities = [Score::class], version = 1)
abstract class ScoreDatabase : RoomDatabase() {
    abstract fun scoreDao(): ScoreDao
}