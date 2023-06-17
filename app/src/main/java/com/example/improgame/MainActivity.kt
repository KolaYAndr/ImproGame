package com.example.improgame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.improgame.ui.theme.ImproGameTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ImproGameTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(
                        Handler.nameTeam1,
                        Handler.nameTeam2,
                        Handler.scoreTeam1,
                        Handler.scoreTeam2,
                        Handler.roundScoreTeam1,
                        Handler.roundScoreTeam2
                    )
                }
            }
        }
    }

    object Handler {
        var scoreTeam1: Int = 0
        var scoreTeam2: Int = 0
        var roundScoreTeam1: Int = 0
        var roundScoreTeam2: Int = 0
        var nameTeam1: String = ""
        var nameTeam2: String = ""

        fun increaseRoundScoreTeam1() {
            roundScoreTeam1++
        }

        fun increaseRoundScoreTeam2() {
            roundScoreTeam2++
        }

        fun swap() {
            scoreTeam1 += roundScoreTeam1
            scoreTeam2 += roundScoreTeam2

            roundScoreTeam1 = 0
            roundScoreTeam2 = 0
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ImproGameTheme {
        MainScreen(
            MainActivity.Handler.nameTeam1,
            MainActivity.Handler.nameTeam2,
            MainActivity.Handler.scoreTeam1,
            MainActivity.Handler.scoreTeam2,
            MainActivity.Handler.roundScoreTeam1,
            MainActivity.Handler.roundScoreTeam2
        )
    }
}

@Composable
fun MainScreen(
    nameTeam1: String, nameTeam2: String, scoreTeam1: Int, scoreTeam2: Int,
    roundScoreTeam1: Int, roundScoreTeam2: Int
) {
    val fontSizeSmall = 26.sp
    val fontSizeLarge = 28.sp
    val verticalPadding = 2.dp
    val horizontalPadding = 0.dp
    val fontSize = 24.sp
    val cornerShape = 2.dp
    val fraction = 0.3f
    val boxPadding = 5.dp

    val teamName1 = remember { mutableStateOf(nameTeam1) }
    val teamName2 = remember { mutableStateOf(nameTeam2) }
    val scoreCounterTeam1 = remember { mutableStateOf(scoreTeam1) }
    val scoreCounterTeam2 = remember { mutableStateOf(scoreTeam2) }
    val roundScoreCounterTeam1 = remember { mutableStateOf(roundScoreTeam1) }
    val roundScoreCounterTeam2 = remember { mutableStateOf(roundScoreTeam2) }

    val focusManager = LocalFocusManager.current
    BackgroundInit()
    //добавить бокс для отмены и скрытия счёта
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceAround) {
        TeamsAndScore(
            teamName1,
            fontSizeLarge,
            focusManager,
            scoreCounterTeam1,
            fontSizeSmall,
            horizontalPadding,
            verticalPadding,
            teamName2,
            scoreCounterTeam2
        )

        EndRoundButton(
            scoreCounterTeam1,
            roundScoreCounterTeam1,
            scoreCounterTeam2,
            roundScoreCounterTeam2
        )

        RoundScoreButtons(
            boxPadding,
            roundScoreCounterTeam1,
            fontSize,
            horizontalPadding,
            verticalPadding,
            fraction,
            cornerShape,
            roundScoreCounterTeam2
        )
    }
}

