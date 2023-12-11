package de.hhn.tictactoe.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.hhn.tictactoe.model.Status
import de.hhn.tictactoe.R
import de.hhn.tictactoe.model.Field
import de.hhn.tictactoe.viewmodel.GameViewModel

@ExperimentalMaterial3Api
@Composable
fun HomeScreen(viewModel: GameViewModel) {
    val gameField by viewModel.gameField.collectAsState()
    val currentGame by viewModel.currentGame.collectAsState()

    val currentPlayer = currentGame.currentPlayer
    val isGameEnding : Boolean = currentGame.isGameEnding
    val winningPlayer = currentGame.winningPlayer
    val winningPlayerColor = Field(winningPlayer).showColor()

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.displayLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 50.dp),
            color = Color.Black
        )
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
            ) {
                if (currentPlayer == Status.PlayerX) {
                    Text(
                        text = Status.PlayerX.toString(),
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 50.dp),
                        color = Color.Blue
                    )
                    Text(
                        text = Status.PlayerO.toString(),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 50.dp),
                        color = Color.Red
                    )
                } else {
                    Text(
                        text = Status.PlayerX.toString(),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 50.dp),
                        color = Color.Blue
                    )
                    Text(
                        text = Status.PlayerO.toString(),
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 50.dp),
                        color = Color.Red
                    )
                }
            }
            Column(
                modifier = Modifier.fillMaxSize().background(color = Color.White),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                gameField.forEach { rows ->
                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                    ) {
                        rows.forEach { field ->
                            Card(
                                modifier = Modifier
                                    .padding(all = 2.dp)
                                    .border(
                                        width = 2.dp,
                                        color = Color.Gray,
                                        shape = RoundedCornerShape(5.dp),
                                    )
                                    .height(111.dp)
                                    .width(111.dp),
                                onClick = {
                                    if (!currentGame.isGameEnding)
                                        viewModel.selectField(field)
                                    // test what happened
                                    Log.d(
                                        "clicked Field",
                                        "field ${field.indexColumn},${field.indexRow} with status ${field.status}"
                                    )
                                }
                            ) {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    Text(
                                        text = field.showText(),
                                        fontSize = 60.sp,
                                        color = field.showColor(),
                                    )
                                }
                            }
                        }
                    }
                }
                    Column(
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        if (isGameEnding) {
                            Text(
                                text = "Winning: $winningPlayer",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(vertical = 25.dp),
                                color = winningPlayerColor
                            )
                    }
                }
            }
        }
    }
}
