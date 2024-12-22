package com.example.fetchexercise.state

import com.example.fetchexercise.data.model.ListItem

sealed class GroupedListState {
    data object Loading : GroupedListState()
    data class Success(
        val groupedItems: Map<Int, List<ListItem>>,
        val filteredItems: Map<Int, List<ListItem>> = groupedItems
    ) : GroupedListState()
    data class Error(val message: String) : GroupedListState()
}