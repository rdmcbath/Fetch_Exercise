import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.fetchexercise.state.GroupedListState
import com.example.fetchexercise.ui.components.EmptyState
import com.example.fetchexercise.ui.components.ErrorState
import com.example.fetchexercise.ui.components.LoadingState
import com.example.fetchexercise.ui.components.SuccessState
import com.example.fetchexercise.ui.screens.groupedList.GroupedListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupedListScreen(
    viewModel: GroupedListViewModel,
) {
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    val pullToRefreshState = rememberPullToRefreshState()

    Column(
        modifier = Modifier
            .padding(vertical = 24.dp)
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
            Text(
                text = "Fetch Coding Exercise",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(bottom = 8.dp)
            )

        PullToRefreshBox(
            state = pullToRefreshState,
            modifier = Modifier.fillMaxSize(),
            isRefreshing = isRefreshing,
            onRefresh = { viewModel.refresh() },
            indicator = {
                Indicator(
                    modifier = Modifier.align(Alignment.TopCenter),
                    isRefreshing = isRefreshing,
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    state = pullToRefreshState
                )
            },
        ) {
            when (val state = uiState) {
                is GroupedListState.Loading -> LoadingState()
                is GroupedListState.Success -> {
                    if (state.filteredItems.isEmpty()) {
                            EmptyState(
                                title = "No Items Yet",
                                message = "Pull down to refresh or add items to get started"
                            )
                    } else {
                        SuccessState(
                            groupedItems = state.filteredItems,
                            transitionSpec = tween(1000, easing = EaseInOut)
                        )
                    }
                }

                is GroupedListState.Error -> ErrorState(
                    message = state.message,
                    onRetry = { viewModel.refresh() }
                )
            }
        }
    }
}
