package com.example.fetchexercise.ui.screens.groupedList

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

    // loading state
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    init {
        fetchDataBasic()
        //fetchDataRetrofit()
    }

    private fun fetchDataBasic() {
        viewModelScope.launch {
            _uiState.value = GroupedListState.Loading

            // I realized that I initially made a mistake in the sorting logic. I was sorting using the entire name string
            repository.fetchItemsBasicMethod()
                .onSuccess { items ->
                    // Process the items according to requirements
                    val processedItems = items
                        .filter { !it.name.isNullOrBlank() }
//                        .sortedWith(
//                            compareBy<ListItem> { it.listId }
//                                .thenBy { it.name }
//                        )
                        .sortedWith(
                            compareBy<ListItem> { it.listId }
                                .thenBy { item ->
                                    // Need to extract the chars after "Item " and compare as Integers to sort correctly
                                    item.name?.substringAfter("Item ")
                                        ?.toIntOrNull() ?: 0 // Fallback to 0 if not a number
                                }
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

    private fun fetchDataRetrofit() {
        viewModelScope.launch {
            _uiState.value = GroupedListState.Loading

            // I realized that I initially made a mistake in the sorting logic. I was sorting using the entire name string
            repository.fetchItemsRetrofit()
                .onSuccess { items ->
                    // Process the items according to requirements
                    val processedItems = items
                        .filter { !it.name.isNullOrBlank() }
//                        .sortedWith(
//                            compareBy<ListItem> { it.listId }
//                                .thenBy { it.name }
//                        )
                        .sortedWith(
                            compareBy<ListItem> { it.listId }
                                .thenBy { item ->
                                    // Need to extract the chars after "Item " and compare as Integers to sort correctly
                                    item.name?.substringAfter("Item ")
                                        ?.toIntOrNull() ?: 0 // Fallback to 0 if not a number
                                }
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