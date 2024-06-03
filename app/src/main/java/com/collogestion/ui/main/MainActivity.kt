package com.collogestion.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.collogestion.data.HouseShare
import com.collogestion.network.AuthService
import com.collogestion.ui.house_share.HouseShareDetailsScreen
import com.collogestion.ui.house_share.HouseShareListScreen
import com.collogestion.ui.theme.ColloGestionTheme
import com.collogestion.ui.user.PersonalDashboardScreen
import com.collogestion.ui.user.ProfileScreen
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AuthService.initialize(this)
        lifecycleScope.launch {
            AuthService.login("string@email.com", "string")
        }
        setContent {
            ColloGestionTheme {
                val navController = rememberNavController()
                val currentRoute =
                    navController.currentBackStackEntryAsState().value?.destination?.route
                Scaffold(
                    topBar = {

                        TopAppBar(
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = Color(0xFF211F26),
                                titleContentColor = Color.White,
                                navigationIconContentColor = Color.White
                            ),
                            title = {
                                Text(
                                    when (currentRoute) {
                                        "house_share" -> "House Share"
                                        "house_share_details" -> "House Share Details"
                                        "personal" -> "Personal"
                                        "profile" -> "Profile"
                                        else -> "ColoGestion"
                                    }, style = TextStyle(fontSize = 20.sp)
                                )
                            },
                            navigationIcon = {
                                if (currentRoute == "house_share_details") {
                                    IconButton(onClick = { navController.popBackStack() }) {
                                        Icon(
                                            Icons.AutoMirrored.Filled.ArrowBack,
                                            contentDescription = "Back"
                                        )

                                    }
                                }
                            }
                        )
                    },
                    floatingActionButton = {
                        if (/*page == 0*/true) {
                            FloatingActionButton(onClick = {}) {
                                Icon(Icons.Default.Add, contentDescription = "Add")
                            }
                        }
                    },
                    bottomBar = {
                        NavigationBar(containerColor = Color.DarkGray) {
                            NavigationBarItem(
                                selected = currentRoute == "house_share" || currentRoute == "house_share_details",
                                onClick = {
                                    navController.navigate("house_share") {
                                        popUpTo(navController.graph.startDestinationRoute!!) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                icon = {
                                    Icon(
                                        Icons.Outlined.Home, contentDescription = "Home",
                                        tint = Color.White
                                    )
                                },
                                label = {
                                    Text(
                                        "Home",
                                        style = TextStyle(
                                            color = Color.White,
                                            fontSize = 15.sp
                                        )
                                    )
                                }
                            )
                            NavigationBarItem(
                                selected = currentRoute == "personal",
                                onClick = {
                                    navController.navigate("personal") {
                                        popUpTo(navController.graph.startDestinationRoute!!) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                icon = {
                                    Icon(
                                        Icons.Outlined.Menu, contentDescription = "Personal",
                                        tint = Color.White
                                    )
                                },
                                label = {
                                    Text(
                                        "Personal",
                                        style = TextStyle(
                                            color = Color.White,
                                            fontSize = 15.sp
                                        )
                                    )
                                }
                            )
                            NavigationBarItem(
                                selected = currentRoute == "profile",
                                onClick = {
                                    navController.navigate("profile") {
                                        popUpTo(navController.graph.startDestinationRoute!!) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                icon = {
                                    Icon(
                                        Icons.Outlined.Person, contentDescription = "Profile",
                                        tint = Color.White
                                    )
                                },
                                label = {
                                    Text(
                                        "Profile",
                                        style = TextStyle(
                                            color = Color.White,
                                            fontSize = 15.sp
                                        )
                                    )
                                }
                            )
                        }

                    }, modifier = Modifier.fillMaxSize(), containerColor = Color.Black
                ) { innerPadding ->
                    val dummyHouseShare =
                        HouseShare(
                            1,
                            "House Share 1",
                            "/",
                            0,
                            emptyList(),
                            emptyList(),
                            emptyList()
                        )
                    NavHost(
                        navController, startDestination = "house_share",
                        modifier = Modifier
                            .padding(innerPadding),
                    ) {
                        composable("house_share") { HouseShareListScreen(navController) }
                        composable("house_share_details") { HouseShareDetailsScreen(dummyHouseShare) }
                        composable("personal") { PersonalDashboardScreen() }
                        composable("profile") { ProfileScreen() }

                    }
                }
            }
        }
    }
}