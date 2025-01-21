package com.rijksmuseum.data.mapper

import com.rijksmuseum.data.model.response.ArtDetailObjectResponse
import com.rijksmuseum.data.model.response.DatingResponse
import com.rijksmuseum.data.model.response.WebImageResponse
import com.rijksmuseum.domain.model.ArtDetailModel
import org.junit.Assert
import org.junit.Test


class ArtObjectDetailMapperKtTest {

    @Test
    fun `when object is mapped all fields are present`() {
        val response = ArtDetailObjectResponse(
            id = "MyId-1",
            objectNumber = "Object-Number-2",
            title = "The Art",
            principalOrFirstMaker = "Leonardo",
            webImage = WebImageResponse(guid = "Something", url = "http://example.com/image.jpg"),
            dating = DatingResponse(presentingDate = "Random date"),
            location = "Some location",
            description = "My description"
        )

        val expected = ArtDetailModel(
            id = "MyId-1",
            objectNumber = "Object-Number-2",
            title = "The Art",
            maker = "Leonardo",
            image = "http://example.com/image.jpg",
            location = "Some location",
            description = "My description",
            dating = "Random date"
        )

        Assert.assertEquals(expected, response.toDomain())
    }

    @Test
    fun `when object is mapped with null dating the is not present`() {
        val response = ArtDetailObjectResponse(
            id = "MyId-1",
            objectNumber = "Object-Number-2",
            title = "The Art",
            principalOrFirstMaker = "Leonardo",
            webImage = WebImageResponse(guid = "Something", url = "http://example.com/image.jpg"),
            dating = null,
            location = "Some location",
            description = "My description"
        )

        val expected = ArtDetailModel(
            id = "MyId-1",
            objectNumber = "Object-Number-2",
            title = "The Art",
            maker = "Leonardo",
            image = "http://example.com/image.jpg",
            location = "Some location",
            description = "My description",
            dating = null
        )

        Assert.assertEquals(expected, response.toDomain())
    }

    @Test
    fun `when object is mapped with null webImage then image is not present`() {
        val response = ArtDetailObjectResponse(
            id = "MyId-1",
            objectNumber = "Object-Number-2",
            title = "The Art",
            principalOrFirstMaker = "Leonardo",
            webImage = null,
            dating = DatingResponse(presentingDate = "Random date"),
            location = "Some location",
            description = "My description"
        )

        val expected = ArtDetailModel(
            id = "MyId-1",
            objectNumber = "Object-Number-2",
            title = "The Art",
            maker = "Leonardo",
            image = null,
            location = "Some location",
            description = "My description",
            dating = "Random date"
        )

        Assert.assertEquals(expected, response.toDomain())
    }

    @Test
    fun `when object is mapped with null guid then image is not present`() {
        val response = ArtDetailObjectResponse(
            id = "MyId-1",
            objectNumber = "Object-Number-2",
            title = "The Art",
            principalOrFirstMaker = "Leonardo",
            webImage = WebImageResponse(guid = null, url = null),
            dating = DatingResponse(presentingDate = "Random date"),
            location = "Some location",
            description = "My description"
        )

        val expected = ArtDetailModel(
            id = "MyId-1",
            objectNumber = "Object-Number-2",
            title = "The Art",
            maker = "Leonardo",
            image = null,
            location = "Some location",
            description = "My description",
            dating = "Random date"
        )

        Assert.assertEquals(expected, response.toDomain())
    }

    @Test
    fun `when object is mapped with null url then image is not present`() {
        val response = ArtDetailObjectResponse(
            id = "MyId-1",
            objectNumber = "Object-Number-2",
            title = "The Art",
            principalOrFirstMaker = "Leonardo",
            webImage = WebImageResponse(guid = "Something", url = null),
            dating = DatingResponse(presentingDate = "Random date"),
            location = "Some location",
            description = "My description"
        )

        val expected = ArtDetailModel(
            id = "MyId-1",
            objectNumber = "Object-Number-2",
            title = "The Art",
            maker = "Leonardo",
            image = null,
            location = "Some location",
            description = "My description",
            dating = "Random date"
        )

        Assert.assertEquals(expected, response.toDomain())
    }

    @Test
    fun `when object is mapped with null location then is not present`() {
        val response = ArtDetailObjectResponse(
            id = "MyId-1",
            objectNumber = "Object-Number-2",
            title = "The Art",
            principalOrFirstMaker = "Leonardo",
            webImage = WebImageResponse(guid = "Something", url = null),
            dating = DatingResponse(presentingDate = "Random date"),
            location = null,
            description = "My description"
        )

        val expected = ArtDetailModel(
            id = "MyId-1",
            objectNumber = "Object-Number-2",
            title = "The Art",
            maker = "Leonardo",
            image = null,
            location = null,
            description = "My description",
            dating = "Random date"
        )

        Assert.assertEquals(expected, response.toDomain())
    }

    @Test
    fun `when object is mapped with null description then description is not present`() {
        val response = ArtDetailObjectResponse(
            id = "MyId-1",
            objectNumber = "Object-Number-2",
            title = "The Art",
            principalOrFirstMaker = "Leonardo",
            webImage = WebImageResponse(guid = "Something", url = null),
            dating = DatingResponse(presentingDate = "Random date"),
            location = "Some location",
            description = null
        )

        val expected = ArtDetailModel(
            id = "MyId-1",
            objectNumber = "Object-Number-2",
            title = "The Art",
            maker = "Leonardo",
            image = null,
            location = "Some location",
            description = null,
            dating = "Random date"
        )

        Assert.assertEquals(expected, response.toDomain())
    }
}
