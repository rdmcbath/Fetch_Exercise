package com.example.fetchexercise.ui.components

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun ErrorState(
    message: String,
    onRetry: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    // Track whether the error was just shown to animate the entrance
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(message) {
        isVisible = true
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.widthIn(max = 300.dp) // Limit width for readability
            ) {
                // Error icon with attention-getting color
                Icon(
                    imageVector = Icons.Filled.Warning,
                    contentDescription = "Error occurred",
                    modifier = Modifier.size(48.dp),
                    tint = MaterialTheme.colorScheme.error
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Error message with clear formatting
                Text(
                    text = "Something went wrong",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Detailed error message
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Retry button with error color scheme
                Button(
                    onClick = {
                        isVisible = false // Trigger exit animation
                        onRetry()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error,
                        contentColor = MaterialTheme.colorScheme.onError
                    )
                ) {
                    Icon(
                        imageVector = Icons.Filled.Refresh,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Try Again")
                }
            }
        }
    }
}

// Preview for the error state with different scenarios
@Preview(showBackground = true)
@Composable
private fun ErrorStatePreview() {
    MaterialTheme {
        Column {
            // Network error preview
            ErrorState(
                message = "Unable to connect to the server. Please check your internet connection.",
                onRetry = {},
                modifier = Modifier.weight(1f)
            )

            // Data processing error preview
            ErrorState(
                message = "Failed to process the data. Please try again later.",
                onRetry = {},
                modifier = Modifier.weight(1f)
            )
        }
    }
}