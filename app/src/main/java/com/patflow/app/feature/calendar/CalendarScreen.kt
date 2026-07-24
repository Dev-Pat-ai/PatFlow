package com.patflow.app.feature.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ReceiptLong
import androidx.compose.material.icons.rounded.ChevronLeft
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material.icons.rounded.Payments
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.patflow.app.core.components.AppTopBar
import com.patflow.app.core.components.LoadingState
import com.patflow.app.core.components.SectionHeader
import com.patflow.app.core.theme.PatFlowShapes
import com.patflow.app.core.theme.PatFlowSpacing
import com.patflow.app.core.utils.CurrencyFormatter
import kotlinx.datetime.*
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(
    modifier: Modifier = Modifier,
    viewModel: CalendarViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var currentMonth by remember { mutableStateOf(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date) }

    Scaffold(
        topBar = { AppTopBar(title = "Financial Calendar") }
    ) { padding ->
        Column(modifier = modifier.fillMaxSize().padding(padding)) {
            when (val state = uiState) {
                CalendarUiState.Loading -> LoadingState()
                is CalendarUiState.Success -> {
                    CalendarHeader(
                        month = currentMonth,
                        onMonthChange = { currentMonth = it }
                    )
                    
                    CalendarGrid(
                        month = currentMonth,
                        selectedDate = state.selectedDate,
                        allEvents = state.allEvents,
                        onDateClick = viewModel::onDateSelected
                    )

                    Spacer(modifier = Modifier.height(PatFlowSpacing.space4))
                    
                    AgendaView(
                        selectedDate = state.selectedDate,
                        events = state.dayEvents
                    )
                }
            }
        }
    }
}

@Composable
private fun CalendarHeader(
    month: LocalDate,
    onMonthChange: (LocalDate) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(PatFlowSpacing.space4),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { onMonthChange(month.minus(DatePeriod(months = 1))) }) {
            Icon(Icons.Rounded.ChevronLeft, contentDescription = "Previous Month")
        }
        Text(
            text = "${month.month.name.lowercase().replaceFirstChar { it.titlecase(Locale.ROOT) }} ${month.year}",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        IconButton(onClick = { onMonthChange(month.plus(DatePeriod(months = 1))) }) {
            Icon(Icons.Rounded.ChevronRight, contentDescription = "Next Month")
        }
    }
}

@Composable
private fun CalendarGrid(
    month: LocalDate,
    selectedDate: LocalDate,
    allEvents: List<CalendarEvent>,
    onDateClick: (LocalDate) -> Unit
) {
    val daysInMonth = month.month.length(month.year % 4 == 0 && (month.year % 100 != 0 || month.year % 400 == 0))
    val firstDayOfMonth = LocalDate(month.year, month.month, 1)
    val dayOfWeekOffset = (firstDayOfMonth.dayOfWeek.ordinal + 1) % 7 

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = PatFlowSpacing.space4)
            .background(MaterialTheme.colorScheme.surfaceContainerLow, shape = PatFlowShapes.lg)
            .padding(PatFlowSpacing.space4)
    ) {
        // Weekdays Header
        Row(modifier = Modifier.fillMaxWidth()) {
            listOf("S", "M", "T", "W", "T", "F", "S").forEach { day ->
                Text(
                    text = day,
                    modifier = Modifier.weight(1f),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
        
        Spacer(modifier = Modifier.height(PatFlowSpacing.space3))

        // Dates Grid
        var day = 1
        for (row in 0..5) {
            Row(modifier = Modifier.fillMaxWidth()) {
                for (col in 0..6) {
                    val currentDayIndex = row * 7 + col
                    if (currentDayIndex < dayOfWeekOffset || day > daysInMonth) {
                        Box(modifier = Modifier.weight(1f).aspectRatio(1f))
                    } else {
                        val date = LocalDate(month.year, month.month, day)
                        val isSelected = date == selectedDate
                        val dayEvents = allEvents.filter { it.date == date }
                        
                        CalendarDayItem(
                            day = day,
                            date = date,
                            isSelected = isSelected,
                            events = dayEvents,
                            onClick = { onDateClick(date) },
                            modifier = Modifier.weight(1f)
                        )
                        day++
                    }
                }
            }
            if (day > daysInMonth) break
        }
    }
}

@Composable
private fun CalendarDayItem(
    day: Int,
    date: LocalDate, // Pass the full date
    isSelected: Boolean,
    events: List<CalendarEvent>,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    val isToday = date == today
    
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .padding(2.dp)
            .background(
                color = when {
                    isSelected -> MaterialTheme.colorScheme.primary
                    else -> Color.Transparent
                },
                shape = PatFlowShapes.md
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = day.toString(),
                color = when {
                    isSelected -> MaterialTheme.colorScheme.onPrimary
                    else -> MaterialTheme.colorScheme.onSurface
                },
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = if (isToday) FontWeight.Bold else FontWeight.Normal
            )
            if (events.isNotEmpty()) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(2.dp),
                    modifier = Modifier.padding(top = 2.dp)
                ) {
                    // Show up to 3 colored dots
                    events.take(3).forEach { event ->
                        val dotColor = when (event) {
                            is CalendarEvent.BillDue -> MaterialTheme.colorScheme.error
                            is CalendarEvent.PaymentMade -> MaterialTheme.colorScheme.primary
                            is CalendarEvent.IncomeReceived -> MaterialTheme.colorScheme.tertiary
                        }
                        Box(
                            modifier = Modifier
                                .size(4.dp)
                                .background(
                                    color = if (isSelected) MaterialTheme.colorScheme.onPrimary else dotColor,
                                    shape = CircleShape
                                )
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun AgendaView(
    selectedDate: LocalDate,
    events: List<CalendarEvent>
) {
    Column(modifier = Modifier.fillMaxSize().padding(horizontal = PatFlowSpacing.space4)) {
        SectionHeader(title = "Agenda: ${selectedDate}")
        if (events.isEmpty()) {
            Text(
                text = "No events scheduled for this day.",
                modifier = Modifier.padding(vertical = PatFlowSpacing.space4),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(PatFlowSpacing.space3)) {
                items(events) { event ->
                    AgendaItem(event)
                }
            }
        }
    }
}

@Composable
private fun AgendaItem(event: CalendarEvent) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = PatFlowShapes.md,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            val (icon, tint, title, amount) = when (event) {
                is CalendarEvent.BillDue -> Quad(Icons.AutoMirrored.Rounded.ReceiptLong, MaterialTheme.colorScheme.error, event.bill.name, event.cycle.amountDue)
                is CalendarEvent.PaymentMade -> Quad(Icons.Rounded.Payments, androidx.compose.ui.graphics.Color(0xFF10B981), event.billName, event.payment.amount)
                is CalendarEvent.IncomeReceived -> Quad(Icons.Rounded.ChevronRight, MaterialTheme.colorScheme.primary, event.sourceName, event.entry.amount)
            }
            
            Box(
                modifier = Modifier.size(40.dp).background(tint.copy(alpha = 0.1f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = tint)
            }
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                )
                Text(
                    text = when(event) {
                        is CalendarEvent.BillDue -> "Bill Due"
                        is CalendarEvent.PaymentMade -> "Payment Made"
                        is CalendarEvent.IncomeReceived -> "Income Received"
                    },
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Text(
                text = CurrencyFormatter.formatAmount(amount),
                style = MaterialTheme.typography.titleMedium.copy(fontFeatureSettings = "tnum"),
                fontWeight = FontWeight.Bold
            )
        }
    }
}

private data class Quad<A, B, C, D>(val a: A, val b: B, val c: C, val d: D)
