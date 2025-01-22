package com.rijksmuseum.view.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag

internal const val LoaderViewTestTag = "LoaderViewTestTag"

private const val HorizontalAlignment = 0f
private const val VerticalAlignment = -0.7f // 30% from top

@Composable
internal fun LoaderView(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .testTag(LoaderViewTestTag),
        contentAlignment = BiasAlignment(
            horizontalBias = HorizontalAlignment,
            verticalBias = VerticalAlignment,
        )
    ) {
        CircularProgressIndicator()
    }
}
