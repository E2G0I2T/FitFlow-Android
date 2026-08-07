package com.fitflow.feature.classinfo.home

data class HomeUiState(
    val classes: List<ClassUiModel> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)