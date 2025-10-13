package com.example.activityresults

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.activityresults.ui.theme.ActivityResultsTheme

class MainActivity : ComponentActivity() {

    companion object {
        const val RAINBOW_COLOR_NAME = "RAINBOW_COLOR_NAME"
        const val RAINBOW_COLOR = "RAINBOW_COLOR"
        const val TRANSPARENT = 0x00FFFFFF
    }

    private var rainbowColor by mutableStateOf(Color(TRANSPARENT))
    private var colorName by mutableStateOf("")
    private var colorMessage by mutableStateOf("")

    private val startForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { activityResult ->
        val data = activityResult.data
        rainbowColor = Color(
            data?.getLongExtra(RAINBOW_COLOR, TRANSPARENT.toLong()) ?: TRANSPARENT.toLong()
        )
        colorName = data?.getStringExtra(RAINBOW_COLOR_NAME) ?: ""
        colorMessage = getString(R.string.color_chosen_message, colorName)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ActivityResultsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val context = LocalContext.current
                    MainScreen(
                        backgroundColor = rainbowColor,
                        colorMessage = colorMessage,
                        context = context,
                        startForResult = startForResult
                    )
                }
            }
        }
    }
}

@Composable
fun TextWithBackgroundColor(backgroundColor: Color, colorMessage: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .height(50.dp)
            .background(backgroundColor)
            .fillMaxWidth()
    ) {
        Text(
            text = colorMessage,
            fontSize = 22.sp,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .background(backgroundColor)
                .fillMaxWidth()
        )
    }
}

@Composable
fun MainScreen(
    backgroundColor: Color,
    colorMessage: String,
    context: android.content.Context,
    startForResult: androidx.activity.result.ActivityResultLauncher<Intent>
) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            Text(
                text = stringResource(id = R.string.header_text_main),
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp)
            )

            Button(
                onClick = {
                    val intent = Intent(context, ColorPickerActivity::class.java)
                    startForResult.launch(intent)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.submit_button_text)
                )
            }

            TextWithBackgroundColor(backgroundColor, colorMessage)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TextWithBackgroundColorPreview() {
    TextWithBackgroundColor(Color(0xDA, 0xFF, 0x00, 0xFF), "Chosen color appears here")
}