@Composable
private fun RoundScoreButtons(
    boxPadding: Dp,
    roundScoreCounterTeam1: MutableState<Int>,
    fontSize: TextUnit,
    horizontalPadding: Dp,
    verticalPadding: Dp,
    fraction: Float,
    cornerShape: Dp,
    roundScoreCounterTeam2: MutableState<Int>
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(boxPadding)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "${roundScoreCounterTeam1.value}",
                        fontSize = fontSize,
                        modifier = Modifier.padding(horizontalPadding, verticalPadding)
                    )
                    Button(
                        onClick = {
                            roundScoreCounterTeam1.value++
                            MainActivity.Handler.increaseRoundScoreTeam1()
                        },
                        colors = ButtonDefaults.buttonColors(Color.Red), modifier = Modifier
                            .fillMaxHeight(fraction)
                            .fillMaxWidth()
                            .clip(shape = RoundedCornerShape(cornerShape))
                    ) {

                    }
                }
            }
            Box(modifier = Modifier.padding(boxPadding)) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "${roundScoreCounterTeam2.value}",
                        fontSize = fontSize,
                        modifier = Modifier.padding(horizontalPadding, verticalPadding)
                    )
                    Button(
                        onClick = {
                            roundScoreCounterTeam2.value++
                            MainActivity.Handler.increaseRoundScoreTeam2()
                        },
                        colors = ButtonDefaults.buttonColors(Color.Blue), modifier = Modifier
                            .fillMaxHeight(fraction)
                            .fillMaxWidth()
                            .clip(shape = RoundedCornerShape(cornerShape))
                    ) {

                    }
                }
            }
        }
    }
}

@Composable
private fun EndRoundButton(
    scoreCounterTeam1: MutableState<Int>,
    roundScoreCounterTeam1: MutableState<Int>,
    scoreCounterTeam2: MutableState<Int>,
    roundScoreCounterTeam2: MutableState<Int>
) {
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.TopCenter
    ) {
        Button(
            onClick = {
                scoreCounterTeam1.value += roundScoreCounterTeam1.value
                scoreCounterTeam2.value += roundScoreCounterTeam2.value
                roundScoreCounterTeam1.value = 0
                roundScoreCounterTeam2.value = 0

                MainActivity.Handler.swap()
            },
            modifier = Modifier
                .background(Color.Transparent)
                .border(BorderStroke(3.dp, Color.Gray), shape = CircleShape),
            colors = ButtonDefaults.buttonColors(Color.Transparent)
        ) {
            Text(
                text = stringResource(id = R.string.end_round),
                fontSize = 26.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
private fun TeamsAndScore(
    teamName1: MutableState<String>,
    fontSizeLarge: TextUnit,
    focusManager: FocusManager,
    scoreCounterTeam1: MutableState<Int>,
    fontSizeSmall: TextUnit,
    horizontalPadding: Dp,
    verticalPadding: Dp,
    teamName2: MutableState<String>,
    scoreCounterTeam2: MutableState<Int>
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Box(modifier = Modifier.fillMaxWidth(0.5f)) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextField(
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                        ),
                        value = teamName1.value,
                        onValueChange = { newText ->
                            teamName1.value = newText
                            MainActivity.Handler.nameTeam1 = newText
                        },
                        textStyle = TextStyle(
                            Color.Red,
                            fontSizeLarge,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        ),
                        keyboardActions = KeyboardActions(onAny = { focusManager.clearFocus() }),
                        keyboardOptions = KeyboardOptions(
                            KeyboardCapitalization.Words,
                            imeAction = ImeAction.Done
                        )
                    )
                    Text(
                        text = "${scoreCounterTeam1.value}",
                        fontSize = fontSizeSmall,
                        modifier = Modifier.padding(horizontalPadding, verticalPadding)
                    )
                }
            }
            Box {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextField(
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent
                        ),
                        value = teamName2.value,
                        onValueChange = { newText ->
                            teamName2.value = newText
                            MainActivity.Handler.nameTeam2 = newText
                        },
                        modifier = Modifier
                            .background(color = Color.Transparent),
                        textStyle = TextStyle(
                            Color.Blue,
                            fontSizeLarge,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        ),
                        keyboardActions = KeyboardActions(onAny = { focusManager.clearFocus() }),
                        keyboardOptions = KeyboardOptions(
                            KeyboardCapitalization.Words,
                            imeAction = ImeAction.Done
                        )
                    )
                    Text(
                        text = "${scoreCounterTeam2.value}",
                        fontSize = fontSizeSmall,
                        modifier = Modifier.padding(horizontalPadding, verticalPadding)
                    )
                }
            }
        }
    }
}

@Composable
private fun BackgroundInit() {
    Image(
        painter = painterResource(id = R.drawable.fon),
        contentDescription = "background",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
}