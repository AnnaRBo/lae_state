package de.hhn.tictactoe.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import de.hhn.tictactoe.model.Field
import de.hhn.tictactoe.model.GameModel
import de.hhn.tictactoe.model.Status

class GameViewModel(context: Context) : ViewModel() {

    private val theContext: Context = context
    val currentGame = GameModel()
    val gameField =
        Array(3) {
            Array(3) {
                Field()
            }
        }


    fun resetGame() {
        currentGame.currentPlayer = Status.PlayerX
        currentGame.winningPlayer = Status.Empty
        currentGame.isGameEnding = false
        for (i in 0..2) {
            for (j in 0..2) {
                gameField[i][j].status = Status.Empty
            }
        }


    }

    fun selectField(field: Field) {
        if (field.status == Status.Empty) {
            field.status = currentGame.currentPlayer
            checkEndingGame()
            currentGame.currentPlayer.next()
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
                if (gameField[i][j].status == currentGame.currentPlayer) {
                    quantityInRow++
                }
                if (gameField[j][i].status == currentGame.currentPlayer) {
                    quantityInColumn++
                }
                if (gameField[i][i].status == currentGame.currentPlayer) {
                    quantityInDiagonal1++
                }
                if (gameField[i][2 - i].status == currentGame.currentPlayer) {
                    quantityInDiagonal2++
                }
            }

            if (quantityInRow == 3 || quantityInColumn == 3 || quantityInDiagonal1 == 3 || quantityInDiagonal2 == 3) {
                currentGame.isGameEnding = true
            }
            quantityInRow = 0
        }
    }

}