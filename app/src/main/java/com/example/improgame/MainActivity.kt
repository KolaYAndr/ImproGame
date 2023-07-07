package com.example.improgame

import android.os.Bundle
import android.util.Log
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
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.improgame.ui.theme.ImproGameTheme
import java.util.Stack

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
                    MainScreen(Handler)
                }
            }
        }
    }

    object Handler {
        val scoreTeam0 = mutableStateOf(0)
        val scoreTeam1 = mutableStateOf(0)
        val roundScoreTeam0 = mutableStateOf(0)
        val roundScoreTeam1 = mutableStateOf(0)
        val nameTeam0 = mutableStateOf("")
        val nameTeam1 = mutableStateOf("")

        private val stack = Stack<Int>()

        fun increaseRoundScoreTeam0() {
            roundScoreTeam0.value++
            stack.push(0)
        }

        fun increaseRoundScoreTeam1() {
            roundScoreTeam1.value++
            stack.push(1)
        }

        fun endRound() {
            scoreTeam0.value += roundScoreTeam0.value
            scoreTeam1.value += roundScoreTeam1.value

            roundScoreTeam0.value = 0
            roundScoreTeam1.value = 0
        }

        fun switchTeams() {
            val name1 = nameTeam0.value
            nameTeam0.value = nameTeam1.value
            nameTeam1.value = name1
        }

        fun reverse() {
            when (stack.pop()) {
                0 -> roundScoreTeam0.value--
                1 -> roundScoreTeam1.value--
                else -> Log.ERROR
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ImproGameTheme {
        MainScreen(MainActivity.Handler)
    }
}

@Composable
fun MainScreen(handler: MainActivity.Handler) {
    BackgroundInit()
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceAround) {
        TeamsAndScore(handler.nameTeam0, handler.scoreTeam0, handler.nameTeam1, handler.scoreTeam1)

        EndRoundButton()

        RoundScoreButtons(
            handler.roundScoreTeam0,
            handler.roundScoreTeam1
        )
    }
}

@Composable
private fun RoundScoreButtons(
    roundScoreTeam0: MutableState<Int>,
    roundScoreTeam1: MutableState<Int>
) {
    val verticalPadding = 2.dp
    val horizontalPadding = 0.dp
    val fontSize = 24.sp
    val cornerShape = 2.dp
    val fraction = 0.3f
    val boxPadding = 5.dp
    val iconSize = 32.dp

    val roundScoreCounterTeam0 = remember { roundScoreTeam0 }
    val roundScoreCounterTeam1 = remember { roundScoreTeam1 }

    Column {
        IconButton(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = {
                MainActivity.Handler.reverse()
            },
            enabled = roundScoreCounterTeam0.value + roundScoreCounterTeam1.value > 0
        )
        {
            Icon(
                painter = painterResource(id = R.drawable.arrow_back),
                contentDescription = "Cancel round score increase",
                modifier = Modifier.size(iconSize)
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(boxPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "${roundScoreCounterTeam0.value}",
                    fontSize = fontSize,
                    modifier = Modifier.padding(horizontalPadding, verticalPadding)
                )
                Button(
                    onClick = {
                        MainActivity.Handler.increaseRoundScoreTeam0()
                    },
                    colors = ButtonDefaults.buttonColors(Color.Red),
                    modifier = Modifier
                        .fillMaxHeight(fraction)
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(cornerShape))
                ) {

                }
            }

            Column(
                modifier = Modifier
                    .padding(boxPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "${roundScoreCounterTeam1.value}",
                    fontSize = fontSize,
                    modifier = Modifier.padding(horizontalPadding, verticalPadding)
                )
                Button(
                    onClick = {
                        MainActivity.Handler.increaseRoundScoreTeam1()
                    },
                    colors = ButtonDefaults.buttonColors(Color.Blue),
                    modifier = Modifier
                        .fillMaxHeight(fraction)
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(cornerShape))
                ) {

                }
            }

        }
    }
}

@Composable
private fun EndRoundButton() {
    val fontSizeSmall = 26.sp
    val borderStrokeWidth = 3.dp

    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.TopCenter
    ) {
        Button(
            onClick = {
                MainActivity.Handler.endRound()
            },
            modifier = Modifier
                .background(Color.Transparent)
                .border(BorderStroke(borderStrokeWidth, Color.Gray), shape = CircleShape),
            colors = ButtonDefaults.buttonColors(Color.Transparent)
        ) {
            Text(
                text = stringResource(id = R.string.end_round),
                fontSize = fontSizeSmall,
                color = Color.Gray
            )
        }
    }
}

@Composable
private fun TeamsAndScore(
    nameTeam0: MutableState<String>,
    scoreTeam0: MutableState<Int>,
    nameTeam1: MutableState<String>,
    scoreTeam1: MutableState<Int>
) {
    val fontSizeSmall = 26.sp
    val fontSizeLarge = 28.sp
    val verticalPadding = 2.dp
    val horizontalPadding = 0.dp
    val focusManager = LocalFocusManager.current
    val iconSize = 32.dp

    val teamName0 = remember { nameTeam0 }
    val teamName1 = remember { nameTeam1 }
    val scoreCounterTeam0 = remember { scoreTeam0 }
    val scoreCounterTeam1 = remember { scoreTeam1 }


    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(0.5f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField(
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                    ),
                    value = teamName0.value,
                    onValueChange = { newText ->
                        MainActivity.Handler.nameTeam0.value = newText
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
                    ),
                    modifier = Modifier
                        .requiredHeight(65.dp)
                        .clip(RoundedCornerShape(20.dp))
                )
                Text(
                    text = "${scoreCounterTeam0.value}",
                    fontSize = fontSizeSmall,
                    modifier = Modifier.padding(horizontalPadding, verticalPadding)
                )
            }

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
                        MainActivity.Handler.nameTeam1.value = newText
                    },
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
                    ),
                    modifier = Modifier
                        .requiredHeight(65.dp)
                        .clip(RoundedCornerShape(20.dp))
                )
                Text(
                    text = "${scoreCounterTeam1.value}",
                    fontSize = fontSizeSmall,
                    modifier = Modifier.padding(horizontalPadding, verticalPadding)
                )
            }

        }
        IconButton(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = {
                MainActivity.Handler.switchTeams()
            },
            enabled = teamName0.value.length + teamName1.value.length > 0
        )
        {
            Icon(
                painter = painterResource(id = R.drawable.swap_horiz),
                contentDescription = "Swap teams button",
                modifier = Modifier.size(iconSize)
            )
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