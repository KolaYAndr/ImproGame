package com.example.improgame.ui.composables

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.improgame.MainActivity
import com.example.improgame.R
import com.example.improgame.utils.Constants
import com.example.improgame.utils.Constants.Companion.fontSizeLarge
import com.example.improgame.utils.Constants.Companion.fontSizeMedium
import com.example.improgame.utils.Constants.Companion.fontSizeSmall
import com.example.improgame.utils.Constants.Companion.iconSize
import com.example.improgame.utils.Constants.Companion.maxHeight
import com.example.improgame.utils.Constants.Companion.minHeight
import com.example.improgame.utils.Constants.Companion.paddingValue
import com.example.improgame.utils.Constants.Companion.roundedCorner

@Composable
fun CustomIconButton(
    modifier: Modifier = Modifier, onClick: () -> Unit, enabledState: Boolean,
    @DrawableRes resId: Int
) {
    IconButton(
        modifier = modifier,
        onClick = onClick,
        enabled = enabledState
    )
    {
        Icon(
            painter = painterResource(id = resId),
            contentDescription = "",
            modifier = Modifier.size(iconSize.dp)
        )
    }
}

@Composable
private fun CustomButtonWithTextAbove(
    textSource: MutableState<Int>,
    onClick: () -> Unit,
    buttonColor: ButtonColors,
    buttonWidth: Int
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .heightIn(min = minHeight.dp, max = maxHeight.dp)
    ) {
        Text(
            text = "${textSource.value}",
            fontSize = fontSizeSmall.sp
        )
        Button(
            onClick = onClick,
            colors = buttonColor,
            modifier = Modifier
                .clip(shape = RoundedCornerShape(roundedCorner.dp))
                .width(buttonWidth.dp)
                .padding(paddingValue.dp)
                .fillMaxHeight()
        ) {

        }
    }
}

@Composable
fun CustomButtonsWithTextsAbove(
    textSource0: MutableState<Int>,
    onClick0: () -> Unit,
    textSource1: MutableState<Int>,
    onClick1: () -> Unit,
    buttonWidth: Int
) {
    Row {
        CustomButtonWithTextAbove(
            textSource = textSource0,
            onClick = onClick0,
            buttonColor = ButtonDefaults.buttonColors(Color.Red),
            buttonWidth = buttonWidth
        )
        CustomButtonWithTextAbove(
            textSource = textSource1,
            onClick = onClick1,
            buttonColor = ButtonDefaults.buttonColors(Color.Blue),
            buttonWidth = buttonWidth
        )
    }
}

@Composable
fun TeamNameAndTextBelow(
    teamName: MutableState<String>,
    scoreCounterTeam: MutableState<Int>,
    textColor: Color,
    focusManager: FocusManager,
    textFieldWidth: Int,
    onValueChange: (String) -> Unit
) {
    Column(
        modifier = Modifier.width(textFieldWidth.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
            ),
            value = teamName.value,
            onValueChange = onValueChange,
            textStyle = TextStyle(
                textColor,
                fontSizeLarge.sp,
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
            text = "${scoreCounterTeam.value}",
            fontSize = fontSizeMedium.sp
        )
    }
}

@Composable
fun EndRoundButton() {
    Button(
        onClick = {
            MainActivity.Handler.endRound()
        },
        modifier = Modifier
            .background(Color.Transparent)
            .border(BorderStroke(Constants.borderStrokeWidth.dp, Color.Gray), shape = CircleShape),
        colors = ButtonDefaults.buttonColors(Color.Transparent)
    ) {
        Text(
            text = stringResource(id = R.string.end_round),
            fontSize = fontSizeSmall.sp,
            color = Color.Gray
        )
    }

}

@Composable
fun BackgroundInit(@DrawableRes resId: Int) {
    Image(
        painter = painterResource(id = resId),
        contentDescription = "background",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
}