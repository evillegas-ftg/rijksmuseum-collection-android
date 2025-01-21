package com.rijksmuseum.view.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasTextExactly
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performScrollToNode
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.rijksmuseum.presentation.model.ArtCollectionGroupViewData
import com.rijksmuseum.presentation.model.ArtCollectionItemViewData
import com.rijksmuseum.presentation.model.PaginatedArtObjectViewData
import com.rijksmuseum.presentation.viewmodel.CollectionState
import com.rijksmuseum.view.ui.component.ErrorViewMessageTestTag
import com.rijksmuseum.view.ui.component.LoaderViewTestTag
import com.rijksmuseum.view.ui.theme.RijksmuseumAndroidTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CollectionScreenKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun whenLoadingInitialPage_thenLoaderIsDisplayed() {
        val state = CollectionState(
            loading = true,
            data = PaginatedArtObjectViewData(isLoadingMore = false, canLoadMore = false, items = emptyList())
        )

        composeTestRule.setContent {
            RijksmuseumAndroidTheme {
                TestScreen(uiState = state)
            }
        }

        composeTestRule.onNodeWithTag(LoaderViewTestTag)
            .assertIsDisplayed()
    }

    @Test
    fun whenLoadingInitialPageFail_thenErrorIsDisplayed() {
        val state = CollectionState(
            loading = false,
            data = PaginatedArtObjectViewData(isLoadingMore = false, canLoadMore = false, items = emptyList()),
            error = "Network Error",
        )

        composeTestRule.setContent {
            RijksmuseumAndroidTheme {
                TestScreen(uiState = state)
            }
        }

        composeTestRule.onNodeWithTag(ErrorViewMessageTestTag)
            .assertTextEquals("Network Error", includeEditableText = false)
    }

    @Test
    fun whenListReceived_thenAllElementsAreDisplayed() {
        val state = CollectionState(
            loading = false,
            data = PaginatedArtObjectViewData(
                isLoadingMore = false,
                canLoadMore = false,
                items = listOf(
                    ArtCollectionGroupViewData(
                        artist = "Artist 1",
                        items = listOf(
                            ArtCollectionItemViewData(title = "Artist 1 - Item 1", id = "a1i1", image = null),
                            ArtCollectionItemViewData(title = "Artist 1 - Item 2", id = "a1i2", image = null),
                            ArtCollectionItemViewData(title = "Artist 1 - Item 3", id = "a1i3", image = null),
                            ArtCollectionItemViewData(title = "Artist 1 - Item 4", id = "a1i4", image = null)
                        )
                    ),
                    ArtCollectionGroupViewData(
                        artist = "Artist 2",
                        items = listOf(
                            ArtCollectionItemViewData(title = "Artist 2 - Item 1", id = "a2i1", image = null),
                            ArtCollectionItemViewData(title = "Artist 2 - Item 2", id = "a2i2", image = null),
                            ArtCollectionItemViewData(title = "Artist 2 - Item 3", id = "a2i3", image = null),
                        )
                    ),
                    ArtCollectionGroupViewData(
                        artist = "Artist 3",
                        items = listOf(
                            ArtCollectionItemViewData(title = "Artist 3 - Item 1", id = "a3i1", image = null),
                            ArtCollectionItemViewData(title = "Artist 3 - Item 2", id = "a3i2", image = null),
                            ArtCollectionItemViewData(title = "Artist 3 - Item 3", id = "a3i3", image = null),
                            ArtCollectionItemViewData(title = "Artist 3 - Item 4", id = "a3i4", image = null),
                            ArtCollectionItemViewData(title = "Artist 3 - Item 5", id = "a3i5", image = null),
                            ArtCollectionItemViewData(title = "Artist 3 - Item 6", id = "a3i6", image = null),
                            ArtCollectionItemViewData(title = "Artist 3 - Item 7", id = "a3i7", image = null),
                            ArtCollectionItemViewData(title = "Artist 3 - Item 8", id = "a3i8", image = null),
                            ArtCollectionItemViewData(title = "Artist 3 - Item 9", id = "a3i9", image = null),
                        )
                    )
                )
            ),
            error = null,
        )

        composeTestRule.setContent {
            RijksmuseumAndroidTheme {
                TestScreen(uiState = state)
            }
        }

        composeTestRule.verifyArtistHeader(1)
        composeTestRule.verifyArtistItem(1, 1)
        composeTestRule.verifyArtistItem(1, 2)
        composeTestRule.verifyArtistItem(1, 3)
        composeTestRule.verifyArtistItem(1, 4)
        composeTestRule.verifyArtistHeader(2)
        composeTestRule.verifyArtistItem(2, 1)
        composeTestRule.verifyArtistItem(2, 2)
        composeTestRule.verifyArtistItem(2, 3)
        composeTestRule.verifyArtistHeader(3)
        composeTestRule.verifyArtistItem(3, 1)
        composeTestRule.verifyArtistItem(3, 2)
        composeTestRule.verifyArtistItem(3, 3)
        composeTestRule.verifyArtistItem(3, 4)
        composeTestRule.verifyArtistItem(3, 5)
        composeTestRule.verifyArtistItem(3, 6)
        composeTestRule.verifyArtistItem(3, 7)
        composeTestRule.verifyArtistItem(3, 8)
        composeTestRule.verifyArtistItem(3, 9)
    }

    private fun ComposeTestRule.verifyArtistHeader(index: Int) {
        onNodeWithTag(CollectionListTestTag)
            .performScrollToNode(hasTextExactly("Artist ${index}"))
            .assertIsDisplayed()
    }

    private fun ComposeTestRule.verifyArtistItem(index: Int, item: Int) {
        onNodeWithTag(CollectionListTestTag, useUnmergedTree = true)
            .performScrollToNode(hasTextExactly("Artist ${index} - Item ${item}"))
            .assertIsDisplayed()
    }

    @Composable
    private fun TestScreen(
        uiState: CollectionState,
    ) {
        CollectionScreenContent(
            uiState = uiState,
            onRetryClicked = {},
            onMoreItemsRequested = {},
            onArtClicked = {},
        )
    }
}
