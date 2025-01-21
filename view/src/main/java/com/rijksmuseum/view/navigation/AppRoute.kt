package com.rijksmuseum.view.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions

sealed class AppRoute(val route: String) {
    data object Collections : AppRoute(route = "collections")
    data object ArtDetails : AppRoute(route = "art_details/{id}")
}

fun NavController.navigateToDetails(id: String, options: NavOptions? = null) {
    navigate(route = "art_details/${id}", options)
}
