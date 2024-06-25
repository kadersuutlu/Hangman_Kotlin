package com.kader.kotlin_hangman.di

import android.content.Context
import androidx.room.Room
import com.kader.kotlin_hangman.db.ScoreDao
import com.kader.kotlin_hangman.db.ScoreDatabase
import com.kader.kotlin_hangman.repository.ScoreRepository
import com.kader.kotlin_hangman.util.ConstantsKey
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomDatabaseModule {

    @Provides
    @Singleton
    fun provideScoreDatabase(@ApplicationContext appContext: Context): ScoreDatabase =
        Room.databaseBuilder(
            appContext,
            ScoreDatabase::class.java,
            ConstantsKey.score_db
        ).build()

    @Provides
    fun provideScoreDao(scoreDatabase: ScoreDatabase): ScoreDao = scoreDatabase.scoreDao()

    @Provides
    @Singleton
    fun provideScoreRepository(scoreDao: ScoreDao): ScoreRepository {
        return ScoreRepository(scoreDao)
    }
}