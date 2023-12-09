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
    private val _gameField = MutableStateFlow(
        Array(3) {
            Array(3) {
                Field()
            }
        })
    private val _currentPlayer = MutableStateFlow(_currentGame.value.currentPlayer)
    private val _winningPlayer = MutableStateFlow(_currentGame.value.winningPlayer)
    private val _isGameEnding = MutableStateFlow(_currentGame.value.isGameEnding)

    val currentGame = _currentGame.asStateFlow()
    val gameField = _gameField.asStateFlow()
    val currentPlayer = _currentPlayer.asStateFlow()
    val winningPlayer = _winningPlayer.asStateFlow()
    val isGameEnding = _isGameEnding.asStateFlow()

    fun resetGame() {
        _currentPlayer.value = Status.PlayerX
        _winningPlayer.value = Status.Empty
        _isGameEnding.value = false
        for (i in 0..2) {
            for (j in 0..2) {
                _gameField.value[i][j].status = Status.Empty
            }
        }
    }

    fun selectField(field: Field) {
        if (field.status == Status.Empty) {
            field.status = _currentGame.value.currentPlayer
            checkEndingGame()
            _currentGame.value.currentPlayer.next()
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
                if (_gameField.value[i][j].status == _currentGame.value.currentPlayer) {
                    quantityInRow++
                }
                if (_gameField.value[j][i].status == _currentGame.value.currentPlayer) {
                    quantityInColumn++
                }
                if (_gameField.value[i][i].status == _currentGame.value.currentPlayer) {
                    quantityInDiagonal1++
                }
                if (_gameField.value[i][2 - i].status == _currentGame.value.currentPlayer) {
                    quantityInDiagonal2++
                }
            }

            if (quantityInRow == 3 || quantityInColumn == 3 || quantityInDiagonal1 == 3 || quantityInDiagonal2 == 3) {
                _currentGame.value.isGameEnding = true
                _currentGame.value.winningPlayer = _currentGame.value.currentPlayer
                break
            }
            quantityInRow = 0
            quantityInColumn = 0
            quantityInDiagonal1 = 0
            quantityInDiagonal2 = 0
        }
    }

}