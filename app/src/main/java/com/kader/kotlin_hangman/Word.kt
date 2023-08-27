package com.kader.kotlin_hangman

class Word(private val word: String) {
    private val guessedLetters = mutableSetOf<Char>()

    fun displayWord(): String {
        // Doğru tahmin edilen harfleri göster, diğerlerini '_' ile göster.
        return word.map { if (guessedLetters.contains(it)) it else '_' }.joinToString(" ")
    }

    fun makeGuess(letter: Char): Boolean {
        // Kullanıcının harf tahminini kontrol et.
        if (word.contains(letter)) {
            guessedLetters.add(letter)
            return true
        }
        return false
    }

    fun isComplete(): Boolean {
        // Tüm harfler doğru tahmin edildi mi?
        return word.all { guessedLetters.contains(it) }
    }
}
