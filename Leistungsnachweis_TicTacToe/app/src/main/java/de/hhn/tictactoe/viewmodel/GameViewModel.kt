package de.hhn.tictactoe.viewmodel

import androidx.lifecycle.ViewModel
import de.hhn.tictactoe.model.Field
import de.hhn.tictactoe.model.GameModel
import de.hhn.tictactoe.model.Status
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class GameViewModel : ViewModel() {
    /**
     * Hier habe ich wie in den Folien beschrieben, die veränderlichen Variablen erstellt.
     * _variablenName wird nur im viewModel verwendet.
     * Auf "variablenName" kann über viewModel. nur lesend zugegriffen werden.
     */
    private var _currentGame = MutableStateFlow(GameModel())
    private var _gameField = MutableStateFlow(Array(3) { row ->
        Array(3) { column ->
            Field(Status.Empty, column, row)
        }
    })
    val gameField: StateFlow<Array<Array<Field>>> = _gameField.asStateFlow()
    val currentGame: StateFlow<GameModel> = _currentGame.asStateFlow()


    /**
     * Hier habe ich die Methode resetGame() implementiert.
     * Ich ersetze die Objekte in den Variablen komplett, da die UI nicht aktualisiert wird,
     * wenn nur die Werte innerhalb der Objekte zurückgesetzt werden.
     */
    fun resetGame() {
        _currentGame.update { GameModel() }
        _gameField.update {
            Array(3) { row ->
                Array(3) { column ->
                    Field(Status.Empty, column, row)
                }
            }
        }
    }

    /**
     * Hier habe ich die Methode selectField() implementiert.
     * Damit die UI aktualisiert wird, clone ich zunächst das gameField und verändere im Clone die Werte.
     * Danach überschreibe ich das gameField mit den geänderten Werten des Clones.
     *
     * Da es eine Änderung des gameFields gab, kontrolliere ich mit checkEndingGame(),
     * ob die Siegesbedingung nun vom aktiven Spieler erfüllt wurde.
     * Anschließend überschreibe ich das currentGame mit einem neuen GameModel mit den veränderten Werten.
     * Dabei wechsle ich auch noch den currentPlayer.
     */
    fun selectField(field: Field) {
        if (field.status == Status.Empty) {
            val tempFields = _gameField.value.clone()

            /**
             * Hier suche ich das richtige Field mit Reihen- und Spaltenindex.
             * In diesem Feld ändere ich den Status zum aktuellen Spieler.
             */
            tempFields[field.indexRow][field.indexColumn] =
                Field(_currentGame.value.currentPlayer, field.indexColumn, field.indexRow)

            _gameField.update {
                tempFields
            }
            checkEndingGame()
            _currentGame.update {
                GameModel(
                    _currentGame.value.currentPlayer.next(),
                    _currentGame.value.winningPlayer,
                    _currentGame.value.isGameEnding
                )
            }
        }
    }

    /**
     * Hier habe ich die Methode checkEndingGame() implementiert.
     * Diese nutze ich nur in der Methode selectField() und habe sie deshalb privat gemacht.
     * Ich kontrolliere einzeln die Spalten und Reihen und Diagonalen, ob der aktive Spieler 3 Felder nacheinander hat.
     * Wenn das der Fall ist, setze ich isGameEnding auf true und winningPlayer auf currentPlayer.
     * Nachdem ich durch eine Reihe/Spalte durchgegangen bin, setze ich den Zähler,
     * wie viele Felder in dieser vom aktiven Spieler belegt sind auf 0 zurück.
     */
    private fun checkEndingGame() {
        var quantityInRow = 0
        var quantityInColumn = 0
        var quantityInDiagonal1 = 0
        var quantityInDiagonal2 = 0

        for (i in 0..2) { // rows
            for (j in 0..2) { // columns
                /**
                 * Hier schaue ich wie viele Felder in einer Reihe vom aktiven Spieler belegt sind.
                 */
                if (_gameField.value[i][j].status == _currentGame.value.currentPlayer) {
                    quantityInRow++
                }
                /**
                 * Um im selben Durchlauf auch die Spalten zu kontrollieren,
                 * vertausche ich hier die Werte für rows und columns.
                 */
                if (_gameField.value[j][i].status == _currentGame.value.currentPlayer) {
                    quantityInColumn++
                }
            }
            /**
             * Ich kontrolliere jede Reihe, ob das Feld der linken und rechten Diagonale vom aktiven Spieler belegt ist.
             * linke Diagonale: (i,i) = (0,0) (1,1) (2,2)
             * rechte Diagonale: (i,2-i) = (0,2-0) (1,2-1) (2,2-2)
             *                              (0,2)   (1,1)   (2,0)
             */
            if (_gameField.value[i][i].status == _currentGame.value.currentPlayer) {
                quantityInDiagonal1++
            }
            if (_gameField.value[i][2 - i].status == _currentGame.value.currentPlayer) {
                quantityInDiagonal2++
            }

            /**
             * Sobald mindestens eine der Reihen/Spalten/Diagonale voll vom aktiven Spieler belegt ist,
             * wird das Spiel beendet und der aktive Spieler zum Sieger erklärt.
             * Die Schleife wird abgebrochen.
             * Wenn in der Schleife keine der Siegesbedingungen entdeckt wird, wird nichts verändert.
             */
            if (quantityInRow == 3 || quantityInColumn == 3 || quantityInDiagonal1 == 3 || quantityInDiagonal2 == 3) {
                _currentGame.value.isGameEnding = true
                _currentGame.value.winningPlayer = _currentGame.value.currentPlayer
                break
            }
            quantityInRow = 0
            quantityInColumn = 0
        }
    }

}