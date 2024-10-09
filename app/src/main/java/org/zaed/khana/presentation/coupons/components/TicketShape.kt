package org.zaed.khana.presentation.coupons.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import org.zaed.khana.presentation.theme.KhanaTheme


class TicketShape(
    private val circleRadius: Dp,
    private val circleCutoffPercentage: Float,
    private val cornerSize: CornerSize
) : Shape {

    override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density): Outline {
        return Outline.Generic(path = getPath(size, density))
    }

    private fun getPath(size: Size, density: Density): Path {
        val roundedRect = RoundRect(size.toRect(), CornerRadius(cornerSize.toPx(size, density)))
        val roundedRectPath = Path().apply { addRoundRect(roundedRect) }
        return Path.combine(operation = PathOperation.Intersect, path1 = roundedRectPath, path2 = getTicketPath(size, density))
    }

    private fun getTicketPath(size: Size, density: Density): Path {
        val middleY = size.height.div(other = circleCutoffPercentage).toFloat()
        val circleRadiusInPx = with(density) { circleRadius.toPx() }
        return Path().apply {
            reset()
            // Ensure we start drawing line at top left
            lineTo(x = 0F, y = 0F)
            // Draw line to middle left
            lineTo(x = 0F, y = middleY)
            // Draw left cutout
            arcTo(
                rect = Rect(
                    left = 0F.minus(circleRadiusInPx),
                    top = middleY.minus(circleRadiusInPx),
                    right = circleRadiusInPx,
                    bottom = middleY.plus(circleRadiusInPx)
                ),
                startAngleDegrees = -90F,
                sweepAngleDegrees = 180F,
                forceMoveTo = false
            )
            // Draw line to bottom left
            lineTo(x = 0F, y = size.height)
            // Draw line to bottom right
            lineTo(x = size.width, y = size.height)
            // Draw line to bottom middle
            lineTo(x = size.width, y = middleY)
            // Draw bottom cutout
            arcTo(
                rect = Rect(
                    left = size.width.minus(circleRadiusInPx),
                    top = middleY.minus(circleRadiusInPx),
                    right = size.width.plus(circleRadiusInPx),
                    bottom = middleY.plus(circleRadiusInPx)
                ),
                startAngleDegrees = 90F,
                sweepAngleDegrees = 180F,
                forceMoveTo = false
            )
            // Draw line to bottom left
            lineTo(x = size.width, y = 0F)
            // Draw line back to top left
            lineTo(x = 0F, y = 0F)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TicketPreview() {
    KhanaTheme {
        Card (
            shape = TicketShape(
                circleRadius = 12.dp,
                circleCutoffPercentage = 2F,
                cornerSize = CornerSize(16.dp)
            ),
            colors = CardDefaults.cardColors(containerColor = Color.Green),
            modifier = Modifier.size(width = 200.dp, height = 100.dp)
        ) {}
    }
}