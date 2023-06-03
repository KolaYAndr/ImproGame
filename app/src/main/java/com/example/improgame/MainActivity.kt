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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
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
    Image(
        painter = painterResource(id = R.drawable.fon),
        contentDescription = "background",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
    //добавить бокс для отмены и скрытия счёта
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceAround) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Box(modifier = Modifier.fillMaxWidth(0.5f)) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Заряженные",
                            fontSize = 30.sp,
                            color = Color.Red, fontWeight = FontWeight.Bold
                        ) //Поменять на текстфилд
                        Text(text = "16", fontSize = 28.sp, modifier = Modifier.padding(0.dp, 2.dp))
                    }
                }
                Box {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Сквозные",
                            fontSize = 30.sp,
                            color = Color.Blue, fontWeight = FontWeight.Bold
                        ) //Поменять на текстфилд
                        Text(text = "52", fontSize = 28.sp, modifier = Modifier.padding(0.dp, 2.dp))
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
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .background(Color.Transparent)
                    .border(BorderStroke(3.dp, Color.Gray), shape = CircleShape),
                colors = ButtonDefaults.buttonColors(Color.Transparent)
            ) {
                Text(text = "Завершить раунд", fontSize = 26.sp, color = Color.Gray)
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
                        .padding(5.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "1", fontSize = 24.sp, modifier = Modifier.padding(0.dp, 2.dp))
                        Button(
                            onClick = { /*TODO*/ },
                            colors = ButtonDefaults.buttonColors(Color.Red), modifier = Modifier
                                .fillMaxHeight(0.3f)
                                .fillMaxWidth()
                        ) {

                        }
                    }
                }
                Box(modifier = Modifier.padding(5.dp)) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "2", fontSize = 24.sp, modifier = Modifier.padding(0.dp, 2.dp))
                        Button(
                            onClick = { /*TODO*/ },
                            colors = ButtonDefaults.buttonColors(Color.Blue), modifier = Modifier
                                .fillMaxHeight(0.3f)
                                .fillMaxWidth()
                        ) {

                        }
                    }
                }
            }
        }
    }
}