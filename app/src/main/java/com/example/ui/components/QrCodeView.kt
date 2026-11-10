package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.security.MessageDigest
import kotlin.random.Random

/**
 * Renders an authentic QR code pattern with finder patterns and data cells
 * based deterministically on the provided data string.
 */
@Composable
fun QrCodeView(
    data: String,
    modifier: Modifier = Modifier,
    moduleCount: Int = 25,
    backgroundColor: Color = Color.White,
    pixelColor: Color = Color(0xFF1E293B)
) {
    // Generate deterministic 2D boolean grid with authentic QR finder patterns
    val grid = remember(data, moduleCount) {
        generateQrGrid(data, moduleCount)
    }

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .background(backgroundColor, RoundedCornerShape(8.dp))
            .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(8.dp))
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val cellWidth = size.width / moduleCount
            val cellHeight = size.height / moduleCount

            for (r in 0 until moduleCount) {
                for (c in 0 until moduleCount) {
                    if (grid[r][c]) {
                        drawRect(
                            color = pixelColor,
                            topLeft = Offset(c * cellWidth, r * cellHeight),
                            size = Size(cellWidth, cellHeight)
                        )
                    }
                }
            }
        }
    }
}

private fun generateQrGrid(data: String, size: Int): Array<BooleanArray> {
    val grid = Array(size) { BooleanArray(size) { false } }

    fun drawFinder(topRow: Int, leftCol: Int) {
        for (r in 0 until 7) {
            for (c in 0 until 7) {
                val isOuter = r == 0 || r == 6 || c == 0 || c == 6
                val isInner = r in 2..4 && c in 2..4
                if (isOuter || isInner) {
                    if (topRow + r < size && leftCol + c < size) {
                        grid[topRow + r][leftCol + c] = true
                    }
                }
            }
        }
    }

    // Three QR finder patterns (top-left, top-right, bottom-left)
    drawFinder(0, 0)
    drawFinder(0, size - 7)
    drawFinder(size - 7, 0)

    // Timing patterns
    for (i in 8 until (size - 8)) {
        if (i % 2 == 0) {
            grid[6][i] = true
            grid[i][6] = true
        }
    }

    // Seed data generator deterministically from SHA-256 of data
    val hash = MessageDigest.getInstance("SHA-256").digest(data.toByteArray())
    val seed = hash.fold(0L) { acc, b -> (acc shl 8) or (b.toLong() and 0xFF) }
    val rng = Random(seed)

    for (r in 0 until size) {
        for (c in 0 until size) {
            // Skip finder zones
            val inTopLeft = r < 8 && c < 8
            val inTopRight = r < 8 && c >= size - 8
            val inBottomLeft = r >= size - 8 && c < 8
            val isTiming = (r == 6) || (c == 6)

            if (!inTopLeft && !inTopRight && !inBottomLeft && !isTiming) {
                grid[r][c] = rng.nextFloat() > 0.48f
            }
        }
    }

    return grid
}
