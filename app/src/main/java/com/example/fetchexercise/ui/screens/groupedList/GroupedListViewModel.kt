package com.example.fetchexercise.ui.screens.groupedList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.fetchexercise.data.model.ListItem
import com.example.fetchexercise.data.repository.ItemRepository
import com.example.fetchexercise.data.repository.ItemRepositoryImpl
import com.example.fetchexercise.state.GroupedListState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GroupedListViewModel(
    private val repository: ItemRepository = ItemRepositoryImpl()
) : ViewModel() {
    private val _uiState = MutableStateFlow<GroupedListState>(GroupedListState.Loading)
    val uiState: StateFlow<GroupedListState> = _uiState.asStateFlow()

    // loading state
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            _uiState.value = GroupedListState.Loading

            repository.fetchItems()
                .onSuccess { items ->
                    // Process the items according to requirements
                    val processedItems = items
                        .filter { !it.name.isNullOrBlank() }
                        .sortedWith(
                            compareBy<ListItem> { it.listId }
                                .thenBy { it.name }
                        )
                        .groupBy { it.listId }

                    _uiState.value = GroupedListState.Success(
                        groupedItems = processedItems,
                        filteredItems = processedItems
                    )
                }
                .onFailure { error ->
                    _uiState.value = GroupedListState.Error(
                        message = "Failed to load items: ${error.localizedMessage}"
                    )
                }
        }
    }

    // refresh data
    fun refresh() {
        viewModelScope.launch {
            _isRefreshing.value = true
            try {
                fetchData()
            } finally {
                _isRefreshing.value = false
            }
        }
    }
}