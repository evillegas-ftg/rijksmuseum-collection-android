package com.rijksmuseum.view.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.rijksmuseum.view.screen.ArtDetailsScreen
import com.rijksmuseum.view.screen.CollectionScreen

@Composable
fun RijksmuseumNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = AppRoute.Collections.route
    ) {
        composable(route = AppRoute.Collections.route) {
            CollectionScreen(
                onNavigateToDetails = { id ->
                    navController.navigateToDetails(id)
                }
            )
        }

        composable(
            route = AppRoute.ArtDetails.route,
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) {
            ArtDetailsScreen(
                onNavigateBack = { navController.navigateUp() }
            )
        }
    }
}
