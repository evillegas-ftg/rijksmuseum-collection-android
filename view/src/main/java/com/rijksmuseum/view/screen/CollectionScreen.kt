package com.rijksmuseum.view.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest
import com.rijksmuseum.presentation.model.ArtCollectionItemViewData
import com.rijksmuseum.presentation.model.PaginatedArtObjectViewData
import com.rijksmuseum.presentation.viewmodel.CollectionState
import com.rijksmuseum.presentation.viewmodel.CollectionViewModel
import com.rijksmuseum.view.R
import com.rijksmuseum.view.ui.component.ErrorView
import com.rijksmuseum.view.ui.component.LoaderView

@Composable
fun CollectionScreen(
    modifier: Modifier = Modifier,
    viewModel: CollectionViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CollectionScreenContent(
        modifier = modifier,
        uiState = uiState,
        onRetryClicked = viewModel::onRetryClicked,
        onMoreItemsRequested = viewModel::onMoreItemsRequested,
    )
}

@Composable
private fun CollectionScreenContent(
    uiState: CollectionState,
    modifier: Modifier = Modifier,
    onRetryClicked: () -> Unit,
    onMoreItemsRequested: () -> Unit
) {
    Box(modifier = modifier.fillMaxSize()) {
        when {
            uiState.loading -> {
                LoaderView()
            }

            uiState.error != null -> {
                ErrorView(
                    reason = uiState.error.orEmpty(),
                    onRetryClicked = onRetryClicked,
                )
            }

            else -> {
                CollectionListView(
                    data = uiState.data,
                    onMoreItemsRequested = onMoreItemsRequested,
                    onRetryClicked = onRetryClicked,
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun CollectionListView(
    data: PaginatedArtObjectViewData,
    onMoreItemsRequested: () -> Unit,
    onRetryClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
    ) {
        data.items.forEachIndexed { index, group ->
            if (index > 0) {
                item { Spacer(modifier = Modifier.height(24.dp)) }
            }

            stickyHeader {
                ArtistHeaderView(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    title = group.artist
                )
            }

            items(group.items, key = { item -> item.id }) { item ->
                ArtistObjectView(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                    data = item,
                    onClick = {}
                )
            }

            // To do the infinity scroll effect, just 3 items before the end start loading the next page
            if (data.canLoadMore && index < data.items.size - 2) {
                item {
                    LaunchedEffect(Unit) {
                        onMoreItemsRequested()
                    }
                }
            }
        }

        if (data.isLoadingMore) {
            item { LoadingMoreContentView(modifier = Modifier.padding(horizontal = 16.dp)) }
        }
        if (data.loadingMoreError != null) {
            item {
                LoadingMoreErrorView(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    onRetryClicked = onRetryClicked,
                )
            }
        }
    }
}

@Composable
private fun ArtistHeaderView(
    title: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.background)
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = null
        )
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Composable
private fun ArtistObjectView(
    data: ArtCollectionItemViewData,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        onClick = { onClick(data.id) }
    ) {
        ObjectImageView(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
            image = data.image
        )
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = data.title,
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = data.id,
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}

@Composable
private fun ObjectImageView(
    image: String?,
    modifier: Modifier = Modifier,
) {
    SubcomposeAsyncImage(
        modifier = modifier,
        model = ImageRequest.Builder(LocalContext.current).data(image).build(),
        contentDescription = null,
        contentScale = ContentScale.Crop,
    ) {
        when (painter.state) {
            AsyncImagePainter.State.Empty -> {
                // Do nothing
            }

            is AsyncImagePainter.State.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = MaterialTheme.colorScheme.error.copy(alpha = 0.1f))
                        .padding(8.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(R.string.generic_image_not_available),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center,
                    )
                }

            }

            is AsyncImagePainter.State.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator(modifier = Modifier.size(48.dp))
                }
            }

            is AsyncImagePainter.State.Success -> {
                SubcomposeAsyncImageContent(
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
private fun LoadingMoreContentView(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth()
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = stringResource(R.string.loading_more_content),
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Composable
private fun LoadingMoreErrorView(
    modifier: Modifier = Modifier,
    onRetryClicked: () -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth()
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = stringResource(R.string.loading_more_content_error),
            style = MaterialTheme.typography.bodySmall,
        )
        Spacer(modifier = Modifier.width(4.dp))
        TextButton(onClick = onRetryClicked) {
            Text(text = stringResource(R.string.generic_error_retry))
        }

    }
}
