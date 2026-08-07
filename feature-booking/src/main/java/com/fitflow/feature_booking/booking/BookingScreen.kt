package com.fitflow.feature_booking.booking

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun BookingScreen(
    onReservationSuccess: () -> Unit,
    viewModel: BookingViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.isReservationSuccess) {
        if (uiState.isReservationSuccess) {
            onReservationSuccess()
        }
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        when {
            uiState.isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .windowInsetsPadding(WindowInsets.safeDrawing)
                        .padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(text = uiState.className, style = MaterialTheme.typography.headlineMedium)
                    Text(text = uiState.classTypeText, style = MaterialTheme.typography.titleMedium)
                    Text(
                        text = "${uiState.timeRangeText} · ${uiState.instructorName}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(text = uiState.location, style = MaterialTheme.typography.bodyMedium)
                    Text(
                        text = "${uiState.reservedCount}/${uiState.capacity}명 예약",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    uiState.errorMessage?.let { message ->
                        Text(text = message, color = MaterialTheme.colorScheme.error)
                    }

                    val isFull = uiState.reservedCount >= uiState.capacity
                    Button(
                        onClick = viewModel::onReserveClick,
                        enabled = !uiState.isReserving && !isFull,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(if (isFull) "정원 마감" else "예약하기")
                    }
                }
            }
        }
    }
}