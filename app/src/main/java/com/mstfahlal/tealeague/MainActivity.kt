package com.mstfahlal.tealeague

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mstfahlal.tealeague.presentation.screens.CompetitionDetailsScreen
import com.mstfahlal.tealeague.presentation.screens.CompetitionsScreen
import com.mstfahlal.tealeague.presentation.viewmodel.CompetitionViewModel
import com.mstfahlal.tealeague.ui.theme.TeaLeagueTheme
import com.mxalbert.sharedelements.SharedElementsRoot
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            TeaLeagueTheme {
                Scaffold(
                    contentWindowInsets = WindowInsets.navigationBars,
                    modifier = Modifier.fillMaxSize(),
                    containerColor = MaterialTheme.colorScheme.background
                ){
                    SharedElementsRoot {
                        AppNavigation(paddingValues = it)
                    }
                }

            }
        }
    }
}

@Composable
fun AppNavigation(paddingValues: PaddingValues) {
    val navController = rememberNavController()
    val viewModel: CompetitionViewModel = hiltViewModel()


    NavHost(
        navController = navController,
        startDestination = "competitions",
        modifier = Modifier.padding(paddingValues)
    ) {
        composable("competitions") {
            CompetitionsScreen(
                viewModel = viewModel ,
                onCompClick = { competitionId ->
                    viewModel.setCompetitionSelected(competitionId)
                    navController.navigate("competitionDetails/$competitionId")
                }
            )
        }
        composable("competitionDetails/{competitionId}") { backStackEntry ->
            val competitionId = backStackEntry.arguments?.getString("competitionId") ?: ""
            CompetitionDetailsScreen(onBack = { navController.popBackStack() }, competitionId = competitionId)
        }
    }
}

