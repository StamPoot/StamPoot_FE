package com.example.footstamp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.footstamp.MainActivity.Companion.screens
import com.example.footstamp.ui.components.BodyText
import com.example.footstamp.ui.screen.board.BoardScreen
import com.example.footstamp.ui.screen.gallery.GalleryScreen
import com.example.footstamp.ui.screen.map.MapScreen
import com.example.footstamp.ui.screen.profile.ProfileScreen
import com.example.footstamp.ui.theme.FootStampTheme
import com.example.footstamp.ui.theme.MainColor
import com.example.footstamp.ui.theme.SubColor
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        setContent {
            FootStampTheme(darkTheme = false) {
                MainView()
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainView() {
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FootStampTheme {
        MainView()
    }
}