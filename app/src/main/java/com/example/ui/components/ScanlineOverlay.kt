package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun ScanlineOverlay(
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val lineSpacing = 4f
        val numLines = (size.height / lineSpacing).toInt()
        val scanlineColor = Color(0x1A000000)

        for (i in 0 until numLines) {
            val y = i * lineSpacing
            drawLine(
                color = scanlineColor,
                start = Offset(0f, y),
                end = Offset(size.width, y),
                strokeWidth = 1f
            )
        }

        // CRT Vignette
        drawRect(
            brush = Brush.radialGradient(
                colors = listOf(Color.Transparent, Color(0x660B0814)),
                center = Offset(size.width / 2f, size.height / 2f),
                radius = size.width.coerceAtLeast(size.height) * 0.75f
            )
        )
    }
}
