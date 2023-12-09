package de.hhn.tictactoe.model

import androidx.compose.ui.graphics.Color

data class Field(
    var status: Status = Status.Empty,
    var indexColumn: Int = 0,
    var indexRow: Int = 0
) {
    fun showText(): String {
        return when (status) {
            Status.Empty -> ""
            Status.PlayerX -> "X"
            Status.PlayerO -> "O"
        }
    }

    fun showColor(): Color {
        return when (status) {
            Status.Empty -> Color.White
            Status.PlayerX -> Color.Blue
            Status.PlayerO -> Color.Red
        }
    }
}