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
import com.patflow.app.core.components.SectionHeader
import com.patflow.app.core.components.AppTextField
import com.patflow.app.core.theme.PatFlowShapes
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
                ProfileSection(prefs, onEdit = { editingValueType = it })
                AppearanceSection(prefs, viewModel, onEdit = { editingValueType = it })
                PreferencesSection(prefs, viewModel, onEdit = { editingValueType = it })
                DataManagementSection(
                    onBackup = dataViewModel::createBackup,
                    onRestore = { openDocLauncher.launch(arrayOf("application/json")) },
                    onExportJson = dataViewModel::createBackup,
                    onExportCsv = dataViewModel::exportCsv
                )
                NotificationsSection(prefs, viewModel)
                CloudSyncSection()
                AboutSection()
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
private fun ProfileSection(prefs: UserPreferences, onEdit: (EditType) -> Unit) {
    SectionHeader(title = "User Profile", modifier = Modifier.padding(horizontal = PatFlowSpacing.space4))
    PreferenceCard {
        PreferenceRow(title = "Display Name", subtitle = prefs.profile.displayName.ifBlank { "Not set" }, icon = Icons.Rounded.AccountCircle, onClick = { onEdit(EditType.DISPLAY_NAME) })
        PreferenceRow(title = "Monthly Budget", subtitle = prefs.profile.monthlyBudget?.let { "₱$it" } ?: "Not set", icon = Icons.Rounded.AccountBalanceWallet, onClick = { onEdit(EditType.MONTHLY_BUDGET) })
    }
}

@Composable
private fun AppearanceSection(prefs: UserPreferences, viewModel: SettingsViewModel, onEdit: (EditType) -> Unit) {
    SectionHeader(title = "Appearance", modifier = Modifier.padding(horizontal = PatFlowSpacing.space4))
    PreferenceCard {
        PreferenceRow(title = "Theme", subtitle = prefs.profile.preferredTheme.name.lowercase().replaceFirstChar { it.titlecase() }, icon = Icons.Rounded.BrightnessMedium, onClick = { onEdit(EditType.THEME) })
        SwitchRow(title = "Material You", subtitle = "Dynamic color from wallpaper", icon = Icons.Rounded.Palette, checked = prefs.useDynamicColor, onCheckedChange = viewModel::updateDynamicColor)
    }
}

@Composable
private fun PreferencesSection(prefs: UserPreferences, viewModel: SettingsViewModel, onEdit: (EditType) -> Unit) {
    SectionHeader(title = "Preferences", modifier = Modifier.padding(horizontal = PatFlowSpacing.space4))
    PreferenceCard {
        PreferenceRow(title = "Preferred Currency", subtitle = prefs.profile.preferredCurrency, icon = Icons.Rounded.CurrencyExchange, onClick = { onEdit(EditType.CURRENCY) })
        PreferenceRow(title = "Date Format", subtitle = prefs.dateFormat, icon = Icons.Rounded.DateRange, onClick = { onEdit(EditType.DATE_FORMAT) })
        PreferenceRow(title = "First Day of Week", subtitle = if (prefs.firstDayOfWeek == 1) "Sunday" else "Monday", icon = Icons.Rounded.CalendarToday, onClick = { onEdit(EditType.FIRST_DAY_OF_WEEK) })
        SwitchRow(title = "Haptic Feedback", subtitle = "Subtle vibration for interactions", icon = Icons.Rounded.TouchApp, checked = prefs.hapticFeedbackEnabled, onCheckedChange = viewModel::updateHapticFeedback)
    }
}

@Composable
private fun DataManagementSection(onBackup: () -> Unit, onRestore: () -> Unit, onExportJson: () -> Unit, onExportCsv: () -> Unit) {
    SectionHeader(title = "Data Management", modifier = Modifier.padding(horizontal = PatFlowSpacing.space4))
    PreferenceCard {
        PreferenceRow(title = "Backup Data", subtitle = "Create a JSON backup file", icon = Icons.Rounded.Backup, onClick = onBackup)
        PreferenceRow(title = "Import Backup", subtitle = "Restore data from a file", icon = Icons.Rounded.FileUpload, onClick = onRestore)
        PreferenceRow(title = "Export as JSON", icon = Icons.Rounded.FileDownload, onClick = onExportJson)
        PreferenceRow(title = "Export as CSV", subtitle = "Financial history", icon = Icons.Rounded.Download, onClick = onExportCsv)
    }
}

