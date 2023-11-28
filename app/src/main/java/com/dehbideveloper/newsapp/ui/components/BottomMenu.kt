package com.dehbideveloper.newsapp.ui.components

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.dehbideveloper.newsapp.BottomMenuScreen

@Composable
fun BottomMenu(navController: NavController){
    val menuItems = listOf(BottomMenuScreen.TopNews, BottomMenuScreen.Categories, BottomMenuScreen.Sources)

    BottomNavigation {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        menuItems.forEach{
            BottomNavigationItem(
                label = { Text(text = it.title)},
                alwaysShowLabel = true,
                selected = currentRoute == it.route,
                onClick = {
                          navController.navigate(it.route){
                              navController.graph.startDestinationRoute?.let {
                                  route ->
                                  popUpTo(route){
                                      saveState = true
                                  }
                              }
                              launchSingleTop = true
                              restoreState = true
                          }
                },
                icon = {Icon(imageVector = it.icon, contentDescription = it.title)})
        }

    }
}