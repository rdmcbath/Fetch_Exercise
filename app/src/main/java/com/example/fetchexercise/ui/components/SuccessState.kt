package com.example.fetchexercise.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.fetchexercise.data.model.ListItem

@Composable
fun SuccessState(
    groupedItems: Map<Int, List<ListItem>>,
    modifier: Modifier = Modifier,
    transitionSpec: FiniteAnimationSpec<IntOffset>
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        groupedItems.forEach { (listId, items) ->
            item(key = "header_$listId") {
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn() + slideInVertically(animationSpec = transitionSpec)
                ) {
                    GroupHeader(
                        listId = listId,
                        itemCount = items.size,
                        modifier = Modifier
                            .padding(top = if (listId == groupedItems.keys.first()) 0.dp else 16.dp)
                    )
                }
            }

            // Add all items in the current group
            items(
                items = items,
                key = { item -> item.id },
                contentType = { "list_item" }
            ) {
                item ->
                ItemCard(
                    item = item,
                    modifier = Modifier.animateItem()
                )
            }
        }
    }
}

