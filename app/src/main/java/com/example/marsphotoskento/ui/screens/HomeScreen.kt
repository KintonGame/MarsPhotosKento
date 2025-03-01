package com.example.marsphotoskento.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.marsphotoskento.ui.theme.MarsPhotosKentoTheme

@Composable
fun HomeScreen(
    marsUiState: String,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    ResultScreen(
        photos = marsUiState,
        modifier = modifier.padding(top = contentPadding.calculateTopPadding())
    )
}

@Composable
fun ResultScreen(
    photos: String,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Text(text = photos)
    }
}

@Preview(showBackground = true)
@Composable
fun ResultScreenPreview() {
    MarsPhotosKentoTheme {
        ResultScreen(photos = "Placeholder result text")
    }
}