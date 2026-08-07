package com.fitflow.user

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.fitflow.core_designsystem.theme.FitFlowTheme
import com.fitflow.feature.classinfo.navigation.ClassRoutes
import com.fitflow.feature.classinfo.navigation.classNavGraph
import com.fitflow.feature_auth.navigation.AuthRoutes
import com.fitflow.feature_auth.navigation.authNavGraph
import com.fitflow.feature_booking.navigation.BookingRoutes
import com.fitflow.feature_booking.navigation.bookingNavGraph
import com.fitflow.feature_mypage.navigation.MyPageRoutes
import com.fitflow.feature_mypage.navigation.myPageNavGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FitFlowTheme {
                val navController = rememberNavController()
                val backStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = backStackEntry?.destination?.route
                val showBottomBar = currentRoute == ClassRoutes.HOME || currentRoute == MyPageRoutes.MYPAGE

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    contentWindowInsets = WindowInsets(0, 0, 0, 0),
                    bottomBar = {
                        if (showBottomBar) {
                            NavigationBar {
                                NavigationBarItem(
                                    selected = currentRoute == ClassRoutes.HOME,
                                    onClick = {
                                        navController.navigate(ClassRoutes.HOME) { launchSingleTop = true }
                                    },
                                    icon = { Text("🏠") },
                                    label = { Text("홈") }
                                )
                                NavigationBarItem(
                                    selected = currentRoute == MyPageRoutes.MYPAGE,
                                    onClick = {
                                        navController.navigate(MyPageRoutes.MYPAGE) { launchSingleTop = true }
                                    },
                                    icon = { Text("👤") },
                                    label = { Text("마이페이지") }
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = AuthRoutes.LOGIN,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        authNavGraph(
                            onLoginSuccess = {
                                navController.navigate(ClassRoutes.HOME) {
                                    popUpTo(AuthRoutes.LOGIN) { inclusive = true }
                                }
                            }
                        )
                        classNavGraph(
                            onClassClick = { classId ->
                                navController.navigate(BookingRoutes.booking(classId))
                            }
                        )
                        bookingNavGraph(
                            onReservationSuccess = { navController.popBackStack() }
                        )
                        myPageNavGraph()
                    }
                }
            }
        }
    }
}