package de.hhn.tictactoe.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import de.hhn.tictactoe.model.Field
import de.hhn.tictactoe.model.GameModel
import de.hhn.tictactoe.model.Status
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GameViewModel(context: Context) : ViewModel() {

    private val theContext: Context = context

    private var _currentGame = MutableStateFlow(GameModel())
    private var _gameField = MutableStateFlow(Array(3) { row ->
        Array(3) { column ->
            Field(Status.Empty,column,row)
        }
    })
    private var _currentPlayer = MutableStateFlow(_currentGame.value.currentPlayer)
    private var _winningPlayer = MutableStateFlow(_currentGame.value.winningPlayer)
    private var _isGameEnding = MutableStateFlow(_currentGame.value.isGameEnding)

    val gameField : StateFlow<Array<Array<Field>>> = _gameField.asStateFlow()
    val currentGame : StateFlow<GameModel> = _currentGame.asStateFlow()

    fun resetGame() {
        _currentPlayer = MutableStateFlow(Status.PlayerX)
        _winningPlayer = MutableStateFlow(Status.Empty)
        _isGameEnding = MutableStateFlow(false)
        _gameField = MutableStateFlow(Array(3) { row ->
            Array(3) { column ->
                Field(Status.Empty,column,row)
            }
        })
    }

    fun selectField(field: Field) {
        if (field.status == Status.Empty) {
            val tempFields = _gameField
            tempFields.value[field.indexRow][field.indexColumn] = Field(_currentGame.value.currentPlayer, field.indexColumn, field.indexRow)
            _gameField = tempFields
            checkEndingGame()
            val tempGame = _currentGame
            tempGame.value.currentPlayer.next()
            _currentGame = tempGame
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
                val tempGame = _currentGame
                tempGame.value.isGameEnding = true
                tempGame.value.winningPlayer = _currentGame.value.currentPlayer
                _currentGame = tempGame
                break
            }
            quantityInRow = 0
            quantityInColumn = 0
            quantityInDiagonal1 = 0
            quantityInDiagonal2 = 0
        }
    }

}