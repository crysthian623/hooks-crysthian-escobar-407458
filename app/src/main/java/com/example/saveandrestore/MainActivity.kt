package com.example.saveandrestore

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.saveandrestore.ui.theme.SaveAndRestoreTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {

    companion object {
        private const val TAG = "MainActivity"
        private const val RANDOM_NUMBER = "RANDOM_NUMBER"
    }

    private var randomNumber by mutableStateOf(0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Restaurar el estado si existe
        if (savedInstanceState != null) {
            randomNumber = savedInstanceState.getInt(RANDOM_NUMBER, 0)
            Log.d(TAG, "Estado restaurado: $randomNumber")
        }

        setContent {
            SaveAndRestoreTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RandomNumberScreen(
                        randomNumber = randomNumber,
                        onGenerateRandomNumber = {
                            randomNumber = generateRandomNumber()
                        }
                    )
                }
            }
        }
        Log.d(TAG, "onCreate - Número actual: $randomNumber")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(RANDOM_NUMBER, randomNumber)
        Log.d(TAG, "onSaveInstanceState - Guardando: $randomNumber")
    }

    private fun generateRandomNumber(): Int {
        return Random.nextInt(0, 1000)
    }
}

@Composable
fun RandomNumberScreen(
    randomNumber: Int,
    onGenerateRandomNumber: () -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = onGenerateRandomNumber,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.generate_random_number),
                fontSize = 18.sp
            )
        }

        Text(
            text = stringResource(
                id = R.string.random_number_message,
                randomNumber
            ),
            fontSize = 18.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RandomNumberScreenPreview() {
    SaveAndRestoreTheme {
        RandomNumberScreen(
            randomNumber = 42,
            onGenerateRandomNumber = {}
        )
    }
}