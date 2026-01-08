package com.example.myfirebase.view.controllNavigasi

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myfirebase.view.DetailSiswaScreen
import com.example.myfirebase.view.EditSiswaScreen
import com.example.myfirebase.view.EntrySiswaScreen
import com.example.myfirebase.view.HomeScreen
import com.example.myfirebase.view.route.DestinasiDetail
import com.example.myfirebase.view.route.DestinasiEdit
import com.example.myfirebase.view.route.DestinasiEntry
import com.example.myfirebase.view.route.DestinasiHome

@Composable
fun DataSiswaApp(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    HostNavigasi(
        navController = navController,
        modifier = modifier
    )
}

@Composable
fun HostNavigasi(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route,
        modifier = modifier
    ) {

        // ================= HOME =================
        composable(DestinasiHome.route) {
            HomeScreen(
                navigateToItemEntry = {
                    navController.navigate(DestinasiEntry.route)
                },
                navigateToItemUpdate = { id ->
                    navController.navigate("${DestinasiDetail.route}/$id")
                }
            )
        }

        // ================= ENTRY =================
        composable(DestinasiEntry.route) {
            EntrySiswaScreen(
                navigateBack = {
                    navController.navigate(DestinasiHome.route)
                }
            )
        }

        // ================= DETAIL =================
        composable(
            route = DestinasiDetail.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiDetail.itemIdArg) {
                    type = NavType.StringType
                }
            )
        ) {
            DetailSiswaScreen(
                navigateToEditItem = { id ->
                    navController.navigate("${DestinasiEdit.route}/$id")
                },
                navigateBack = {
                    navController.navigate(DestinasiHome.route)
                }
            )
        }

        // ================= EDIT =================
        composable(
            route = DestinasiEdit.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiEdit.itemIdArg) {
                    type = NavType.StringType
                }
            )
        ) {
            EditSiswaScreen(
                navigateBack = {
                    navController.navigate(DestinasiHome.route)
                },
                onNavigateUp = {
                    navController.navigateUp()
                }
            )
        }
    }
}