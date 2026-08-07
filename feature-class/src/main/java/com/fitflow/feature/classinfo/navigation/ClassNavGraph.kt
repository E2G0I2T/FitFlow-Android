package com.fitflow.feature.classinfo.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.fitflow.feature.classinfo.home.HomeScreen

fun NavGraphBuilder.classNavGraph(
    onClassClick: (Long) -> Unit
) {
    composable(ClassRoutes.HOME) {
        HomeScreen(onClassClick = onClassClick)
    }
}