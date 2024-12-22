package com.example.fetchexercise.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.List
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun EmptyState(
    title: String = "No Items Found",
    message: String = "Items you add will appear here",
    modifier: Modifier = Modifier
) {
    // Center the empty state content both vertically and horizontally
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.widthIn(max = 300.dp) // Limit width for better readability
        ) {
            // Display a list icon to visually represent emptiness
            Icon(
                imageVector = Icons.Outlined.List,
                contentDescription = "Empty list",
                modifier = Modifier.size(72.dp),
                tint = MaterialTheme.colorScheme.secondary.copy(alpha = 0.6f)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Main empty state message
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Supporting text to guide the user
            Text(
                text = message,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}