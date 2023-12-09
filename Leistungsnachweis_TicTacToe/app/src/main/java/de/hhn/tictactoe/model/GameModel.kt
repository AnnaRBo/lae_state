package de.hhn.tictactoe.model

data class GameModel(
    var currentPlayer: Status = Status.PlayerX,
    var winningPlayer: Status = Status.Empty,
    var isGameEnding: Boolean = false
)