@Composable
private fun NotificationsSection(prefs: UserPreferences, viewModel: SettingsViewModel) {
    SectionHeader(title = "Notifications", modifier = Modifier.padding(horizontal = PatFlowSpacing.space4))
    PreferenceCard {
        SwitchRow(title = "Master Toggle", checked = prefs.notificationsMasterEnabled, onCheckedChange = viewModel::updateNotifMaster)
        if (prefs.notificationsMasterEnabled) {
            SwitchRow(title = "Upcoming Bills", checked = prefs.notificationUpcomingEnabled, onCheckedChange = viewModel::updateNotifUpcoming)
            SwitchRow(title = "Due Today", checked = prefs.notificationDueTodayEnabled, onCheckedChange = viewModel::updateNotifDueToday)
            SwitchRow(title = "Overdue Alerts", checked = prefs.notificationOverdueEnabled, onCheckedChange = viewModel::updateNotifOverdue)
            PreferenceRow(title = "Test Notification", icon = Icons.Rounded.Notifications, onClick = viewModel::testNotification)
        }
    }
}

@Composable
private fun CloudSyncSection() {
    SectionHeader(title = "Cloud Synchronization", modifier = Modifier.padding(horizontal = PatFlowSpacing.space4))
    PreferenceCard {
        ComingSoonRow(title = "Sync with Cloud")
        ComingSoonRow(title = "Multi-device Support")
        ComingSoonRow(title = "Automated Backups")
    }
}

@Composable
private fun AboutSection() {
    SectionHeader(title = "About", modifier = Modifier.padding(horizontal = PatFlowSpacing.space4))
    PreferenceCard {
        PreferenceRow(title = "App Version", subtitle = "1.0.0 (Build 100)", icon = Icons.Rounded.Info)
    }
}

@Composable
private fun ThemeSelectionDialog(currentTheme: String, onDismiss: () -> Unit, onConfirm: (String) -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Select Theme") },
        text = {
            Column {
                listOf("LIGHT", "DARK", "SYSTEM").forEach { theme ->
                    Row(modifier = Modifier.fillMaxWidth().clickable { onConfirm(theme) }.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
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
                    Row(modifier = Modifier.fillMaxWidth().clickable { onConfirm(value) }.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
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

@Composable
private fun PreferenceCard(content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = PatFlowSpacing.space4, vertical = PatFlowSpacing.space2),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow
        ),
        shape = PatFlowShapes.lg,
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Column(modifier = Modifier.fillMaxWidth(), content = content)
    }
}

@Composable
private fun PreferenceRow(title: String, subtitle: String? = null, icon: ImageVector? = null, onClick: (() -> Unit)? = null) {
    ListItem(
        headlineContent = { Text(title, style = MaterialTheme.typography.titleMedium) },
        supportingContent = subtitle?.let { { Text(it, style = MaterialTheme.typography.bodyMedium) } },
        leadingContent = icon?.let { { Icon(it, contentDescription = null, tint = MaterialTheme.colorScheme.primary) } },
        trailingContent = onClick?.let { { Icon(Icons.Rounded.ChevronRight, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant) } },
        modifier = Modifier.then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier),
        colors = ListItemDefaults.colors(containerColor = androidx.compose.ui.graphics.Color.Transparent)
    )
}

@Composable
private fun SwitchRow(title: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit, subtitle: String? = null, icon: ImageVector? = null) {
    ListItem(
        headlineContent = { Text(title, style = MaterialTheme.typography.titleMedium) },
        supportingContent = subtitle?.let { { Text(it, style = MaterialTheme.typography.bodyMedium) } },
        leadingContent = icon?.let { { Icon(it, contentDescription = null, tint = MaterialTheme.colorScheme.primary) } },
        trailingContent = { Switch(checked = checked, onCheckedChange = onCheckedChange) },
        colors = ListItemDefaults.colors(containerColor = androidx.compose.ui.graphics.Color.Transparent)
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
