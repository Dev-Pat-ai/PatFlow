package com.patflow.app.feature.income

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Archive
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Unarchive
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.patflow.app.core.components.AppFab
import com.patflow.app.core.components.AppTopBar
import com.patflow.app.core.components.TopBarType
import com.patflow.app.core.components.CategoryChip
import com.patflow.app.core.components.EmptyState
import com.patflow.app.core.theme.PatFlowSpacing
import com.patflow.app.core.utils.CategoryMapper
import com.patflow.app.core.utils.CurrencyFormatter
import com.patflow.app.domain.model.IncomeSource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IncomeSourceListScreen(
    onNavigateBack: () -> Unit,
    onAddSourceClick: () -> Unit,
    onSourceClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: IncomeSourceListViewModel = hiltViewModel()
) {
    val sources by viewModel.sources.collectAsState()

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Income Templates",
                type = TopBarType.Small,
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        floatingActionButton = {
            AppFab(
                onClick = onAddSourceClick,
                icon = Icons.Rounded.Add,
                contentDescription = "Add Template"
            )
        }
    ) { padding ->
        if (sources.isEmpty()) {
            EmptyState(
                title = "No templates",
                description = "Create a template for recurring income.",
                icon = Icons.Rounded.Add,
                modifier = Modifier.padding(padding)
            )
        } else {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(PatFlowSpacing.space5),
                verticalArrangement = Arrangement.spacedBy(PatFlowSpacing.space3)
            ) {
                items(sources, key = { it.id }) { source ->
                    IncomeSourceItem(
                        source = source,
                        onClick = { onSourceClick(source.id) },
                        onArchive = { viewModel.archiveSource(source.id, !source.isArchived) },
                        onDelete = { viewModel.deleteSource(source.id) }
                    )
                }
            }
        }
    }
}

@Composable
private fun IncomeSourceItem(
    source: IncomeSource,
    onClick: () -> Unit,
    onArchive: () -> Unit,
    onDelete: () -> Unit
) {
    androidx.compose.material3.Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = com.patflow.app.core.theme.PatFlowShapes.lg,
        colors = androidx.compose.material3.CardDefaults.cardColors(
            containerColor = if (source.isArchived) MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f) else MaterialTheme.colorScheme.surface
        ),
        elevation = androidx.compose.material3.CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(PatFlowSpacing.space4)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(PatFlowSpacing.space4)
        ) {
            CategoryChip(category = CategoryMapper.mapToType(source.category.name))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = source.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = if (source.isArchived) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "${source.recurrence.type.name} · ${source.recurrence.startDate}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Row {
                IconButton(onClick = onArchive) {
                    Icon(
                        imageVector = if (source.isArchived) Icons.Rounded.Unarchive else Icons.Rounded.Archive,
                        contentDescription = "Archive",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                IconButton(onClick = onDelete) {
                    Icon(
                        imageVector = Icons.Rounded.Delete,
                        contentDescription = "Delete",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}
