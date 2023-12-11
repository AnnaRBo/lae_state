package de.hhn.tictactoe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import de.hhn.tictactoe.ui.theme.TicTacToeTheme
import de.hhn.tictactoe.view.HomeScreen
import de.hhn.tictactoe.viewmodel.GameViewModel

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            /**
             * Hier habe ich das ViewModel hinzugefügt
             */
            val viewModel: GameViewModel by viewModels()
            TicTacToeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        modifier = Modifier
                            .fillMaxSize(),
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text(
                                        text = stringResource(id = R.string.app_name),
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(vertical = 50.dp),
                                        color = Color.Black
                                    )
                                },
                                actions = {
                                    IconButton(
                                        onClick = {
                                            /**
                                             *  Hier habe ich die erste Reset-Möglichkeit implementiert.
                                             */
                                            viewModel.resetGame()
                                        }
                                    ) {
                                        Icon(
                                            Icons.Filled.Refresh,
                                            contentDescription = "Reload Game"
                                        )
                                    }
                                },
                                navigationIcon = {
                                    IconButton(
                                        onClick = {
                                            /**
                                             *  Hier habe ich die zweite Reset-Möglichkeit implementiert.
                                             */
                                            viewModel.resetGame()
                                        }
                                    ) {
                                        Icon(
                                            Icons.Default.Menu,
                                            contentDescription = "Reload Game"
                                        )
                                    }
                                },
                                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = LightGray)
                            )
                        }
                    ) { values ->
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(values)
                        ) {
                            item {
                                HomeScreen(viewModel)
                            }
                        }
                    }

                }
            }
        }
    }
}