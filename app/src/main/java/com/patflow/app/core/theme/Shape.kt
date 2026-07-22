package com.patflow.app.core.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

/**
 * Design System §5 — Corner Radius (Shape). Named tokens only — never an
 * arbitrary radius typed into a Composable (Governance §17).
 */
object PatFlowShapes {
    val xs = RoundedCornerShape(4.dp)   // status chip inner tag, small badges
    val sm = RoundedCornerShape(8.dp)   // text fields, input containers
    val md = RoundedCornerShape(12.dp)  // compact cards, dialog internals
    val lg = RoundedCornerShape(16.dp)  // default card radius (bill/dashboard/list cards)
    val xl = RoundedCornerShape(28.dp)  // bottom sheets (top corners), large feature cards, extended FAB
    val full = RoundedCornerShape(50)   // chips, standard FAB, avatar, nav active-indicator pill
}

/** M3 Shapes mapping used by MaterialTheme — extraLarge maps to bottom-sheet radius. */
val PatFlowMaterialShapes = Shapes(
    extraSmall = PatFlowShapes.xs,
    small = PatFlowShapes.sm,
    medium = PatFlowShapes.md,
    large = PatFlowShapes.lg,
    extraLarge = PatFlowShapes.xl,
)

/** Design System §4 — Spacing (4dp base grid). Named tokens only. */
object PatFlowSpacing {
    val space1 = 4.dp
    val space2 = 8.dp
    val space3 = 12.dp
    val space4 = 16.dp
    val space5 = 24.dp
    val space6 = 32.dp
    val space8 = 48.dp
}
