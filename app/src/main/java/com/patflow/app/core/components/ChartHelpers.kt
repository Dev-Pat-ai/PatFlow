package com.patflow.app.core.components

import android.graphics.Typeface
import android.text.Layout
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import com.patrykandpatrick.vico.compose.cartesian.marker.rememberDefaultCartesianMarker
import com.patrykandpatrick.vico.compose.common.component.rememberLineComponent
import com.patrykandpatrick.vico.compose.common.component.rememberShapeComponent
import com.patrykandpatrick.vico.compose.common.component.rememberTextComponent
import com.patrykandpatrick.vico.compose.common.of
import com.patrykandpatrick.vico.compose.common.shape.dashed
import com.patrykandpatrick.vico.compose.common.shape.markerCornered
import com.patrykandpatrick.vico.core.cartesian.marker.CartesianMarker
import com.patrykandpatrick.vico.core.cartesian.marker.DefaultCartesianMarker
import com.patrykandpatrick.vico.core.common.Dimensions
import com.patrykandpatrick.vico.core.common.LayeredComponent
import com.patrykandpatrick.vico.core.common.component.Component
import com.patrykandpatrick.vico.core.common.shape.Corner
import com.patrykandpatrick.vico.core.common.shape.Shape

@Composable
fun rememberMarker(
    labelPosition: DefaultCartesianMarker.LabelPosition = DefaultCartesianMarker.LabelPosition.Top,
    showIndicator: Boolean = true,
): CartesianMarker {
    val labelBackgroundShape = Shape.markerCornered(Corner.FullyRounded)
    val labelBackground = rememberShapeComponent(
        color = MaterialTheme.colorScheme.primary,
        shape = labelBackgroundShape,
    )
    val label = rememberTextComponent(
        color = MaterialTheme.colorScheme.onPrimary,
        background = labelBackground,
        padding = Dimensions.of(8.dp, 4.dp),
        typeface = Typeface.DEFAULT_BOLD,
        textAlignment = Layout.Alignment.ALIGN_CENTER,
    )
    
    val indicator: ((Color) -> Component)? = if (showIndicator) {
        { color ->
            val indicatorFrontComponent = shapeComponent(color, Shape.Pill)
            val indicatorRearComponent = shapeComponent(
                Color(ColorUtils.setAlphaComponent(color.toArgb(), 0x33)), 
                Shape.Pill
            )
            LayeredComponent(
                rear = indicatorRearComponent,
                front = indicatorFrontComponent,
                padding = Dimensions.of(5.dp),
            )
        }
    } else {
        null
    }

    val guideline = rememberLineComponent(
        color = Color(ColorUtils.setAlphaComponent(MaterialTheme.colorScheme.onSurface.toArgb(), 0x33)),
        thickness = 2.dp,
        shape = Shape.dashed(Shape.Rectangle, 8.dp, 4.dp),
    )
    return rememberDefaultCartesianMarker(
        label = label,
        labelPosition = labelPosition,
        indicator = indicator,
        guideline = guideline,
    )
}

// Internal Vico helpers that might not be exposed as @Composable
private fun shapeComponent(color: Color, shape: Shape) = 
    com.patrykandpatrick.vico.core.common.component.ShapeComponent(color.toArgb(), shape)
