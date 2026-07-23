package com.patflow.app.feature.income

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patflow.app.domain.model.IncomeSource
import com.patflow.app.domain.repository.IncomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IncomeSourceListViewModel @Inject constructor(
    private val repository: IncomeRepository
) : ViewModel() {

    val sources: StateFlow<List<IncomeSource>> = repository.getSources()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun deleteSource(id: Long) {
        viewModelScope.launch {
            repository.deleteSource(id)
        }
    }

    fun archiveSource(id: Long, archived: Boolean) {
        viewModelScope.launch {
            repository.archiveSource(id, archived)
        }
    }
}
