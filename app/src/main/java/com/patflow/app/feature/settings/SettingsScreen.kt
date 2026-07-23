package com.patflow.app.feature.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.BrightnessMedium
import androidx.compose.material.icons.rounded.CalendarToday
import androidx.compose.material.icons.rounded.ColorLens
import androidx.compose.material.icons.rounded.CurrencyExchange
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.Feedback
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Palette
import androidx.compose.material.icons.rounded.TouchApp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.patflow.app.core.components.AppTopBar
import com.patflow.app.core.components.SectionHeader
import com.patflow.app.core.theme.PatFlowSpacing
import com.patflow.app.domain.model.ThemeMode
import com.patflow.app.domain.model.UserPreferences
import java.util.Locale

/**
 * Screen for managing application settings and user profile (Architecture §6).
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val settings by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Settings",
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(bottom = PatFlowSpacing.space6)
        ) {
            settings?.let { prefs ->
                ProfileSection(prefs, viewModel)
                AppearanceSection(prefs, viewModel)
                PreferencesSection(prefs, viewModel)
                NotificationsSection(prefs, viewModel)
                AboutSection()
            }
        }
    }
}

@Composable
private fun ProfileSection(prefs: UserPreferences, viewModel: SettingsViewModel) {
    SectionHeader(title = "User Profile", modifier = Modifier.padding(horizontal = PatFlowSpacing.space4))
    PreferenceCard {
        PreferenceRow(
            title = "Display Name",
            subtitle = prefs.profile.displayName.ifBlank { "Not set" },
            icon = Icons.Rounded.AccountCircle,
            onClick = { /* TODO: Open name edit dialog */ }
        )
        PreferenceRow(
            title = "Monthly Budget",
            subtitle = prefs.profile.monthlyBudget?.let { "₱$it" } ?: "Not set",
            icon = Icons.Rounded.Notifications,
            onClick = { /* TODO: Open budget edit dialog */ }
        )
    }
}

@Composable
private fun AppearanceSection(prefs: UserPreferences, viewModel: SettingsViewModel) {
    SectionHeader(title = "Appearance", modifier = Modifier.padding(horizontal = PatFlowSpacing.space4))
    PreferenceCard {
        PreferenceRow(
            title = "Theme",
            subtitle = prefs.profile.preferredTheme.name.lowercase().replaceFirstChar { it.titlecase(Locale.getDefault()) },
            icon = Icons.Rounded.BrightnessMedium,
            onClick = { /* TODO: Open theme selection dialog */ }
        )
        SwitchRow(
            title = "Material You",
            subtitle = "Dynamic color from wallpaper",
            icon = Icons.Rounded.Palette,
            checked = prefs.useDynamicColor,
            onCheckedChange = viewModel::updateDynamicColor
        )
    }
}

@Composable
private fun PreferencesSection(prefs: UserPreferences, viewModel: SettingsViewModel) {
    SectionHeader(title = "Preferences", modifier = Modifier.padding(horizontal = PatFlowSpacing.space4))
    PreferenceCard {
        PreferenceRow(
            title = "Preferred Currency",
            subtitle = prefs.profile.preferredCurrency,
            icon = Icons.Rounded.CurrencyExchange,
            onClick = { /* TODO: Open currency selection dialog */ }
        )
        PreferenceRow(
            title = "Date Format",
            subtitle = prefs.dateFormat,
            icon = Icons.Rounded.DateRange,
            onClick = { /* TODO: Open date format selection dialog */ }
        )
        PreferenceRow(
            title = "First Day of Week",
            subtitle = if (prefs.firstDayOfWeek == 1) "Sunday" else "Monday",
            icon = Icons.Rounded.CalendarToday,
            onClick = { /* TODO: Open day selection dialog */ }
        )
        SwitchRow(
            title = "Haptic Feedback",
            subtitle = "Subtle vibration for interactions",
            icon = Icons.Rounded.TouchApp,
            checked = prefs.hapticFeedbackEnabled,
            onCheckedChange = viewModel::updateHapticFeedback
        )
    }
}

@Composable
private fun NotificationsSection(prefs: UserPreferences, viewModel: SettingsViewModel) {
    SectionHeader(title = "Notifications (Coming Soon)", modifier = Modifier.padding(horizontal = PatFlowSpacing.space4))
    PreferenceCard {
        SwitchRow(
            title = "Due Tomorrow",
            checked = prefs.notificationDueTomorrow,
            onCheckedChange = viewModel::updateNotifDueTomorrow,
            enabled = false
        )
        SwitchRow(
            title = "Due Today",
            checked = prefs.notificationDueToday,
            onCheckedChange = viewModel::updateNotifDueToday,
            enabled = false
        )
        SwitchRow(
            title = "Overdue",
            checked = prefs.notificationOverdue,
            onCheckedChange = viewModel::updateNotifOverdue,
            enabled = false
        )
    }
}

@Composable
private fun AboutSection() {
    SectionHeader(title = "About", modifier = Modifier.padding(horizontal = PatFlowSpacing.space4))
    PreferenceCard {
        PreferenceRow(
            title = "App Version",
            subtitle = "1.0.0-RC1 (Build 100)",
            icon = Icons.Rounded.Info
        )
        PreferenceRow(
            title = "Open Source Licenses",
            icon = Icons.Rounded.Feedback,
            onClick = { /* TODO: Navigate to licenses */ }
        )
        ComingSoonRow(title = "Backup & Restore")
        ComingSoonRow(title = "Export Data")
        ComingSoonRow(title = "Cloud Sync")
    }
}

@Composable
private fun PreferenceCard(content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = PatFlowSpacing.space4, vertical = PatFlowSpacing.space2),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
    ) {
        Column(modifier = Modifier.fillMaxWidth(), content = content)
    }
}

@Composable
private fun PreferenceRow(
    title: String,
    subtitle: String? = null,
    icon: ImageVector? = null,
    onClick: (() -> Unit)? = null
) {
    ListItem(
        headlineContent = { Text(title) },
        supportingContent = subtitle?.let { { Text(it) } },
        leadingContent = icon?.let { { Icon(it, contentDescription = null) } },
        modifier = Modifier.then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier)
    )
}

@Composable
private fun SwitchRow(
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    subtitle: String? = null,
    icon: ImageVector? = null,
    enabled: Boolean = true
) {
    ListItem(
        headlineContent = { Text(title) },
        supportingContent = subtitle?.let { { Text(it) } },
        leadingContent = icon?.let { { Icon(it, contentDescription = null) } },
        trailingContent = {
            Switch(checked = checked, onCheckedChange = onCheckedChange, enabled = enabled)
        },
        modifier = Modifier.alpha(if (enabled) 1f else 0.5f)
    )
}

@Composable
private fun ComingSoonRow(title: String) {
    ListItem(
        headlineContent = { Text(title) },
        trailingContent = { 
            Text(
                "Coming Soon", 
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary
            ) 
        },
        modifier = Modifier.alpha(0.5f)
    )
}
