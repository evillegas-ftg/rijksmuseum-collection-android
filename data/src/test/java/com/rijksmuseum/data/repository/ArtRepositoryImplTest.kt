package com.rijksmuseum.data.repository

import com.rijksmuseum.data.fake.ErrorFakeRemoteDataSource
import com.rijksmuseum.data.fake.FakeGetCollectionRemoteDataSource
import com.rijksmuseum.data.fake.FakeGetDetailsRemoteDataSource
import com.rijksmuseum.data.model.response.ArtDetailObjectResponse
import com.rijksmuseum.data.model.response.ArtObjectResponse
import com.rijksmuseum.data.model.response.CollectionDetailResponse
import com.rijksmuseum.data.model.response.CollectionResponse
import com.rijksmuseum.domain.model.ArtDetailModel
import com.rijksmuseum.domain.model.ArtObjectListModel
import com.rijksmuseum.domain.model.PaginatedArtListModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

@OptIn(ExperimentalCoroutinesApi::class)
class ArtRepositoryImplTest {

    @Test
    fun `when getDetails fails then result is a failure`() = runTest {
        val expected = RuntimeException("Something is wrong")
        val repository = ArtRepositoryImpl(
            remoteDataSource = ErrorFakeRemoteDataSource(expected)
        )

        val result = repository.getArtDetails("id")

        assertEquals(true, result.isFailure)
        assertEquals(expected, result.exceptionOrNull())
    }

    @Test
    fun `when getCollection fails then result is a failure`() = runTest {
        val expected = RuntimeException("Something is wrong")
        val repository = ArtRepositoryImpl(
            remoteDataSource = ErrorFakeRemoteDataSource(expected)
        )

        val result = repository.getCollection(1)

        assertEquals(true, result.isFailure)
        assertEquals(expected, result.exceptionOrNull())
    }

    @Test
    fun `when getCollection succeed with more pages then result is correct`() = runTest {
        val response = CollectionResponse(
            count = 70,
            artObjects = listOf(
                ArtObjectResponse(
                    id = "quaestio",
                    objectNumber = "mattis",
                    title = "sapien",
                    principalOrFirstMaker = "ornare",
                    webImage = null
                ),
                ArtObjectResponse(
                    id = "quem",
                    objectNumber = "commodo",
                    title = "atqui",
                    principalOrFirstMaker = "veri",
                    webImage = null
                )
            ),
        )
        val repository = ArtRepositoryImpl(remoteDataSource = FakeGetCollectionRemoteDataSource(response))
        val result = repository.getCollection(1)

        assertEquals(true, result.isSuccess)

        val expectedModel = PaginatedArtListModel(
            items = listOf(
                ArtObjectListModel(
                    id = "quaestio",
                    objectNumber = "mattis",
                    title = "sapien",
                    maker = "ornare",
                    image = null
                ),
                ArtObjectListModel(
                    id = "quem",
                    objectNumber = "commodo",
                    title = "atqui",
                    maker = "veri",
                    image = null
                ),
            ),
            hasMoreItems = true,
            nextPageIndex = 2,
        )

        assertEquals(expectedModel, result.getOrNull())
    }

    @Test
    fun `when getCollection succeed with no more pages then result is correct`() = runTest {
        val response = CollectionResponse(
            count = 2,
            artObjects = listOf(
                ArtObjectResponse(
                    id = "quaestio",
                    objectNumber = "mattis",
                    title = "sapien",
                    principalOrFirstMaker = "ornare",
                    webImage = null
                ),
                ArtObjectResponse(
                    id = "quem",
                    objectNumber = "commodo",
                    title = "atqui",
                    principalOrFirstMaker = "veri",
                    webImage = null
                )
            ),
        )
        val repository = ArtRepositoryImpl(remoteDataSource = FakeGetCollectionRemoteDataSource(response))
        val result = repository.getCollection(1)

        assertEquals(true, result.isSuccess)

        val expectedModel = PaginatedArtListModel(
            items = listOf(
                ArtObjectListModel(
                    id = "quaestio",
                    objectNumber = "mattis",
                    title = "sapien",
                    maker = "ornare",
                    image = null
                ),
                ArtObjectListModel(
                    id = "quem",
                    objectNumber = "commodo",
                    title = "atqui",
                    maker = "veri",
                    image = null
                ),
            ),
            hasMoreItems = false,
            nextPageIndex = 2,
        )

        assertEquals(expectedModel, result.getOrNull())
    }

    @Test
    fun `when getCollection request more than 10000 items then result is a failure`() = runTest {
        val expected = RuntimeException("Something is wrong")
        val repository = ArtRepositoryImpl(
            remoteDataSource = ErrorFakeRemoteDataSource(expected)
        )

        val result = repository.getCollection(300)

        assertEquals(true, result.isFailure)
    }

    @Test
    fun `when getArtDetail succeed then result is correct`() = runTest {
        val response = CollectionDetailResponse(
            artObject = ArtDetailObjectResponse(
                id = "tritani",
                objectNumber = "quod",
                title = "solum",
                principalOrFirstMaker = "salutatus",
                webImage = null,
                dating = null,
                location = null,
                description = null
            )
        )
        val repository = ArtRepositoryImpl(remoteDataSource = FakeGetDetailsRemoteDataSource(response))
        val result = repository.getArtDetails("")

        assertEquals(true, result.isSuccess)

        val expectedModel = ArtDetailModel(
            id = "tritani",
            objectNumber = "quod",
            title = "solum",
            maker = "salutatus",
            image = null,
            dating = null,
            location = null,
            description = null
        )

        assertEquals(expectedModel, result.getOrNull())
    }
}
