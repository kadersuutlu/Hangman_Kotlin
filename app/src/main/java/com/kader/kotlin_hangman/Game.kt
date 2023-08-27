package com.kader.kotlin_hangman

class Game(private val wordList: List<String>) {
    private var currentWord: Word? = null
    private val player = Player()

    init {
        selectRandomWord()
    }

    private fun selectRandomWord() {
        val randomIndex = (0 until wordList.size).random()
        currentWord = Word(wordList[randomIndex])
    }

    fun displayGameState(): String {
        // Oyun durumunu görsel olarak göster.
        return "Current word: ${currentWord?.displayWord()}\n" +
                "Guesses left: ${player.remainingGuesses}\n" +
                "Score: ${player.score}"
    }

    fun makeGuess(letter: Char): Boolean {
        // Kullanıcının tahminini kontrol et ve duruma göre puan ve tahmin sayısını güncelle.
        val isCorrect = currentWord?.makeGuess(letter) ?: false
        if (isCorrect) {
            player.increaseScore()
        } else {
            player.decreaseGuesses()
        }
        return isCorrect
    }

    fun isGameOver(): Boolean {
        // Oyunun bitip bitmediğini kontrol et.
        return player.remainingGuesses <= 0 || currentWord?.isComplete() == true
    }
}
