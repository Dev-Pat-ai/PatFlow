package com.patflow.app.feature.settings

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material.icons.rounded.Backup
import androidx.compose.material.icons.rounded.BrightnessMedium
import androidx.compose.material.icons.rounded.CalendarToday
import androidx.compose.material.icons.rounded.CurrencyExchange
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.Download
import androidx.compose.material.icons.rounded.Feedback
import androidx.compose.material.icons.rounded.FileDownload
import androidx.compose.material.icons.rounded.FileUpload
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Palette
import androidx.compose.material.icons.rounded.TouchApp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.patflow.app.core.components.AppTopBar
import com.patflow.app.core.components.SectionHeader
import com.patflow.app.core.theme.PatFlowSpacing
import com.patflow.app.domain.model.UserPreferences
import java.util.Locale

/**
 * Screen for managing application settings and user profile (Architecture §6).
 * Expanded with Data Management features in Phase 7B.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel(),
    dataViewModel: DataManagementViewModel = hiltViewModel()
) {
    val settings by viewModel.uiState.collectAsState()
    val dataState by dataViewModel.uiState.collectAsState()
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    var pendingFileContent by remember { mutableStateOf<String?>(null) }
    var pendingMimeType by remember { mutableStateOf<String?>(null) }
    var showRestoreConfirm by remember { mutableStateOf(false) }
    var selectedRestoreUri by remember { mutableStateOf<Uri?>(null) }

    val createDocLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument("*/*")
    ) { uri ->
        uri?.let {
            context.contentResolver.openOutputStream(it)?.use { stream ->
                stream.write(pendingFileContent?.toByteArray() ?: byteArrayOf())
            }
            pendingFileContent = null
        }
    }

    val openDocLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri ->
        uri?.let {
            selectedRestoreUri = it
            showRestoreConfirm = true
        }
    }

    LaunchedEffect(Unit) {
        dataViewModel.eventFlow.collect { event ->
            when (event) {
                is DataManagementViewModel.UiEvent.SaveFile -> {
                    pendingFileContent = event.content
                    pendingMimeType = event.mimeType
                    createDocLauncher.launch(event.filename)
                }
                is DataManagementViewModel.UiEvent.ShowSuccess -> {
                    snackbarHostState.showSnackbar(event.message)
                }
            }
        }
    }

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
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
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
                DataManagementSection(
                    onBackup = dataViewModel::createBackup,
                    onRestore = { openDocLauncher.launch(arrayOf("application/json")) },
                    onExportJson = dataViewModel::createBackup, // Reuses logic for JSON dump
                    onExportCsv = dataViewModel::exportCsv
                )
                NotificationsSection(prefs, viewModel)
                AboutSection()
            }
        }
    }

    if (showRestoreConfirm) {
        AlertDialog(
            onDismissRequest = { showRestoreConfirm = false },
            title = { Text("Restore Backup?") },
            text = { Text("This will PERMANENTLY overwrite your existing data with the selected backup. This action cannot be undone.") },
            confirmButton = {
                TextButton(onClick = {
                    showRestoreConfirm = false
                    selectedRestoreUri?.let { uri ->
                        val content = context.contentResolver.openInputStream(uri)?.bufferedReader()?.use { it.readText() }
                        content?.let { dataViewModel.restoreBackup(it) }
                    }
                }) {
                    Text("Restore", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showRestoreConfirm = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    // Loading overlay
    if (dataState is DataManagementViewModel.DataManagementUiState.Loading) {
        AlertDialog(
            onDismissRequest = {},
            title = { Text((dataState as DataManagementViewModel.DataManagementUiState.Loading).message) },
            confirmButton = {}
        )
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
private fun DataManagementSection(
    onBackup: () -> Unit,
    onRestore: () -> Unit,
    onExportJson: () -> Unit,
    onExportCsv: () -> Unit
) {
    SectionHeader(title = "Data Management", modifier = Modifier.padding(horizontal = PatFlowSpacing.space4))
    PreferenceCard {
        PreferenceRow(
            title = "Backup Data",
            subtitle = "Create a JSON backup file",
            icon = Icons.Rounded.Backup,
            onClick = onBackup
        )
        PreferenceRow(
            title = "Import Backup",
            subtitle = "Restore data from a file",
            icon = Icons.Rounded.FileUpload,
            onClick = onRestore
        )
        PreferenceRow(
            title = "Export as JSON",
            icon = Icons.Rounded.FileDownload,
            onClick = onExportJson
        )
        PreferenceRow(
            title = "Export as CSV",
            subtitle = "Bills and Payments history",
            icon = Icons.Rounded.Download,
            onClick = onExportCsv
        )
        ComingSoonRow(title = "Cloud Sync")
    }
}

@Composable
private fun NotificationsSection(prefs: UserPreferences, viewModel: SettingsViewModel) {
    SectionHeader(title = "Notifications", modifier = Modifier.padding(horizontal = PatFlowSpacing.space4))
    PreferenceCard {
        SwitchRow(
            title = "Master Toggle",
            subtitle = "Enable or disable all notifications",
            checked = prefs.notificationsMasterEnabled,
            onCheckedChange = viewModel::updateNotifMaster
        )
        if (prefs.notificationsMasterEnabled) {
            SwitchRow(
                title = "Upcoming Bills",
                checked = prefs.notificationUpcomingEnabled,
                onCheckedChange = viewModel::updateNotifUpcoming
            )
            SwitchRow(
                title = "Due Today",
                checked = prefs.notificationDueTodayEnabled,
                onCheckedChange = viewModel::updateNotifDueToday
            )
            SwitchRow(
                title = "Overdue Alerts",
                checked = prefs.notificationOverdueEnabled,
                onCheckedChange = viewModel::updateNotifOverdue
            )
            SwitchRow(
                title = "Payment Success",
                checked = prefs.notificationPaymentSuccessEnabled,
                onCheckedChange = viewModel::updateNotifPaymentSuccess
            )
            SwitchRow(
                title = "Quiet Hours",
                subtitle = "Suppress notifications at night",
                checked = prefs.quietHoursEnabled,
                onCheckedChange = viewModel::updateQuietHoursEnabled
            )
            PreferenceRow(
                title = "Test Notification",
                subtitle = "Fire a sample reminder",
                icon = Icons.Rounded.Notifications,
                onClick = viewModel::testNotification
            )
        }
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
