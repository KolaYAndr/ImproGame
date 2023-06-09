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
                    MainScreen()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ImproGameTheme {
        MainScreen()
    }
}

@Composable
fun MainScreen() {
    BackgroundInit()
    //добавить бокс для отмены и скрытия счёта
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceAround) {
        val fontSizeSmall = 26.sp
        val fontSizeLarge = 28.sp
        val verticalPadding = 2.dp
        val horizontalPadding = 0.dp
        val fontSize = 24.sp
        val cornerShape = 2.dp
        val fraction = 0.3f
        val boxPadding = 5.dp

        val teamName1 = remember { mutableStateOf("") }
        val teamName2 = remember { mutableStateOf("") }
        val scoreCounterTeam1 = remember { mutableStateOf(0) }
        val scoreCounterTeam2 = remember { mutableStateOf(0) }
        val roundScoreCounterTeam1 = remember { mutableStateOf(0) }
        val roundScoreCounterTeam2 = remember { mutableStateOf(0) }

        val focusManager = LocalFocusManager.current

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