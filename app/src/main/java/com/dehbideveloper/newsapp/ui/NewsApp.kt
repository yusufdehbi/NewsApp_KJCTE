package com.dehbideveloper.newsapp.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dehbideveloper.newsapp.BottomMenuScreen
import com.dehbideveloper.newsapp.MockData
import com.dehbideveloper.newsapp.ui.components.BottomMenu
import com.dehbideveloper.newsapp.ui.screen.Categories
import com.dehbideveloper.newsapp.ui.screen.DetailScreen
import com.dehbideveloper.newsapp.ui.screen.Sources
import com.dehbideveloper.newsapp.ui.screen.TopNews

@Composable
fun NewsApp(){
    val navController = rememberNavController()
    val scrollState = rememberScrollState()
    MainScreen(navController = navController, scrollState = scrollState)

}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(navController: NavHostController, scrollState: ScrollState){

    Scaffold(bottomBar = { BottomMenu(navController = navController)}) {
        Navigation(navController, scrollState)
    }
}

@Composable
fun Navigation(navController: NavHostController, scrollState: ScrollState){
    NavHost(navController = navController, startDestination = "TopNews"){
        bottomNavigation(navController)
        composable("TopNews"){
            TopNews(navController = navController)
        }
        composable("Detail/{newsId}",
            arguments = listOf(navArgument("newsId"){type = NavType.IntType})
        ){
            navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getInt("newsId")
            val newsData = MockData.getNews(id)
            DetailScreen(newsData, scrollState, navController = navController)
        }
    }
}

fun NavGraphBuilder.bottomNavigation(navController: NavController){
    composable(BottomMenuScreen.TopNews.route){
        TopNews(navController = navController)
    }

    composable(BottomMenuScreen.Categories.route){
        Categories()
    }

    composable(BottomMenuScreen.Sources.route){
        Sources()
    }
}

