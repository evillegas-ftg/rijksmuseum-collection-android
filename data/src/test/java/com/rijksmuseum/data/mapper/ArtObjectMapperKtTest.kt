package com.rijksmuseum.data.mapper

import com.rijksmuseum.data.model.response.ArtObjectResponse
import com.rijksmuseum.data.model.response.WebImageResponse
import com.rijksmuseum.domain.model.ArtObjectListModel
import org.junit.Assert
import org.junit.Test

class ArtObjectMapperKtTest {

    @Test
    fun `when object is mapped all fields are present`() {
        val response = ArtObjectResponse(
            id = "MyId-1",
            objectNumber = "Object-Number-2",
            title = "The Art",
            principalOrFirstMaker = "Leonardo",
            webImage = WebImageResponse(guid = "Something", url = "http://example.com/image.jpg"),
        )

        val expected = ArtObjectListModel(
            id = "MyId-1",
            objectNumber = "Object-Number-2",
            title = "The Art",
            maker = "Leonardo",
            image = "http://example.com/image.jpg",
        )

        Assert.assertEquals(expected, response.toDomain())
    }

    @Test
    fun `when object is mapped with null webImage then image is not present`() {
        val response = ArtObjectResponse(
            id = "MyId-1",
            objectNumber = "Object-Number-2",
            title = "The Art",
            principalOrFirstMaker = "Leonardo",
            webImage = null,
        )

        val expected = ArtObjectListModel(
            id = "MyId-1",
            objectNumber = "Object-Number-2",
            title = "The Art",
            maker = "Leonardo",
            image = null,
        )

        Assert.assertEquals(expected, response.toDomain())
    }

    @Test
    fun `when object is mapped with null guid then image is not present`() {
        val response = ArtObjectResponse(
            id = "MyId-1",
            objectNumber = "Object-Number-2",
            title = "The Art",
            principalOrFirstMaker = "Leonardo",
            webImage = WebImageResponse(guid = null, url = null),
        )

        val expected = ArtObjectListModel(
            id = "MyId-1",
            objectNumber = "Object-Number-2",
            title = "The Art",
            maker = "Leonardo",
            image = null,
        )

        Assert.assertEquals(expected, response.toDomain())
    }

    @Test
    fun `when object is mapped with null url then image is not present`() {
        val response = ArtObjectResponse(
            id = "MyId-1",
            objectNumber = "Object-Number-2",
            title = "The Art",
            principalOrFirstMaker = "Leonardo",
            webImage = WebImageResponse(guid = "something", url = null),
        )

        val expected = ArtObjectListModel(
            id = "MyId-1",
            objectNumber = "Object-Number-2",
            title = "The Art",
            maker = "Leonardo",
            image = null,
        )

        Assert.assertEquals(expected, response.toDomain())
    }
}
