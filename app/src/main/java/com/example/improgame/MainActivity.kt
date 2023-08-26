package com.example.improgame

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import com.example.improgame.ui.composables.Background
import com.example.improgame.ui.composables.CustomButtonsWithTextsAbove
import com.example.improgame.ui.composables.CustomIconButton
import com.example.improgame.ui.composables.EndRoundButton
import com.example.improgame.ui.composables.TeamNameAndTextBelow
import com.example.improgame.ui.composables.WindowSize
import com.example.improgame.ui.composables.WindowType
import com.example.improgame.ui.composables.getScreenWidthInDp
import com.example.improgame.ui.composables.rememberWindowSize
import com.example.improgame.ui.theme.ImproGameTheme
import com.example.improgame.utils.Constants.Companion.colorBlue
import com.example.improgame.utils.Constants.Companion.colorRed
import com.example.improgame.utils.Constants.Companion.iconSize
import com.example.improgame.utils.Constants.Companion.paddingValue
import java.util.Stack


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ImproGameTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(Handler)
                }
            }
        }
    }

    object Handler {
        val scoreTeam0 = mutableIntStateOf(0)
        val scoreTeam1 = mutableIntStateOf(0)
        val roundScoreTeam0Jury0 = mutableIntStateOf(0)
        val roundScoreTeam1Jury0 = mutableIntStateOf(0)
        val roundScoreTeam0Jury1 = mutableIntStateOf(0)
        val roundScoreTeam1Jury1 = mutableIntStateOf(0)
        val nameTeam0 = mutableStateOf("")
        val nameTeam1 = mutableStateOf("")

        private val stack = Stack<Int>()

        fun increaseRoundScoreTeam0Jury0() {
            roundScoreTeam0Jury0.intValue++
            stack.push(0)
        }

        fun increaseRoundScoreTeam1Jury0() {
            roundScoreTeam1Jury0.intValue++
            stack.push(1)

        }

        fun increaseRoundScoreTeam0Jury1() {
            roundScoreTeam0Jury1.intValue++
            stack.push(2)
        }

        fun increaseRoundScoreTeam1Jury1() {
            roundScoreTeam1Jury1.intValue++
            stack.push(3)

        }

        fun endRound() {
            scoreTeam0.intValue += roundScoreTeam0Jury0.intValue
            scoreTeam1.intValue += roundScoreTeam1Jury0.intValue
            scoreTeam0.intValue += roundScoreTeam0Jury1.intValue
            scoreTeam1.intValue += roundScoreTeam1Jury1.intValue

            roundScoreTeam0Jury0.intValue = 0
            roundScoreTeam1Jury0.intValue = 0
            roundScoreTeam0Jury1.intValue = 0
            roundScoreTeam1Jury1.intValue = 0
        }

        fun switchTeams() {
            val name1 = nameTeam0.value
            nameTeam0.value = nameTeam1.value
            nameTeam1.value = name1
        }

        fun rollBack() {
            when (stack.pop()) {
                0 -> roundScoreTeam0Jury0.intValue--
                1 -> roundScoreTeam1Jury0.intValue--
                2 -> roundScoreTeam0Jury1.intValue--
                3 -> roundScoreTeam1Jury1.intValue--

                else -> Log.ERROR
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun MainScreenPreview() {
    MainScreen(handler = MainActivity.Handler)
}

@Composable
fun MainScreen(handler: MainActivity.Handler) {
    val window = rememberWindowSize()
    Background(R.drawable.back)
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TeamsAndScore(
            handler.nameTeam0,
            handler.scoreTeam0,
            handler.nameTeam1,
            handler.scoreTeam1,
            window
        )

        EndRoundButton()

        RoundScoreButtons(
            handler.roundScoreTeam0Jury0,
            handler.roundScoreTeam1Jury0,
            handler.roundScoreTeam0Jury1,
            handler.roundScoreTeam1Jury1,
            window
        )
    }
}

@Composable
private fun RoundScoreButtons(
    roundScoreTeam0Jury0: MutableState<Int>,
    roundScoreTeam1Jury0: MutableState<Int>,
    roundScoreTeam0Jury1: MutableState<Int>,
    roundScoreTeam1Jury1: MutableState<Int>,
    window: WindowSize
) {
    val width = getScreenWidthInDp()

    val roundScoreCounterTeam0Jury0 = remember { roundScoreTeam0Jury0 }
    val roundScoreCounterTeam1Jury0 = remember { roundScoreTeam1Jury0 }


    if (window.width != WindowType.Compact) {
        val roundScoreCounterTeam0Jury1 = remember { roundScoreTeam0Jury1 }
        val roundScoreCounterTeam1Jury1 = remember { roundScoreTeam1Jury1 }

        val buttonWidth = (width - paddingValue * 8 - iconSize) / 4

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CustomButtonsWithTextsAbove(
                textSource0 = roundScoreCounterTeam0Jury0,
                onClick0 = { MainActivity.Handler.increaseRoundScoreTeam0Jury0() },
                textSource1 = roundScoreCounterTeam1Jury0,
                onClick1 = { MainActivity.Handler.increaseRoundScoreTeam1Jury0() },
                buttonWidth = buttonWidth
            )
            CustomIconButton(
                onClick = { MainActivity.Handler.rollBack() },
                enabledState = roundScoreCounterTeam0Jury0.value + roundScoreCounterTeam1Jury0.value +
                        roundScoreCounterTeam0Jury1.value + roundScoreCounterTeam1Jury1.value > 0,
                resId = R.drawable.arrow_back
            )
            CustomButtonsWithTextsAbove(
                textSource0 = roundScoreCounterTeam0Jury1,
                onClick0 = { MainActivity.Handler.increaseRoundScoreTeam0Jury1() },
                textSource1 = roundScoreCounterTeam1Jury1,
                onClick1 = { MainActivity.Handler.increaseRoundScoreTeam1Jury1() },
                buttonWidth = buttonWidth
            )
        }
    } else {
        val buttonWidth = (width - paddingValue * 2 - iconSize) / 2

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CustomIconButton(
                onClick = { MainActivity.Handler.rollBack() },
                enabledState = roundScoreCounterTeam0Jury0.value + roundScoreCounterTeam1Jury0.value > 0,
                resId = R.drawable.arrow_back
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                CustomButtonsWithTextsAbove(
                    textSource0 = roundScoreCounterTeam0Jury0,
                    onClick0 = { MainActivity.Handler.increaseRoundScoreTeam0Jury0() },
                    textSource1 = roundScoreCounterTeam1Jury0,
                    onClick1 = { MainActivity.Handler.increaseRoundScoreTeam1Jury0() },
                    buttonWidth = buttonWidth
                )
            }
        }
    }
}



