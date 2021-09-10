package com.cesoft.organizate3.ui.composables

import androidx.annotation.FloatRange
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

private const val strokeWidth = 1f

@Composable
fun RatingStar(
    @FloatRange(from = 0.0, to = 1.0) fraction: Float,
    modifier: Modifier = Modifier,
    activeColor: Color,
    inactiveColor: Color,
    ratingBarStyle: RatingBarStyle) {
    Box(modifier = modifier) {
        FilledStar(fraction,activeColor)
        EmptyStar(fraction,ratingBarStyle,inactiveColor)
    }
}

@Composable
private fun FilledStar(fraction: Float,activeColor: Color) = Canvas(
    modifier = Modifier
        .fillMaxSize()
        .clip(FractionalRectangleShape(0f, fraction))
) {
    val path = Path().addStar(size)

    drawPath(path, color = activeColor, style = Fill) // Filled Star
    drawPath(path, color = activeColor, style = Stroke(width = strokeWidth)) // Border
}

@Composable
private fun EmptyStar(fraction: Float, ratingBarStyle: RatingBarStyle,inactiveColor: Color) = Canvas(
    modifier = Modifier
        .fillMaxSize()
        .clip(FractionalRectangleShape(fraction, 1f))
) {
    val path = Path().addStar(size)
    if(ratingBarStyle is RatingBarStyle.Normal)
        drawPath(path, color = inactiveColor, style = Fill) // Border
    else
        drawPath(path, color = Color.Gray, style = Stroke(width = strokeWidth)) // Border
}

@Preview(showBackground = true)
@Composable
fun EmptyRatingStarPreview() {
    RatingStar(fraction = 0f, modifier = Modifier.size(20.dp),
        Color(0xffffd740),
        Color(0xffffecb3),
        RatingBarStyle.Normal)
}

@Preview(showBackground = true)
@Composable
fun PartialRatingStarPreview() {
    RatingStar(fraction = 0.7f, modifier = Modifier.size(20.dp),
        Color(0xffffd740),
        Color(0xffffecb3),
        RatingBarStyle.Normal)
}

@Preview(showBackground = true)
@Composable
fun FullRatingStarPreview() {
    RatingStar(fraction = 1f, modifier = Modifier.size(20.dp),
        Color(0xffffd740),
        Color(0xffffecb3),
        RatingBarStyle.Normal)
}

@Stable
class FractionalRectangleShape(
    @FloatRange(from = 0.0, to = 1.0) private val startFraction: Float,
    @FloatRange(from = 0.0, to = 1.0) private val endFraction: Float
): Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Rectangle(
            Rect(
            left = (startFraction * size.width).coerceAtMost(size.width - 1f),
            top = 0f,
            right = (endFraction * size.width).coerceAtLeast(1f),
            bottom = size.height
        )
        )
    }

}

fun Path.addStar(
    size: Size,
    spikes: Int = 5,
    @FloatRange(from = 0.0, to = 0.5) outerRadiusFraction: Float = 0.5f,
    @FloatRange(from = 0.0, to = 0.5) innerRadiusFraction: Float = 0.2f
): Path {
    val outerRadius = size.minDimension * outerRadiusFraction
    val innerRadius = size.minDimension * innerRadiusFraction

    val centerX = size.width / 2
    val centerY = size.height / 2

    var totalAngle = PI / 2 // Since we start at the top center, the initial angle will be 90°
    val degreesPerSection = (2 * PI) / spikes

    moveTo(centerX, 0f) // Starts at the top center of the bounds

    var x: Double
    var y: Double

    for (i in 1 ..spikes) {
        // Line going inwards from outerCircle to innerCircle
        totalAngle += degreesPerSection / 2
        x = centerX + cos(totalAngle) * innerRadius
        y = centerY - sin(totalAngle) * innerRadius
        lineTo(x.toFloat(), y.toFloat())


        // Line going outwards from innerCircle to outerCircle
        totalAngle += degreesPerSection / 2
        x = centerX + cos(totalAngle) * outerRadius
        y = centerY - sin(totalAngle) * outerRadius
        lineTo(x.toFloat(), y.toFloat())
    }

    // Path should be closed to ensure it's not an open shape
    close()

    return this
}