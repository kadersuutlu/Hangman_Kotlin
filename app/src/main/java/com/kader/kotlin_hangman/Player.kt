package com.kader.kotlin_hangman

class Player {
    var score = 0
        private set

    var remainingGuesses = 6
        private set

    fun increaseScore() {
        score += 10
    }

    fun decreaseGuesses() {
        remainingGuesses--
    }
}
