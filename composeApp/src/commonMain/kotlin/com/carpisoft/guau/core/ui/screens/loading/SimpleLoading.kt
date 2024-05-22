package com.carpisoft.guau.core.ui.screens.loading

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SimpleLoading() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        CircularProgressIndicator(Modifier.align(Alignment.Center))
    }
}

@Composable
fun CustomLoading(modifier: Modifier) {
    Box(
        modifier = modifier
    ) {
        CircularProgressIndicator(Modifier.align(Alignment.Center))
    }
}
