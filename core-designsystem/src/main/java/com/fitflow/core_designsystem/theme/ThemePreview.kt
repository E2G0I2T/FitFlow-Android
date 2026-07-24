package com.fitflow.core_designsystem.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(name = "Light", showBackground = true)
@Composable
private fun FitFlowThemeLightPreview() {
    FitFlowTheme(darkTheme = false) {
        PreviewContent()
    }
}

@Preview(name = "Dark", showBackground = true)
@Composable
private fun FitFlowThemeDarkPreview() {
    FitFlowTheme(darkTheme = true) {
        PreviewContent()
    }
}

@Composable
private fun PreviewContent() {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(text = "아침 요가 클래스", style = MaterialTheme.typography.titleLarge)
            Text(
                text = "07:00 - 08:00 · 김지은 강사",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Button(onClick = {}) {
                Text("예약하기")
            }
        }
    }
}