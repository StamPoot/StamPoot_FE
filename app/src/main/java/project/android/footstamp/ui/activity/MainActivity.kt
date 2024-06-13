package project.android.footstamp.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import project.android.footstamp.R
import dagger.hilt.android.AndroidEntryPoint
import project.android.footstamp.ui.activity.MainActivity.Companion.screens
import project.android.footstamp.ui.base.BaseActivity
import project.android.footstamp.ui.components.BodyText
import project.android.footstamp.ui.theme.FootStampTheme
import project.android.footstamp.ui.theme.MainColor
import project.android.footstamp.ui.theme.SubColor
import project.android.footstamp.ui.view.board.screen.BoardScreen
import project.android.footstamp.ui.view.gallery.screen.GalleryScreen
import project.android.footstamp.ui.view.map.screen.MapScreen
import project.android.footstamp.ui.view.profile.screen.ProfileScreen

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        setContent {
            FootStampTheme(darkTheme = false) {
                MainView(mainViewModel)
            }
        }
    }

    companion object {
        val screens = listOf(
            NavigationItem(name = "map", iconRoute = R.drawable.icon_map, "tab_map"),
            NavigationItem(name = "gallery", iconRoute = R.drawable.icon_gallery, "tab_gallery"),
            NavigationItem(name = "board", iconRoute = R.drawable.icon_board, "tab_board"),
            NavigationItem(name = "profile", iconRoute = R.drawable.icon_profile, "tab_profile")
        )

    }
}

@Composable
fun MainView(viewModel: MainViewModel) {
    val isLoading by viewModel.isLoading.collectAsState()
    val navController = rememberNavController()
    val navItems = screens

    Scaffold(
        bottomBar = { BottomNavigation(navController, navItems) }) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = navItems.first().route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("tab_map") { MapScreen() }
            composable("tab_gallery") { GalleryScreen() }
            composable("tab_board") { BoardScreen() }
            composable("tab_profile") { ProfileScreen() }
        }
    }
}

@Composable
fun BottomNavigation(navController: NavHostController, navItems: List<NavigationItem>) {
    BottomAppBar {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            navItems.forEach { nav ->
                val currentRoute =
                    navController.currentBackStackEntryAsState().value?.destination?.route
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                        .clickable { navController.navigate(nav.route) }) {
                    val isCurrentRoute = nav.route == currentRoute
                    Icon(
                        painter = painterResource(nav.iconRoute),
                        contentDescription = nav.name,
                        tint = if (isCurrentRoute) MainColor else SubColor
                    )
                    if (isCurrentRoute) {
                        Spacer(modifier = Modifier.height(2.dp))
                        BodyText(nav.name, MainColor)
                    }
                }
            }
        }
    }
}

data class NavigationItem(
    val name: String, val iconRoute: Int, val route: String
)