package com.fitflow.core_designsystem.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightColors = lightColorScheme(
    primary = SageDark,
    onPrimary = Color.White,
    primaryContainer = SageLight,
    onPrimaryContainer = OnCreamText,
    secondary = SageMid,
    onSecondary = Color.White,
    secondaryContainer = SageContainerLight,
    onSecondaryContainer = OnCreamText,
    tertiary = TerracottaAccent,
    background = Cream,
    onBackground = OnCreamText,
    surface = Cream,
    onSurface = OnCreamText,
    surfaceVariant = CreamVariant,
    onSurfaceVariant = OnCreamVariantText,
    outline = OutlineLight,
    error = ErrorLight,
    onError = OnErrorLight,
    errorContainer = ErrorContainerLight,
    onErrorContainer = OnErrorContainerLight
)

private val DarkColors = darkColorScheme(
    primary = SageLight,
    onPrimary = OnCreamText,
    primaryContainer = SageContainerDark,
    onPrimaryContainer = OnSageContainerDark,
    secondary = SageMid,
    onSecondary = DarkBg,
    secondaryContainer = SageContainerDark,
    onSecondaryContainer = OnSageContainerDark,
    tertiary = TerracottaAccentDark,
    background = DarkBg,
    onBackground = OnDarkBg,
    surface = DarkBg,
    onSurface = OnDarkBg,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = OnDarkSurfaceVariant,
    outline = OutlineDark,
    error = ErrorDark,
    onError = OnErrorDark,
    errorContainer = ErrorContainerDark,
    onErrorContainer = OnErrorContainerDark
)

@Composable
fun FitFlowTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Material You(기기별 배경화면 색 적용)는 기본 off — 브랜드 아이덴티티(세이지그린)를
    // 기기마다 다르게 흔들리지 않고 일관되게 유지하기 위함입니다. 필요하면 true로 켤 수 있어요.
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColors
        else -> LightColors
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = FitFlowTypography,
        content = content
    )
}