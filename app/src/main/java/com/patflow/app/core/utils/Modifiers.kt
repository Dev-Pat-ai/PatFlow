package com.patflow.app.core.utils

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

/**
 * Design System §8.2 — Shimmer effect for skeleton loading states.
 */
fun Modifier.shimmer(
    durationMillis: Int = 1000,
    baseColor: Color? = null,
    highlightColor: Color? = null
): Modifier = composed {
    val transition = rememberInfiniteTransition(label = "shimmer")
    
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = durationMillis,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmerTranslation"
    )

    val shimmerColors = listOf(
        (baseColor ?: MaterialTheme.colorScheme.surfaceVariant).copy(alpha = 0.6f),
        (highlightColor ?: MaterialTheme.colorScheme.surface).copy(alpha = 0.2f),
        (baseColor ?: MaterialTheme.colorScheme.surfaceVariant).copy(alpha = 0.6f),
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset.Zero,
        end = Offset(x = translateAnim.value, y = translateAnim.value)
    )

    background(brush)
}

/**
 * A clickable modifier that doesn't show a ripple effect.
 * Useful for transient UI elements or when a custom interaction is needed.
 */
fun Modifier.noRippleClickable(
    onClick: () -> Unit
): Modifier = composed {
    clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = null,
        onClick = onClick
    )
}
