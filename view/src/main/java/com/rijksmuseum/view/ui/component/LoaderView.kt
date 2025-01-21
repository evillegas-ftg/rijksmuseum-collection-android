package com.rijksmuseum.view.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier

@Composable
fun LoaderView(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = BiasAlignment(
            horizontalBias = 0f,
            verticalBias = -0.7f
        )
    ) {
        CircularProgressIndicator()
    }
}
