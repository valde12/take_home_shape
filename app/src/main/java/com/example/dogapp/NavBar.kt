package com.example.dogapp

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

data class BottomNavItem(
    val name: String,
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
)

@Composable
fun BottomNavBar(navController: NavController) {

    //List of items for the navigation bar
    val bottomNavItems = listOf(
        BottomNavItem(
            name = "Home",
            route = "home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home
        ),
        BottomNavItem(
            name = "Favorites",
            route = "favorites",
            selectedIcon = Icons.Filled.Favorite,
            unselectedIcon = Icons.Filled.FavoriteBorder
        )
    )

    var selectedItemIndex by rememberSaveable {
        mutableStateOf(0)
    }

    val contentColor = MaterialTheme.colorScheme.onSurface
    val containerColor = MaterialTheme.colorScheme.surface
    val indicatorColor = MaterialTheme.colorScheme.secondaryContainer

    val navStackBackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navStackBackEntry?.destination?.route
    selectedItemIndex = bottomNavItems.indexOfFirst { it.route == currentRoute }.coerceAtLeast(0)

    NavigationBar(
        containerColor = containerColor,
        tonalElevation = 0.dp,
    ) {
        bottomNavItems.forEachIndexed { index, item ->
            NavigationBarItem(
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = contentColor,
                    unselectedIconColor = contentColor,
                    indicatorColor = indicatorColor
                ),
                selected = selectedItemIndex == index,
                onClick = {
                    selectedItemIndex = index
                    navController.navigate(item.route)
                },
                label = {
                    Text(text = item.name, color = contentColor)
                },
                icon = {
                    Icon(
                        imageVector = if (index == selectedItemIndex) {
                            item.selectedIcon
                        } else item.unselectedIcon,
                        contentDescription = item.name
                    )
                }
            )
        }
    }
}