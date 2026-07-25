package com.patflow.app.feature.bills.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Notes
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import com.patflow.app.core.components.*
import com.patflow.app.core.theme.PatFlowShapes
import com.patflow.app.core.theme.PatFlowSpacing
import com.patflow.app.core.theme.patFlowCategoryColors
import com.patflow.app.core.utils.CurrencyFormatter
import com.patflow.app.domain.model.Bill
import com.patflow.app.domain.model.BillStatus
import com.patflow.app.domain.model.RecurrenceType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BillDetailBottomSheet(
    bill: Bill,
    status: BillStatus,
    amountDue: Double,
    dueDate: String,
    onDismiss: () -> Unit,
    onEdit: () -> Unit,
    onMarkAsPaid: () -> Unit
) {
    AppModalBottomSheet(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .padding(horizontal = PatFlowSpacing.space5)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header with Icon
            val categoryColors = patFlowCategoryColors()
            val categoryType = try { CategoryType.valueOf(bill.category.name.uppercase()) } catch(_: Exception) { CategoryType.OTHER }
            val icon = getCategoryIcon(categoryType)
            val colors = getCategoryColors(categoryType, categoryColors)
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .background(color = colors.containerColor, shape = PatFlowShapes.full),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(icon, contentDescription = null, tint = colors.onColor, modifier = Modifier.size(24.dp))
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(text = bill.name, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                        Text(text = bill.category.name, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
                StatusChip(status = status)
            }
            
            Spacer(modifier = Modifier.height(PatFlowSpacing.space5))
            
            // Amount
            Text(
                text = CurrencyFormatter.formatAmount(amountDue, bill.currencyCode),
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "Due $dueDate" + if(status == BillStatus.OVERDUE) " (Overdue)" else "",
                style = MaterialTheme.typography.bodyMedium,
                color = if(status == BillStatus.OVERDUE) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(PatFlowSpacing.space6))
            
            // Details Grid
            Column(verticalArrangement = Arrangement.spacedBy(PatFlowSpacing.space4)) {
                DetailRow(icon = Icons.Rounded.Info, label = "Status", value = status.name.lowercase().replaceFirstChar { it.titlecase() }, valueColor = if(status == BillStatus.OVERDUE) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface)
                DetailRow(icon = Icons.Rounded.Category, label = "Category", value = bill.category.name)
                DetailRow(icon = Icons.Rounded.CalendarToday, label = "Frequency", value = bill.recurrence.type.name.lowercase().replaceFirstChar { it.titlecase() })
                DetailRow(icon = Icons.Rounded.Numbers, label = "Account Number", value = bill.accountNumber ?: "Not set")
                DetailRow(icon = Icons.Rounded.Description, label = "Bill Reference", value = bill.billReference ?: "Not set")
                DetailRow(icon = Icons.AutoMirrored.Rounded.Notes, label = "Notes", value = bill.notes ?: "No notes added")
            }
            
            Spacer(modifier = Modifier.height(PatFlowSpacing.space8))
            
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(PatFlowSpacing.space3)) {
                FilledTonalButton(
                    onClick = onMarkAsPaid,
                    modifier = Modifier.weight(1f).height(44.dp),
                    colors = ButtonDefaults.filledTonalButtonColors(
                        containerColor = Color(0xFF10B981).copy(alpha = 0.1f),
                        contentColor = Color(0xFF10B981)
                    ),
                    shape = PatFlowShapes.full
                ) {
                    Icon(Icons.Rounded.Check, contentDescription = null, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Mark as Paid")
                }
                AppButton(
                    onClick = onEdit,
                    modifier = Modifier.weight(1f),
                    type = AppButtonType.Tonal
                ) {
                    Icon(Icons.Rounded.Edit, contentDescription = null, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Edit Bill")
                }
            }
            Spacer(modifier = Modifier.height(PatFlowSpacing.space6))
        }
    }
}

@Composable
private fun DetailRow(icon: ImageVector, label: String, value: String, valueColor: Color = MaterialTheme.colorScheme.onSurface) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = label, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.weight(1f))
        Text(text = value, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium, color = valueColor, textAlign = TextAlign.End, modifier = Modifier.weight(1.5f))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBillBottomSheet(
    onDismiss: () -> Unit,
    onSave: (name: String, amount: Double, category: CategoryType, dueDate: LocalDate, recurrence: RecurrenceType) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<CategoryType?>(null) }
    var selectedDate by remember { mutableStateOf(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date) }
    var selectedFrequency by remember { mutableStateOf(RecurrenceType.MONTHLY) }
    
    var showDatePicker by remember { mutableStateOf(false) }
    var showFrequencyPicker by remember { mutableStateOf(false) }
    
    AppModalBottomSheet(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .padding(horizontal = PatFlowSpacing.space5)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            Text(text = "Add Bill", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(PatFlowSpacing.space4))
            
            // Category Grid
            Text(text = "Select Category", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(modifier = Modifier.height(PatFlowSpacing.space2))
            
            val mockCategories = listOf(
                CategoryType.ELECTRICITY, CategoryType.WATER, CategoryType.INTERNET, CategoryType.PHONE, CategoryType.INSURANCE,
                CategoryType.LOAN, CategoryType.RENT, CategoryType.SUBSCRIPTION
            )
            
            Box(modifier = Modifier.height(140.dp)) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(5),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(mockCategories) { cat ->
                        CategoryIconItem(cat, isSelected = selectedCategory == cat) { selectedCategory = cat }
                    }
                    item {
                        CategoryIconItem(CategoryType.OTHER, isSelected = selectedCategory == CategoryType.OTHER, label = "Others") { selectedCategory = CategoryType.OTHER }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(PatFlowSpacing.space4))
            
            AppTextField(value = name, onValueChange = { name = it }, label = "Bill Name", placeholder = "e.g. Meralco")
            Spacer(modifier = Modifier.height(PatFlowSpacing.space3))
            AmountTextField(value = amount, onValueChange = { amount = it }, label = "Amount")
            Spacer(modifier = Modifier.height(PatFlowSpacing.space3))
            
            // Due Date and Frequency
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(PatFlowSpacing.space3)) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { showDatePicker = true }
                ) {
                    AppTextField(
                        value = "${selectedDate.month.name.lowercase().replaceFirstChar { it.uppercase() }} ${selectedDate.dayOfMonth}, ${selectedDate.year}", 
                        onValueChange = {}, 
                        label = "Due Date", 
                        trailingIcon = { Icon(Icons.Rounded.CalendarToday, null) }, 
                        readOnly = true
                    )
                    Box(modifier = Modifier.matchParentSize().clickable { showDatePicker = true })
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { showFrequencyPicker = true }
                ) {
                    AppTextField(
                        value = selectedFrequency.name.lowercase().replaceFirstChar { it.titlecase() }, 
                        onValueChange = {}, 
                        label = "Frequency", 
                        trailingIcon = { Icon(Icons.Rounded.ArrowDropDown, null) }, 
                        readOnly = true
                    )
                    Box(modifier = Modifier.matchParentSize().clickable { showFrequencyPicker = true })
                }
            }
            
            Spacer(modifier = Modifier.height(PatFlowSpacing.space6))
            
            AppButton(
                onClick = { 
                    val amt = amount.toDoubleOrNull() ?: 0.0
                    selectedCategory?.let { cat ->
                        onSave(name, amt, cat, selectedDate, selectedFrequency)
                    }
                }, 
                modifier = Modifier.fillMaxWidth(),
                enabled = name.isNotBlank() && amount.isNotBlank() && selectedCategory != null
            ) {
                Text("Save Bill")
            }
            Spacer(modifier = Modifier.height(PatFlowSpacing.space6))
        }
    }
    
    if (showDatePicker) {
        AppDatePickerDialog(
            onDateSelected = { date ->
                if (date != null) selectedDate = date
                showDatePicker = false
            },
            onDismiss = { showDatePicker = false },
            initialDate = selectedDate
        )
    }
    
    if (showFrequencyPicker) {
        AlertDialog(
            onDismissRequest = { showFrequencyPicker = false },
            title = { Text("Select Frequency") },
            text = {
                Column {
                    listOf(RecurrenceType.MONTHLY, RecurrenceType.WEEKLY, RecurrenceType.BIWEEKLY, RecurrenceType.YEARLY, RecurrenceType.ONE_TIME).forEach { freq ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { 
                                    selectedFrequency = freq
                                    showFrequencyPicker = false
                                }
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(selected = selectedFrequency == freq, onClick = null)
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(text = freq.name.lowercase().replaceFirstChar { it.titlecase() })
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showFrequencyPicker = false }) { Text("Cancel") }
            }
        )
    }
}

