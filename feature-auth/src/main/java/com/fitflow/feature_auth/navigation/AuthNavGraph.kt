package com.fitflow.feature_auth.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.fitflow.feature_auth.login.LoginScreen

fun NavGraphBuilder.authNavGraph(
    onLoginSuccess: () -> Unit
) {
    composable(AuthRoutes.LOGIN) {
        LoginScreen(onLoginSuccess = onLoginSuccess)
    }
}