@Composable
private fun TeamsAndScore(
    nameTeam0: MutableState<String>,
    scoreTeam0: MutableState<Int>,
    nameTeam1: MutableState<String>,
    scoreTeam1: MutableState<Int>,
    window: WindowSize
) {
    val width = getScreenWidthInDp()
    val textFieldWidth = (width - iconSize - paddingValue * 4) / 2
    val focusManager = LocalFocusManager.current

    val teamName0 = remember { nameTeam0 }
    val teamName1 = remember { nameTeam1 }
    val scoreCounterTeam0 = remember { scoreTeam0 }
    val scoreCounterTeam1 = remember { scoreTeam1 }

    if (window.width != WindowType.Compact) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TeamNameAndTextBelow(
                teamName = teamName0,
                scoreCounterTeam = scoreCounterTeam0,
                textColor = Color.Red,
                focusManager = focusManager,
                onValueChange = { newText ->
                    MainActivity.Handler.nameTeam0.value = newText
                },
                textFieldWidth = textFieldWidth
            )
            CustomIconButton(
                onClick = { MainActivity.Handler.switchTeams() },
                enabledState = teamName0.value.length + teamName1.value.length > 0,
                resId = R.drawable.swap_horiz
            )
            TeamNameAndTextBelow(
                teamName = teamName1,
                scoreCounterTeam = scoreCounterTeam1,
                textColor = Color.Blue,
                focusManager = focusManager,
                onValueChange = { newText ->
                    MainActivity.Handler.nameTeam1.value = newText
                },
                textFieldWidth = textFieldWidth
            )

        }
    } else {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                TeamNameAndTextBelow(
                    teamName = teamName0,
                    scoreCounterTeam = scoreCounterTeam0,
                    textColor = colorRed,
                    focusManager = focusManager,
                    onValueChange = { newText ->
                        MainActivity.Handler.nameTeam0.value = newText
                    },
                    textFieldWidth = textFieldWidth
                )
                TeamNameAndTextBelow(
                    teamName = teamName1,
                    scoreCounterTeam = scoreCounterTeam1,
                    textColor = colorBlue,
                    focusManager = focusManager,
                    onValueChange = { newText ->
                        MainActivity.Handler.nameTeam1.value = newText
                    },
                    textFieldWidth = textFieldWidth
                )
            }
            CustomIconButton(
                onClick = { MainActivity.Handler.switchTeams() },
                enabledState = teamName0.value.length + teamName1.value.length > 0,
                resId = R.drawable.swap_horiz
            )
        }
    }
}