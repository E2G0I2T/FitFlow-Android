package com.fitflow.feature_booking.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.fitflow.feature_booking.booking.BookingScreen

fun NavGraphBuilder.bookingNavGraph(
    onReservationSuccess: () -> Unit
) {
    composable(
        route = BookingRoutes.PATTERN,
        arguments = listOf(navArgument("classId") { type = NavType.LongType })
    ) {
        BookingScreen(onReservationSuccess = onReservationSuccess)
    }
}