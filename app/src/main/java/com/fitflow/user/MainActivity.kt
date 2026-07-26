package com.fitflow.user

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fitflow.core_designsystem.theme.FitFlowTheme
import com.fitflow.feature_auth.navigation.AuthRoutes
import com.fitflow.feature_auth.navigation.authNavGraph
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.foundation.layout.padding

private const val ROUTE_HOME = "home" // TODO: feature-class(홈) 완성되면 그쪽 라우트로 교체

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FitFlowTheme {
                val navController = rememberNavController()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = AuthRoutes.LOGIN,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        authNavGraph(
                            onLoginSuccess = {
                                navController.navigate(ROUTE_HOME) {
                                    popUpTo(AuthRoutes.LOGIN) { inclusive = true }
                                }
                            }
                        )
                        composable(ROUTE_HOME) {
                            HomePlaceholder()
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun HomePlaceholder() {
    Text("홈 화면 준비 중")
}