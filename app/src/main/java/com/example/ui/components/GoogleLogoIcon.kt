package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.unit.dp

/**
 * Official Google 'G' 4-color brand logo drawn cleanly via Vector Canvas
 */
@Composable
fun GoogleLogoIcon(modifier: Modifier = Modifier.size(20.dp)) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        val red = Color(0xFFEA4335)
        val yellow = Color(0xFFFBBC05)
        val green = Color(0xFF34A853)
        val blue = Color(0xFF4285F4)

        // Blue horizontal bar and right arc
        val bluePath = Path().apply {
            moveTo(w * 0.98f, h * 0.50f)
            lineTo(w * 0.50f, h * 0.50f)
            lineTo(w * 0.50f, h * 0.69f)
            lineTo(w * 0.78f, h * 0.69f)
            cubicTo(w * 0.74f, h * 0.81f, w * 0.64f, h * 0.89f, w * 0.50f, h * 0.89f)
            lineTo(w * 0.50f, h * 1.00f)
            cubicTo(w * 0.78f, h * 1.00f, w * 0.98f, h * 0.79f, w * 0.98f, h * 0.50f)
            close()
        }
        drawPath(bluePath, color = blue, style = Fill)

        // Green bottom arc
        val greenPath = Path().apply {
            moveTo(w * 0.50f, h * 1.00f)
            lineTo(w * 0.50f, h * 0.81f)
            cubicTo(w * 0.33f, h * 0.81f, w * 0.20f, h * 0.68f, w * 0.19f, h * 0.51f)
            lineTo(w * 0.04f, h * 0.63f)
            cubicTo(w * 0.13f, h * 0.85f, w * 0.30f, h * 1.00f, w * 0.50f, h * 1.00f)
            close()
        }
        drawPath(greenPath, color = green, style = Fill)

        // Yellow left arc
        val yellowPath = Path().apply {
            moveTo(w * 0.19f, h * 0.51f)
            cubicTo(w * 0.19f, h * 0.45f, w * 0.20f, h * 0.39f, w * 0.22f, h * 0.34f)
            lineTo(w * 0.06f, h * 0.22f)
            cubicTo(w * 0.02f, h * 0.31f, w * 0.00f, h * 0.40f, w * 0.00f, h * 0.50f)
            cubicTo(w * 0.00f, h * 0.55f, w * 0.02f, h * 0.60f, w * 0.04f, h * 0.63f)
            lineTo(w * 0.19f, h * 0.51f)
            close()
        }
        drawPath(yellowPath, color = yellow, style = Fill)

        // Red top arc
        val redPath = Path().apply {
            moveTo(w * 0.22f, h * 0.34f)
            cubicTo(w * 0.28f, h * 0.23f, w * 0.38f, h * 0.19f, w * 0.50f, h * 0.19f)
            cubicTo(w * 0.63f, h * 0.19f, w * 0.72f, h * 0.24f, w * 0.77f, h * 0.29f)
            lineTo(w * 0.91f, h * 0.15f)
            cubicTo(w * 0.80f, h * 0.05f, w * 0.66f, h * 0.00f, w * 0.50f, h * 0.00f)
            cubicTo(w * 0.30f, h * 0.00f, w * 0.13f, h * 0.15f, w * 0.06f, h * 0.22f)
            lineTo(w * 0.22f, h * 0.34f)
            close()
        }
        drawPath(redPath, color = red, style = Fill)
    }
}
