package com.example.activityresults

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.activityresults.ui.theme.ActivityResultsTheme

class ColorPickerActivity : ComponentActivity() {

    companion object {
        const val RED = 0xFFFF0000L
        const val ORANGE = 0xFFFFA500L
        const val YELLOW = 0xFFFFFF00L
        const val GREEN = 0xFF00FF00L
        const val BLUE = 0xFF0000FFL
        const val INDIGO = 0xFF4B0082L
        const val VIOLET = 0xFF8A2BE2L
    }

    private fun setRainbowColor(color: Long, colorName: String) {
        Intent().let { pickedColorIntent ->
            pickedColorIntent.putExtra(MainActivity.RAINBOW_COLOR_NAME, colorName)
            pickedColorIntent.putExtra(MainActivity.RAINBOW_COLOR, color)
            setResult(RESULT_OK, pickedColorIntent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ActivityResultsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ColorPickerScreen(
                        onColorSelected = { color, name ->
                            setRainbowColor(color, name)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun RainbowColor(
    color: Long,
    colorName: String,
    onButtonClick: (Long, String) -> Unit
) {
    Button(
        onClick = { onButtonClick(color, colorName) },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(color)
        ),
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 8.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = colorName,
            color = Color.White,
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun ColorPickerScreen(onColorSelected: (Long, String) -> Unit) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            Text(
                text = stringResource(id = R.string.header_text_picker),
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth()
            )

            RainbowColor(ColorPickerActivity.RED, stringResource(R.string.red), onColorSelected)
            RainbowColor(ColorPickerActivity.ORANGE, stringResource(R.string.orange), onColorSelected)
            RainbowColor(ColorPickerActivity.YELLOW, stringResource(R.string.yellow), onColorSelected)
            RainbowColor(ColorPickerActivity.GREEN, stringResource(R.string.green), onColorSelected)
            RainbowColor(ColorPickerActivity.BLUE, stringResource(R.string.blue), onColorSelected)
            RainbowColor(ColorPickerActivity.INDIGO, stringResource(R.string.indigo), onColorSelected)
            RainbowColor(ColorPickerActivity.VIOLET, stringResource(R.string.violet), onColorSelected)

            Text(
                text = stringResource(id = R.string.footer_text_picker),
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RainbowColorPreview() {
    ActivityResultsTheme {
        ColorPickerScreen(onColorSelected = { _, _ -> })
    }
}