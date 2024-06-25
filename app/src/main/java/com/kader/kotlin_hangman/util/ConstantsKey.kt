package com.kader.kotlin_hangman.util

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey

object ConstantsKey {
    val SCORE_DATABASE: Preferences.Key<String> = stringPreferencesKey("ScoreDatabase.db")
    val score_db = "score_database"
}