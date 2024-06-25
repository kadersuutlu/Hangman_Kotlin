package com.kader.kotlin_hangman.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.kader.kotlin_hangman.repository.WordRepository


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideWordRepository(): WordRepository {
        return WordRepository()
    }
}
