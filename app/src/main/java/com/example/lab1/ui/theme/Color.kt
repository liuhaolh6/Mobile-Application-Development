package com.example.lab1.ui.theme

import androidx.compose.ui.graphics.Color

/**
 * 实验1 配色。
 * 状态与优先级使用固定语义色，保证"蓝色=进行中、绿色=已完成、红色=高优先级"的认知一致。
 */

// 浅色主题
val PrimaryLight = Color(0xFF1E88E5)
val OnPrimaryLight = Color(0xFFFFFFFF)
val PrimaryContainerLight = Color(0xFFD3E4FF)
val OnPrimaryContainerLight = Color(0xFF001C38)
val SecondaryLight = Color(0xFF565E71)
val BackgroundLight = Color(0xFFF7F9FC)
val SurfaceLight = Color(0xFFFFFFFF)
val OnSurfaceLight = Color(0xFF1A1C1E)
val SurfaceVariantLight = Color(0xFFE1E2EC)
val OnSurfaceVariantLight = Color(0xFF44474F)

// 深色主题
val PrimaryDark = Color(0xFF8FCDFF)
val OnPrimaryDark = Color(0xFF003256)
val PrimaryContainerDark = Color(0xFF00497B)
val OnPrimaryContainerDark = Color(0xFFD3E4FF)
val SecondaryDark = Color(0xFFBEC6DC)
val BackgroundDark = Color(0xFF121212)
val SurfaceDark = Color(0xFF1E1F22)
val OnSurfaceDark = Color(0xFFE3E2E6)
val SurfaceVariantDark = Color(0xFF44474F)
val OnSurfaceVariantDark = Color(0xFFC4C6D0)

/** 状态语义色：进行中（蓝） */
val OngoingBlue = Color(0xFF1E88E5)

/** 状态语义色：已完成（绿） */
val CompletedGreen = Color(0xFF2E7D32)

/** 优先级语义色：高（红） */
val PriorityHigh = Color(0xFFD32F2F)

/** 优先级语义色：中（蓝） */
val PriorityNormal = Color(0xFF1E88E5)

/** 优先级语义色：低（灰） */
val PriorityLow = Color(0xFF9E9E9E)
