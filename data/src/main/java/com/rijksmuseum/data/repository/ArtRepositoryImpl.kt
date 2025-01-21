package com.rijksmuseum.data.repository

import com.rijksmuseum.data.datasource.RemoteDataSource
import com.rijksmuseum.data.mapper.toDomain
import com.rijksmuseum.domain.model.ArtDetailModel
import com.rijksmuseum.domain.model.PaginatedArtListModel
import com.rijksmuseum.domain.repository.ArtRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.math.min

class ArtRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
) : ArtRepository {

    companion object {
        private const val COLLECTION_DEFAULT_PAGE_SIZE = 50
        private const val COLLECTION_MAX_ITEMS = 10000 // From API note
    }

    override suspend fun getCollection(page: Int): Result<PaginatedArtListModel> {
        return withContext(Dispatchers.IO) {
            try {
                check(page * COLLECTION_DEFAULT_PAGE_SIZE <= COLLECTION_MAX_ITEMS)

                val pageContent = remoteDataSource.getCollection(
                    page = page,
                    pageSize = COLLECTION_DEFAULT_PAGE_SIZE
                )
                Result.success(
                    PaginatedArtListModel(
                        items = pageContent.artObjects.map { it.toDomain() },
                        hasMoreItems = (page.inc() * COLLECTION_DEFAULT_PAGE_SIZE < min(
                            COLLECTION_MAX_ITEMS,
                            pageContent.count
                        )),
                        nextPageIndex = page.inc(),
                    )
                )
            } catch (reason: Throwable) {
                Result.failure(reason)
            }
        }
    }

    override suspend fun getArtDetails(id: String): Result<ArtDetailModel> {
        return withContext(Dispatchers.IO) {
            try {
                val response = remoteDataSource.getCollectionDetail(id)
                Result.success(response.artObject.toDomain())
            } catch (reason: Throwable) {
                Result.failure(reason)
            }
        }
    }
}
