package com.patflow.app.feature.settings

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.patflow.app.core.components.AppTopBar
import com.patflow.app.core.components.TopBarType
import com.patflow.app.core.components.AppTextField
import com.patflow.app.core.theme.PatFlowSpacing
import com.patflow.app.domain.model.UserPreferences

enum class EditType { DISPLAY_NAME, MONTHLY_BUDGET, THEME, CURRENCY, DATE_FORMAT, FIRST_DAY_OF_WEEK }

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
    var showRestoreConfirm by remember { mutableStateOf(false) }
    var selectedRestoreUri by remember { mutableStateOf<Uri?>(null) }
    var editingValueType by remember { mutableStateOf<EditType?>(null) }

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
                type = TopBarType.Small
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(bottom = PatFlowSpacing.space6)
        ) {
            settings?.let { prefs ->
                SettingsSection(title = "User Profile") {
                    PreferenceRow(
                        title = "Display Name", 
                        subtitle = prefs.profile.displayName.ifBlank { "Not set" }, 
                        icon = Icons.Rounded.AccountCircle, 
                        onClick = { editingValueType = EditType.DISPLAY_NAME }
                    )
                    HorizontalDivider(modifier = Modifier.padding(start = 56.dp), color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
                    PreferenceRow(
                        title = "Monthly Budget", 
                        subtitle = prefs.profile.monthlyBudget?.let { "₱$it" } ?: "Not set", 
                        icon = Icons.Rounded.AccountBalanceWallet, 
                        onClick = { editingValueType = EditType.MONTHLY_BUDGET }
                    )
                }

                SettingsSection(title = "Appearance") {
                    PreferenceRow(
                        title = "Theme", 
                        subtitle = prefs.profile.preferredTheme.name.lowercase().replaceFirstChar { it.titlecase() }, 
                        icon = Icons.Rounded.BrightnessMedium, 
                        onClick = { editingValueType = EditType.THEME }
                    )
                    HorizontalDivider(modifier = Modifier.padding(start = 56.dp), color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
                    SwitchRow(
                        title = "Material You", 
                        subtitle = "Dynamic color from wallpaper", 
                        icon = Icons.Rounded.Palette, 
                        checked = prefs.useDynamicColor, 
                        onCheckedChange = viewModel::updateDynamicColor
                    )
                }

                SettingsSection(title = "Preferences") {
                    PreferenceRow(
                        title = "Preferred Currency", 
                        subtitle = prefs.profile.preferredCurrency, 
                        icon = Icons.Rounded.CurrencyExchange, 
                        onClick = { editingValueType = EditType.CURRENCY }
                    )
                    HorizontalDivider(modifier = Modifier.padding(start = 56.dp), color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
                    PreferenceRow(
                        title = "Date Format", 
                        subtitle = prefs.dateFormat, 
                        icon = Icons.Rounded.DateRange, 
                        onClick = { editingValueType = EditType.DATE_FORMAT }
                    )
                    HorizontalDivider(modifier = Modifier.padding(start = 56.dp), color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
                    PreferenceRow(
                        title = "First Day of Week", 
                        subtitle = if (prefs.firstDayOfWeek == 1) "Sunday" else "Monday", 
                        icon = Icons.Rounded.CalendarToday, 
                        onClick = { editingValueType = EditType.FIRST_DAY_OF_WEEK }
                    )
                    HorizontalDivider(modifier = Modifier.padding(start = 56.dp), color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
                    SwitchRow(
                        title = "Haptic Feedback", 
                        subtitle = "Subtle vibration for interactions", 
                        icon = Icons.Rounded.TouchApp, 
                        checked = prefs.hapticFeedbackEnabled, 
                        onCheckedChange = viewModel::updateHapticFeedback
                    )
                }

                SettingsSection(title = "Notifications") {
                    SwitchRow(
                        title = "Master Toggle", 
                        checked = prefs.notificationsMasterEnabled, 
                        onCheckedChange = viewModel::updateNotifMaster,
                        icon = Icons.Rounded.Notifications
                    )
                    if (prefs.notificationsMasterEnabled) {
                        HorizontalDivider(modifier = Modifier.padding(start = 56.dp), color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
                        SwitchRow(title = "Upcoming Bills", checked = prefs.notificationUpcomingEnabled, onCheckedChange = viewModel::updateNotifUpcoming)
                        HorizontalDivider(modifier = Modifier.padding(start = 56.dp), color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
                        SwitchRow(title = "Due Today", checked = prefs.notificationDueTodayEnabled, onCheckedChange = viewModel::updateNotifDueToday)
                        HorizontalDivider(modifier = Modifier.padding(start = 56.dp), color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
                        SwitchRow(title = "Overdue Alerts", checked = prefs.notificationOverdueEnabled, onCheckedChange = viewModel::updateNotifOverdue)
                        HorizontalDivider(modifier = Modifier.padding(start = 56.dp), color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
                        PreferenceRow(title = "Test Notification", icon = Icons.Rounded.NotificationAdd, onClick = viewModel::testNotification)
                    }
                }

                SettingsSection(title = "Backup & Data") {
                    PreferenceRow(title = "Backup Data", subtitle = "Create a JSON backup file", icon = Icons.Rounded.Backup, onClick = dataViewModel::createBackup)
                    HorizontalDivider(modifier = Modifier.padding(start = 56.dp), color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
                    PreferenceRow(title = "Import Backup", subtitle = "Restore data from a file", icon = Icons.Rounded.FileUpload, onClick = { openDocLauncher.launch(arrayOf("application/json")) })
                    HorizontalDivider(modifier = Modifier.padding(start = 56.dp), color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
                    PreferenceRow(title = "Export as JSON", icon = Icons.Rounded.FileDownload, onClick = dataViewModel::createBackup)
                    HorizontalDivider(modifier = Modifier.padding(start = 56.dp), color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
                    PreferenceRow(title = "Export as CSV", subtitle = "Financial history", icon = Icons.Rounded.Download, onClick = dataViewModel::exportCsv)
                }

                SettingsSection(title = "Cloud Synchronization") {
                    ComingSoonRow(title = "Sync with Cloud", icon = Icons.Rounded.CloudSync)
                    HorizontalDivider(modifier = Modifier.padding(start = 56.dp), color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
                    ComingSoonRow(title = "Multi-device Support", icon = Icons.Rounded.Devices)
                    HorizontalDivider(modifier = Modifier.padding(start = 56.dp), color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
                    ComingSoonRow(title = "Automated Backups", icon = Icons.Rounded.AutoMode)
                }

                SettingsSection(title = "About") {
                    PreferenceRow(title = "App Version", subtitle = "1.0.0 (Build 100)", icon = Icons.Rounded.Info)
                }
            }
        }
    }

    // Dialogs
    editingValueType?.let { type ->
        when (type) {
            EditType.THEME -> ThemeSelectionDialog(
                currentTheme = settings?.profile?.preferredTheme?.name ?: "SYSTEM",
                onDismiss = { editingValueType = null },
                onConfirm = { viewModel.updateThemeMode(it); editingValueType = null }
            )
            EditType.FIRST_DAY_OF_WEEK -> FirstDaySelectionDialog(
                currentDay = settings?.firstDayOfWeek ?: 1,
                onDismiss = { editingValueType = null },
                onConfirm = { viewModel.updateFirstDayOfWeek(it); editingValueType = null }
            )
            else -> SettingsValueDialog(
                type = type,
                initialValue = when (type) {
                    EditType.DISPLAY_NAME -> settings?.profile?.displayName ?: ""
                    EditType.MONTHLY_BUDGET -> settings?.profile?.monthlyBudget?.toString() ?: ""
                    EditType.CURRENCY -> settings?.profile?.preferredCurrency ?: "PHP"
                    EditType.DATE_FORMAT -> settings?.dateFormat ?: "MM/dd/yyyy"
                    else -> ""
                },
                onDismiss = { editingValueType = null },
                onConfirm = { newValue ->
                    when (type) {
                        EditType.DISPLAY_NAME -> viewModel.updateDisplayName(newValue)
                        EditType.MONTHLY_BUDGET -> viewModel.updateMonthlyBudget(newValue.toDoubleOrNull())
                        EditType.CURRENCY -> viewModel.updatePreferredCurrency(newValue)
                        EditType.DATE_FORMAT -> viewModel.updateDateFormat(newValue)
                        else -> {}
                    }
                    editingValueType = null
                }
            )
        }
    }

    if (showRestoreConfirm) {
        AlertDialog(
            onDismissRequest = { showRestoreConfirm = false },
            title = { Text("Restore Backup?") },
            text = { Text("This will PERMANENTLY overwrite your existing data. This action cannot be undone.") },
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
                TextButton(onClick = { showRestoreConfirm = false }) { Text("Cancel") }
            }
        )
    }

    if (dataState is DataManagementViewModel.DataManagementUiState.Loading) {
        AlertDialog(onDismissRequest = {}, title = { Text((dataState as DataManagementViewModel.DataManagementUiState.Loading).message) }, confirmButton = {})
    }
}

@Composable
private fun SettingsSection(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = PatFlowSpacing.space4)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
        content()
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
        headlineContent = { Text(title, style = MaterialTheme.typography.titleMedium) },
        supportingContent = subtitle?.let { { Text(it, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant) } },
        leadingContent = icon?.let { { Icon(it, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant) } },
        trailingContent = onClick?.let { { Icon(Icons.Rounded.ChevronRight, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant) } },
        modifier = Modifier
            .fillMaxWidth()
            .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier),
        colors = ListItemDefaults.colors(containerColor = androidx.compose.ui.graphics.Color.Transparent)
    )
}

@Composable
private fun SwitchRow(
    title: String, 
    checked: Boolean, 
    onCheckedChange: (Boolean) -> Unit, 
    subtitle: String? = null, 
    icon: ImageVector? = null
) {
    ListItem(
        headlineContent = { Text(title, style = MaterialTheme.typography.titleMedium) },
        supportingContent = subtitle?.let { { Text(it, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant) } },
        leadingContent = icon?.let { { Icon(it, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant) } },
        trailingContent = { Switch(checked = checked, onCheckedChange = onCheckedChange) },
        colors = ListItemDefaults.colors(containerColor = androidx.compose.ui.graphics.Color.Transparent)
    )
}

@Composable
private fun ComingSoonRow(title: String, icon: ImageVector? = null) {
    ListItem(
        headlineContent = { Text(title, style = MaterialTheme.typography.titleMedium) },
        leadingContent = icon?.let { { Icon(it, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)) } },
        trailingContent = { 
            Text(
                "Coming Soon", 
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary
            ) 
        },
        modifier = Modifier.alpha(0.5f),
        colors = ListItemDefaults.colors(containerColor = androidx.compose.ui.graphics.Color.Transparent)
    )
}

@Composable
private fun ThemeSelectionDialog(currentTheme: String, onDismiss: () -> Unit, onConfirm: (String) -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Select Theme") },
        text = {
            Column {
                listOf("LIGHT", "DARK", "SYSTEM").forEach { theme ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onConfirm(theme) }
                            .padding(16.dp), 
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(selected = theme == currentTheme, onClick = { onConfirm(theme) })
                        Text(text = theme.lowercase().replaceFirstChar { it.titlecase() }, modifier = Modifier.padding(start = 16.dp))
                    }
                }
            }
        },
        confirmButton = { TextButton(onClick = onDismiss) { Text("Close") } }
    )
}

@Composable
private fun FirstDaySelectionDialog(currentDay: Int, onDismiss: () -> Unit, onConfirm: (Int) -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("First Day of Week") },
        text = {
            Column {
                mapOf(1 to "Sunday", 2 to "Monday").forEach { (value, label) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onConfirm(value) }
                            .padding(16.dp), 
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(selected = value == currentDay, onClick = { onConfirm(value) })
                        Text(text = label, modifier = Modifier.padding(start = 16.dp))
                    }
                }
            }
        },
        confirmButton = { TextButton(onClick = onDismiss) { Text("Close") } }
    )
}

@Composable
private fun SettingsValueDialog(type: EditType, initialValue: String, onDismiss: () -> Unit, onConfirm: (String) -> Unit) {
    var value by remember { mutableStateOf(initialValue) }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { 
            Text(
                text = "Update ${type.name.lowercase().split("_").joinToString(" ") { it.replaceFirstChar { char -> char.titlecase() } }}",
                style = MaterialTheme.typography.titleLarge
            ) 
        },
        text = { 
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                AppTextField(value = value, onValueChange = { value = it }, label = "New Value") 
            }
        },
        confirmButton = { TextButton(onClick = { onConfirm(value) }) { Text("Save") } },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancel") } }
    )
}
