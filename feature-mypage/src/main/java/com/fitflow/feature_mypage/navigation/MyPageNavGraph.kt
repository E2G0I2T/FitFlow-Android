package com.fitflow.feature_mypage.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.fitflow.feature_mypage.mypage.MyPageScreen

fun NavGraphBuilder.myPageNavGraph() {
    composable(MyPageRoutes.MYPAGE) {
        MyPageScreen()
    }
}