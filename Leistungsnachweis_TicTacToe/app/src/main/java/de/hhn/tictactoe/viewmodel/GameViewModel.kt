package de.hhn.tictactoe.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import de.hhn.tictactoe.model.Field
import de.hhn.tictactoe.model.GameModel
import de.hhn.tictactoe.model.Status
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class GameViewModel(context: Context) : ViewModel() {

    private val theContext: Context = context
    private val _currentGame = MutableStateFlow(GameModel())
    val currentGame = _currentGame.asStateFlow()
    private val _gameField = MutableStateFlow(
        Array(3) {
            Array(3) {
                Field()
            }
        })
    val gameField = _gameField.asStateFlow()


    fun resetGame() {
        _currentGame.value.currentPlayer = Status.PlayerX
        _currentGame.value.winningPlayer = Status.Empty
        _currentGame.value.isGameEnding = false
        for (i in 0..2) {
            for (j in 0..2) {
                gameField.value[i][j].status = Status.Empty
            }
        }


    }

    fun selectField(field: Field) {
        if (field.status == Status.Empty) {
            field.status = currentGame.value.currentPlayer
            checkEndingGame()
            currentGame.value.currentPlayer.next()
        } else {
            Toast.makeText(theContext, "Invalid move! Please choose an other field.", Toast.LENGTH_LONG).show()
        }
    }

    private fun checkEndingGame() {
        var quantityInRow = 0
        var quantityInColumn = 0
        var quantityInDiagonal1 = 0
        var quantityInDiagonal2 = 0

        for (i in 0..2) {
            for (j in 0..2) {
                if (gameField.value[i][j].status == currentGame.value.currentPlayer) {
                    quantityInRow++
                }
                if (gameField.value[j][i].status == currentGame.value.currentPlayer) {
                    quantityInColumn++
                }
                if (gameField.value[i][i].status == currentGame.value.currentPlayer) {
                    quantityInDiagonal1++
                }
                if (gameField.value[i][2 - i].status == currentGame.value.currentPlayer) {
                    quantityInDiagonal2++
                }
            }

            if (quantityInRow == 3 || quantityInColumn == 3 || quantityInDiagonal1 == 3 || quantityInDiagonal2 == 3) {
                currentGame.value.isGameEnding = true
                currentGame.value.winningPlayer = currentGame.value.currentPlayer
                break
            }
            quantityInRow = 0
            quantityInColumn = 0
            quantityInDiagonal1 = 0
            quantityInDiagonal2 = 0
        }
    }

}