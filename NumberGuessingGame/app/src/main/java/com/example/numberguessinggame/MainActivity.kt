package com.example.numberguessinggame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.numberguessinggame.ui.theme.NumberGuessingGameTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    private var answer = Random.nextInt(1000)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NumberGuessingGameTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    GameScreen(answer, ::restartGame)
                }
            }
        }
    }

    private fun restartGame() {
        answer = Random.nextInt(1000)
        recreate()
    }

}

@Composable
fun GameScreen(answer: Int, onRestart: () -> Unit){

    var counter by remember {
        mutableStateOf(0)
    }
    var playAgainButton by remember {
        mutableStateOf(false)
    }
    var showCounter by remember {
        mutableStateOf(false)
    }
    var amountInput by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var minNum by remember {
        mutableStateOf(1)
    }
    var maxNum by remember {
        mutableStateOf(1000)
    }
    Column(
        modifier = Modifier.padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Number Guessing Game",
            fontSize = 30.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontWeight = FontWeight.Bold,
            color = Color(95,0,160)
        )
        Spacer(Modifier.height(10.dp))
        Text(
            text = "Try to guess the number I'm thinking of form 1 - 1000",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = "Current range form $minNum - $maxNum",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 15.sp,

        )
        GuessingNumberField(value = amountInput,
            onValueChange = { amountInput = it },)
        if(showCounter){
            if(counter > 1){
                Text(
                    text = "Try $counter times",
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    fontSize = 15.sp,

                    )
            }else{
                Text(
                    text = "Try $counter time",
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    fontSize = 15.sp,

                    )
            }

        }
        if(playAgainButton){
            Button(
                onClick = {
                    onRestart()
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)

            ) {
                Text("Play Again")
            }
        }else{
            Button(
                onClick = {
                    val tip = playingGame(amountInput.toIntOrNull() ?: 0, answer)
                    result = tip
                    showCounter = true
                    counter += 1
                    if(result.compareTo("You are correct") == 0){
                        playAgainButton = true
                    }else if(result.compareTo("Hint: number is lower than your guess") == 0){
                        maxNum = amountInput.toIntOrNull() ?: 0

                    }else{
                        minNum = amountInput.toIntOrNull() ?: 0

                    }
                    amountInput = ""

                },
                modifier = Modifier.align(Alignment.CenterHorizontally)

            ) {
                Text("Submit")
            }
        }
        Spacer(Modifier.height(16.dp))
        Text(
            text = result,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

    }


}

@Composable
fun GuessingNumberField(
    value: String,
    onValueChange: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextField(
            label = { Text("your guess") },
            modifier = Modifier.weight(1f),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            value = value,
            onValueChange = onValueChange
        )
    }
}

private fun playingGame(
    input:Int = 0,
    answer:Int = 0
): String {
    return if(input > answer){
        "Hint: number is lower than your guess"
    }else if(input < answer){
        "Hint: number is higher than your guess"
    }else{
        "You are correct"
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NumberGuessingGameTheme {

    }
}