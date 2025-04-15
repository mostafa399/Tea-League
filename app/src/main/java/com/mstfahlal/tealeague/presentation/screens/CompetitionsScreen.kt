package com.mstfahlal.tealeague.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.mstfahlal.tealeague.domain.model.DomainCompetition
import com.mstfahlal.tealeague.presentation.composables.CompetitionItem
import com.mstfahlal.tealeague.presentation.composables.EmptyContent
import com.mstfahlal.tealeague.presentation.viewmodel.CompetitionViewModel
import com.mstfahlal.tealeague.ui.theme.dimens
import com.mstfahlal.tealeague.utils.Resource


@Composable
fun CompetitionsScreen(
    viewModel: CompetitionViewModel = hiltViewModel(),
    onCompClick: (String) -> Unit
) {
    val context = LocalContext.current
    val competitionsState by viewModel.competitions.collectAsState()
    val swipeRefreshState =
        rememberSwipeRefreshState(isRefreshing = competitionsState is Resource.Loading)

    LaunchedEffect(Unit) {
        viewModel.fetchAllCompetitions(context)
    }

    CompetitionsScreenContent(
        comps = competitionsState.data?.competitions ?: emptyList(),
        updateComps = { viewModel.fetchAllCompetitions(context) },
        swipeRefreshState = swipeRefreshState,
        isLoading = competitionsState is Resource.Loading,
        onCompClick = { id ->
            viewModel.setCompetitionSelected(id)
            onCompClick(id)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompetitionsScreenContent(
    comps: List<DomainCompetition>,
    updateComps: () -> Unit,
    swipeRefreshState: SwipeRefreshState,
    isLoading: Boolean,
    onCompClick: (String) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Available competitions") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = updateComps
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.small3)
            ) {
                item {
                    Spacer(modifier = Modifier.height(MaterialTheme.dimens.small3))
                }

                when {

                    comps.isEmpty() -> {
                        item {
                            EmptyContent(
                                onReloadClick = updateComps
                            )
                        }
                    }

                    else -> {
                        items(items = comps, key = { it.id!! }) { comp ->
                            CompetitionItem(competition = comp, onCompClick = onCompClick)
                        }
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(MaterialTheme.dimens.medium4))
                }
            }
        }
    }
}