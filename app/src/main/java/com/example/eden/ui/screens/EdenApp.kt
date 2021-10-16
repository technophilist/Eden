package com.example.eden.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.eden.ui.components.EdenSearchBar
import com.example.eden.viewmodels.EdenHomeScreenViewModel
import com.example.eden.viewmodels.HomeScreenViewModel

@ExperimentalMaterialApi
@Composable
fun EdenApp(){
    val scaffoldState = rememberScaffoldState()
    var searchText by remember { mutableStateOf("") }
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            EdenSearchBar(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .fillMaxWidth(),
                label = { Text("Search") },
                onValueChange = { searchText = it },
                value = searchText,
            )
        },
        bottomBar = { EdenBottomNavigation() }
    ){
        Box(modifier = Modifier.padding(it)){
            HomeScreen(EdenHomeScreenViewModel())
        }
    }
}

@Composable
private fun EdenBottomNavigation(modifier: Modifier = Modifier) {
    BottomNavigation(
        modifier = modifier,
        elevation = 16.dp,
    ) {
        BottomNavigationItem(
            icon = {
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = " "
                )
            },
            selected = false,
            onClick = { /*TODO*/ },
            label = {
                Text(
                    text = "Home",
                    style = MaterialTheme.typography.caption,
                    color = MaterialTheme.colors.onPrimary
                )
            }
        )
        BottomNavigationItem(
            icon = {
                Icon(
                    imageVector = Icons.Outlined.FavoriteBorder,
                    contentDescription = ""
                )
            },
            selected = false,
            onClick = { /*TODO*/ },
            label = {
                Text(
                    text = "Favourites", style = MaterialTheme.typography.caption,
                    color = MaterialTheme.colors.onPrimary
                )
            }
        )
        BottomNavigationItem(
            icon = {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = " "
                )
            },
            selected = false,
            onClick = { /*TODO*/ },
            label = {
                Text(
                    text = "Profile", style = MaterialTheme.typography.caption,
                    color = MaterialTheme.colors.onPrimary
                )
            }
        )
        BottomNavigationItem(
            icon = {
                Icon(
                    imageVector = Icons.Filled.ShoppingCart,
                    contentDescription = ""
                )
            },
            selected = false,
            onClick = { /*TODO*/ },
            label = {
                Text(
                    text = "Cart", style = MaterialTheme.typography.caption,
                    color = MaterialTheme.colors.onPrimary
                )
            }
        )
    }
}