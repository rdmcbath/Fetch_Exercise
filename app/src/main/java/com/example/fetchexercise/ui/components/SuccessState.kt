package com.example.fetchexercise.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.fetchexercise.state.GroupedListState

@Composable
fun SuccessState(
    state: GroupedListState.Success,
    onToggleFilter: () -> Unit,
    onToggleFilterSort: () -> Unit,
    modifier: Modifier = Modifier,
    transitionSpec: FiniteAnimationSpec<IntOffset>,
) {
    val isFiltered = state.filteredItems != state.groupedItems
    val isSorted = state.sortedFilteredItems != state.groupedItems

    Column(
        modifier = modifier.fillMaxSize()
            .padding(vertical = 8.dp, horizontal = 16.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { onToggleFilter() }
            ) {
                Text(text = if (isFiltered) "No Filter" else "Filter")
            }

            Button(
                onClick = { onToggleFilterSort() }
            ) {
                Text(text = if (isSorted) "No Filter or Sort" else "Filter and Sort")
            }
        }
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val itemList = when {
                isSorted -> state.sortedFilteredItems
                isFiltered -> state.filteredItems
                else -> state.groupedItems
            }

            itemList.forEach { (listId, items) ->
                item(key = "header_$listId") {
                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn() + slideInVertically(animationSpec = transitionSpec)
                    ) {
                        GroupHeader(
                            listId = listId,
                            itemCount = items.size,
                            modifier = Modifier
                                .padding(top = if (listId == itemList.keys.first()) 0.dp else 16.dp)
                        )
                    }
                }

                // Add all items in the current group
                items(
                    items = items,
                    key = { item -> item.id },
                    contentType = { "list_item" }
                ) { item ->
                    ItemCard(
                        item = item,
                        modifier = Modifier.animateItem()
                    )
                }
            }
        }
    }
}

