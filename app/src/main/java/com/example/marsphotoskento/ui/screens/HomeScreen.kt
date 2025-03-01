package com.example.marsphotoskento.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.marsphotoskento.R
import com.example.marsphotoskento.ui.theme.MarsPhotosKentoTheme

@Composable
fun HomeScreen(
    marsUiState: MarsUiState,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    when (marsUiState) {
        is MarsUiState.Loading -> LoadingScreen(modifier = modifier)
        is MarsUiState.Success -> ResultScreen(
            photos = marsUiState.photos,
            modifier = modifier.padding(top = contentPadding.calculateTopPadding())
        )
        is MarsUiState.Error -> ErrorScreen(modifier = modifier.fillMaxSize())
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(
            modifier = Modifier.then(Modifier.size(64.dp)),
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant
        )
    }
}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.baseline_signal_wifi_connected_no_internet_4_24),
            contentDescription = "",
        )
        Text(text = "Error loading data")
    }
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