@Composable
private fun CategoryIconItem(category: CategoryType, isSelected: Boolean, label: String? = null, onClick: () -> Unit) {
    val categoryColors = patFlowCategoryColors()
    val icon = getCategoryIcon(category)
    val colors = getCategoryColors(category, categoryColors)
    
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.clickable(onClick = onClick)) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .background(color = if(isSelected) MaterialTheme.colorScheme.primary else colors.containerColor, shape = PatFlowShapes.full),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = if(isSelected) MaterialTheme.colorScheme.onPrimary else colors.onColor, modifier = Modifier.size(20.dp))
        }
        Text(
            text = label ?: category.name.lowercase().replaceFirstChar { it.titlecase() },
            style = MaterialTheme.typography.labelSmall,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

// Helpers
private fun getCategoryIcon(category: CategoryType): ImageVector = when (category) {
    CategoryType.ELECTRICITY -> Icons.Rounded.Bolt
    CategoryType.WATER -> Icons.Rounded.WaterDrop
    CategoryType.INTERNET -> Icons.Rounded.Wifi
    CategoryType.RENT -> Icons.Rounded.House
    CategoryType.PHONE -> Icons.Rounded.Smartphone
    CategoryType.INSURANCE -> Icons.Rounded.Shield
    CategoryType.TUITION -> Icons.Rounded.School
    CategoryType.SUBSCRIPTION -> Icons.Rounded.Subscriptions
    CategoryType.LOAN -> Icons.Rounded.AccountBalance
    CategoryType.SAVINGS -> Icons.Rounded.Savings
    CategoryType.HOA_FEES -> Icons.Rounded.Apartment
    else -> Icons.Rounded.AddCircle
}

private fun getCategoryColors(category: CategoryType, colors: com.patflow.app.core.theme.CategoryColors) = when (category) {
    CategoryType.ELECTRICITY -> colors.electricity
    CategoryType.WATER -> colors.water
    CategoryType.INTERNET -> colors.internet
    CategoryType.RENT -> colors.rent
    CategoryType.PHONE -> colors.phone
    CategoryType.INSURANCE -> colors.insurance
    CategoryType.TUITION -> colors.tuition
    CategoryType.SUBSCRIPTION -> colors.subscription
    CategoryType.LOAN -> colors.loan
    CategoryType.SAVINGS -> colors.savings
    CategoryType.HOA_FEES -> colors.hoaFees
    else -> colors.other
}
