package com.patflow.app.core.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.patflow.app.core.theme.PatFlowShapes
import com.patflow.app.core.theme.PatFlowTheme
import com.patflow.app.core.utils.shimmer

/**
 * Design System §8.2 — Loading States (Skeletons).
 */
@Composable
fun SkeletonBox(
    modifier: Modifier = Modifier,
    width: Dp = Dp.Unspecified,
    height: Dp = Dp.Unspecified,
    shape: Shape = PatFlowShapes.md
) {
    val boxModifier = modifier
        .then(if (width != Dp.Unspecified) Modifier.size(width, height) else Modifier.fillMaxWidth().height(height))
        .clip(shape)
        .shimmer()
    
    Box(modifier = boxModifier)
}

/**
 * A generic full-screen loading state using skeletons.
 */
@Composable
fun LoadingState(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        repeat(5) {
            SkeletonBox(height = 80.dp, shape = PatFlowShapes.lg)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoadingStatePreview() {
    PatFlowTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Skeleton for a title
            SkeletonBox(width = 150.dp, height = 24.dp)
            
            // Skeleton for a card
            SkeletonBox(height = 80.dp, shape = PatFlowShapes.lg)
            
            // Skeleton for a list item with icon
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SkeletonBox(width = 40.dp, height = 40.dp, shape = CircleShape)
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    SkeletonBox(width = 200.dp, height = 16.dp)
                    SkeletonBox(width = 100.dp, height = 12.dp)
                }
            }
        }
    }
}
