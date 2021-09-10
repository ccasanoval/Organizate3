package com.cesoft.organizate3.ui.composables

import android.view.MotionEvent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.SemanticsPropertyKey
import androidx.compose.ui.semantics.SemanticsPropertyReceiver
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.cesoft.organizate3.R
import com.cesoft.organizate3.ui.composables.RatingBarUtils.stepSized
import kotlin.math.roundToInt

sealed class StepSize {
    object ONE : StepSize()
    object HALF : StepSize()
}

sealed class RatingBarStyle {
    object Normal : RatingBarStyle()
    object HighLighted : RatingBarStyle()
}

//For ui testing
val StarRatingKey = SemanticsPropertyKey<Float>("StarRating")
var SemanticsPropertyReceiver.starRating by StarRatingKey


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RatingBarCompo(
    modifier: Modifier = Modifier,
    value: Float = 0f,
    numStars: Int = 5,
    size: Dp = 26.dp,
    padding: Dp = 2.dp,
    isIndicator: Boolean = false,
    activeColor: Color = Color(0xffffd740),
    inactiveColor: Color = Color(0x55ffecb3),
    stepSize: StepSize = StepSize.ONE,
    hideInactiveStars: Boolean = false,
    ratingBarStyle: RatingBarStyle = RatingBarStyle.Normal,
    @StringRes label: Int = R.string.field_priority,
    onValueChange: (Float) -> Unit,
    onRatingChanged: (Float) -> Unit
) {
    android.util.Log.e("Rat", "--------------- value = $value")

    var rowSize by remember { mutableStateOf(Size.Zero) }
    var changedValue by remember { mutableStateOf(0f) }

    Row(modifier = modifier
        .onSizeChanged { rowSize = it.toSize() }
        .pointerInteropFilter {
            if(isIndicator || hideInactiveStars) return@pointerInteropFilter false
            when(it.action) {
                MotionEvent.ACTION_DOWN -> { //handling when single click happens
                    val calculatedStars = RatingBarUtils.calculateStars(
                        it.x, rowSize.width, numStars, padding.value.toInt()
                    )
                    val newValue = calculatedStars.stepSized(stepSize)
                    onValueChange(newValue)
                    onRatingChanged(changedValue)
                }
                MotionEvent.ACTION_MOVE -> { //handling while dragging event
                    val x1 = it.x.coerceIn(0f, rowSize.width)
                    val calculatedStars = RatingBarUtils.calculateStars(
                        x1, rowSize.width, numStars, padding.value.toInt()
                    )
                    val newValue = calculatedStars.stepSized(stepSize)
                    onValueChange(newValue)
                    changedValue = newValue
                }
                MotionEvent.ACTION_UP -> { //when the click or drag is released
                    onRatingChanged(changedValue)
                }
            }
            true
        }) {
        Text(text=stringResource(label), modifier = Modifier.padding(8.dp, 4.dp))
        ComposeStars(
            value, numStars, size, padding, activeColor,
            inactiveColor, hideInactiveStars, ratingBarStyle
        )
    }
}

@Composable
fun ComposeStars(
    value: Float,
    numStars: Int,
    size: Dp,
    padding: Dp,
    activeColor: Color,
    inactiveColor: Color,
    hideInactiveStars: Boolean,
    ratingBarStyle: RatingBarStyle
) {

    val ratingPerStar = 1f
    var remainingRating = value

    Row(modifier = Modifier.semantics { starRating = value }) {
        for (i in 1..numStars) {
            val starRating = when {
                remainingRating == 0f -> {
                    0f
                }
                remainingRating >= ratingPerStar -> {
                    remainingRating -= ratingPerStar
                    1f
                }
                else -> {
                    val fraction = remainingRating / ratingPerStar
                    remainingRating = 0f
                    fraction
                }
            }
            if (hideInactiveStars && starRating == 0.0f)
                break
            RatingStar(
                fraction = starRating,
                modifier = Modifier
                    .padding(
                        start = if (i > 1) padding else 0.dp,
                        end = if (i < numStars) padding else 0.dp
                    )
                    .size(size = size)
                    .testTag("RatingStar"),
                activeColor,
                inactiveColor,
                ratingBarStyle
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun RatingBarPreview() {
    var rating by remember { mutableStateOf(3.3f) }
    RatingBarCompo(value = rating, onValueChange = {
        rating = it
    }) {
        android.util.Log.e("TAG", "-----------RatingBarPreview: $it")
    }
}

object RatingBarUtils {

    fun calculateStars(
        draggedWidth: Float, width: Float,
        numStars: Int, padding: Int
    ): Float {
        var overAllComposeWidth = width
        val spacerWidth = numStars * (2 * padding)

        //removing padding's width
        overAllComposeWidth -= spacerWidth
        return if (draggedWidth != 0f)
            ((draggedWidth / overAllComposeWidth) * numStars)
        else 0f
    }

    fun Float.stepSized(stepSize: StepSize): Float {
        return if (stepSize is StepSize.ONE)
            this.roundToInt().toFloat()
        else {
            var value = this.toInt().toFloat()
            if (this < value.plus(0.5)) {
                if(this==0f)
                    return 0f
                value = value.plus(0.5).toFloat()
                value
            } else
                this.roundToInt().toFloat()
        }
    }
}
