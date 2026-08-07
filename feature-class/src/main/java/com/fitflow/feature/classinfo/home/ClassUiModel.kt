package com.fitflow.feature.classinfo.home

data class ClassUiModel(
    val id: Long,
    val className: String,
    val classType: String,      // "요가" | "필라테스"
    val instructorName: String,
    val timeRangeText: String,  // 예: "07:00 - 08:00"
    val location: String,
    val reservedCount: Int,
    val capacity: Int
)