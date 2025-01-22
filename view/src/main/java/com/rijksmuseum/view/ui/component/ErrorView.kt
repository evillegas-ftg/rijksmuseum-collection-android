package com.rijksmuseum.view.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.rijksmuseum.view.R
import com.rijksmuseum.view.ui.theme.Spacing

internal const val ErrorViewMessageTestTag = "ErrorViewMessageTestTag"

@Composable
internal fun ErrorView(
    modifier: Modifier = Modifier,
    reason: String,
    onRetryClicked: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(Spacing.medium),
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.generic_error_title),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(Spacing.large))
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .testTag(ErrorViewMessageTestTag),
            text = reason,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(Spacing.extraLarge))
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onRetryClicked,
        ) {
            Text(text = stringResource(R.string.generic_error_retry))
        }
    }
}
