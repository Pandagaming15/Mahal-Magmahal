package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.ui.GameViewModel
import com.example.ui.ScreenState
import com.example.ui.screens.EndingScreen
import com.example.ui.screens.GameScreen
import com.example.ui.screens.TitleScreen
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {

    private val viewModel: GameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainGameNavigation(
                        viewModel = viewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun MainGameNavigation(
    viewModel: GameViewModel,
    modifier: Modifier = Modifier
) {
    val screenState by viewModel.screenState.collectAsState()
    val showEnglishSubtitles by viewModel.showEnglishSubtitles.collectAsState()
    val showCrtScanlines by viewModel.showCrtScanlines.collectAsState()
    val currentEnding by viewModel.currentEnding.collectAsState()
    val pusoScore by viewModel.pusoScore.collectAsState()
    val realidadScore by viewModel.realidadScore.collectAsState()
    val unlockedSecrets by viewModel.unlockedSecrets.collectAsState()

    when (screenState) {
        ScreenState.TITLE -> {
            TitleScreen(
                showEnglishSubtitles = showEnglishSubtitles,
                showCrtScanlines = showCrtScanlines,
                onStartGame = { viewModel.startGame() },
                onOpenJukebox = { viewModel.openJukebox() },
                onToggleSubtitles = { viewModel.toggleSubtitles() },
                onToggleCrt = { viewModel.toggleCrt() },
                modifier = modifier
            )
        }
        ScreenState.ENDING -> {
            currentEnding?.let { ending ->
                EndingScreen(
                    ending = ending,
                    pusoScore = pusoScore,
                    realidadScore = realidadScore,
                    secretsCount = unlockedSecrets.size,
                    showEnglishSubtitles = showEnglishSubtitles,
                    showCrtScanlines = showCrtScanlines,
                    onRestart = { viewModel.restartGame() },
                    onOpenJukebox = { viewModel.openJukebox() },
                    modifier = modifier
                )
            } ?: run {
                GameScreen(
                    viewModel = viewModel,
                    modifier = modifier
                )
            }
        }
        else -> {
            GameScreen(
                viewModel = viewModel,
                modifier = modifier
            )
        }
    }
}
