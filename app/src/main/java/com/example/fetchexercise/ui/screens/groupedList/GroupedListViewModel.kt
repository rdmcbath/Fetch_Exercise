package com.example.fetchexercise.ui.screens.groupedList

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fetchexercise.data.model.ListItem
import com.example.fetchexercise.data.repository.ItemRepository
import com.example.fetchexercise.data.repository.ItemRepositoryImpl
import com.example.fetchexercise.state.GroupedListState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GroupedListViewModel(
    private val repository: ItemRepository = ItemRepositoryImpl()
) : ViewModel() {
    private val _uiState = MutableStateFlow<GroupedListState>(GroupedListState.Loading)
    val uiState: StateFlow<GroupedListState> = _uiState.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    private val _isFiltered = MutableStateFlow(false)
    val isFiltered = _isFiltered.asStateFlow()

    private val _isSorted = MutableStateFlow(false)
    val isSorted = _isSorted.asStateFlow()

    init {
        fetchDataBasic()
        //fetchDataRetrofit()
    }

    private fun fetchDataBasic() {
        viewModelScope.launch {
            _uiState.value = GroupedListState.Loading

            repository.fetchItemsBasicMethod()
                .onSuccess { items ->
                    Log.d("viewModel", "fetch Success")
//
                    val groupedItems = items.groupBy { it.listId }

                    _uiState.value = GroupedListState.Success(
                        groupedItems = groupedItems,
                        filteredItems = groupedItems, // will filter in separate method
                        sortedFilteredItems = groupedItems, // will sort in separate method
                    )
                }
                .onFailure { error ->
                    _uiState.value = GroupedListState.Error(
                        message = "Failed to load items: ${error.localizedMessage}"
                    )
                }
        }
    }

    private fun fetchDataRetrofit() {
        viewModelScope.launch {
            _uiState.value = GroupedListState.Loading

            repository.fetchItemsRetrofit()
                .onSuccess { items ->
                    // Process the items according to requirements
                    val processedItems = items
                        .filter { !it.name.isNullOrBlank() }
                        .sortedWith(
                            compareBy<ListItem> { it.listId }
                                .thenBy { item ->
                                    // extract the chars after "Item " and compare as Integers to sort correctly
                                    item.name?.substringAfter("Item ")
                                        ?.toIntOrNull() ?: 0 // Fallback to 0 if not a number
                                }
                        )
                        .groupBy { it.listId }

                    _uiState.value = GroupedListState.Success(
                        groupedItems = processedItems,
                        filteredItems = processedItems,
                        sortedFilteredItems = processedItems
                    )
                }
                .onFailure { error ->
                    _uiState.value = GroupedListState.Error(
                        message = "Failed to load items: ${error.localizedMessage}"
                    )
                }
        }
    }

    fun toggleFilter() {
        val currentState = _uiState.value
        if (currentState is GroupedListState.Success) {
            if (currentState.filteredItems == currentState.groupedItems) {
                val filtered = currentState.groupedItems.mapValues { (_, items) ->
                    items.filter { !it.name.isNullOrBlank() }
                }.filterValues { it.isNotEmpty() }

                _uiState.value = currentState.copy(filteredItems = filtered)
            } else {
                _uiState.value = currentState.copy(filteredItems = currentState.groupedItems)
            }

            _isFiltered.value = !isFiltered.value
        }
    }

    fun toggleFilterSort() {
        val currentState = _uiState.value
        if (currentState is GroupedListState.Success) {
            if (currentState.sortedFilteredItems == currentState.groupedItems) {
                val sortedFiltered = currentState.groupedItems.mapValues { (_, items) ->
                    items.filter { !it.name.isNullOrBlank() }
                    .sortedWith(
                        compareBy<ListItem> { it.listId }
                            .thenBy { item ->
                                // extract the chars after "Item " and compare as Integers to sort correctly
                                item.name?.substringAfter("Item ")
                                    ?.toIntOrNull() ?: 0 // Fallback to 0 if not a number
                            }
                    )
                }

                _uiState.value = currentState.copy(sortedFilteredItems = sortedFiltered)
            } else {
                _uiState.value = currentState.copy(sortedFilteredItems = currentState.groupedItems)
            }

            _isSorted.value = !isSorted.value
        }
    }

    fun refresh() {
        viewModelScope.launch {
            _isRefreshing.value = true
            try {
                fetchDataBasic()
            } finally {
                delay(300) // Brief delay to ensure smooth animation
                _isRefreshing.value = false
            }
        }
    }
}