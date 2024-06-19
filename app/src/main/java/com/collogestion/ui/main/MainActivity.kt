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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.collogestion.network.AuthService
import com.collogestion.ui.due.DueFormScreen
import com.collogestion.ui.due.DueViewModel
import com.collogestion.ui.event.EventFormScreen
import com.collogestion.ui.event.EventViewModel
import com.collogestion.ui.grocery.GroceryListFormScreen
import com.collogestion.ui.grocery.GroceryListScreen
import com.collogestion.ui.grocery.GroceryViewModel
import com.collogestion.ui.house_share.HouseShareDetailsScreen
import com.collogestion.ui.house_share.HouseShareListScreen
import com.collogestion.ui.house_share.HouseShareViewModel
import com.collogestion.ui.task.TaskViewModel
import com.collogestion.ui.theme.ColloGestionTheme
import com.collogestion.ui.user.LoginScreen
import com.collogestion.ui.user.PersonalDashboardScreen
import com.collogestion.ui.user.ProfileScreen
import com.collogestion.ui.user.UserViewModel


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AuthService.initialize(this)

        setContent {
            ColloGestionTheme {
                val isLoggedIn by AuthService.loggedIn.collectAsState(initial = false)

                if (isLoggedIn) {
                    LoggedInContent()
                } else {
                    LoginScreen()
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoggedInContent() {
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    Scaffold(
        topBar = {
            TopAppBar(colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFF211F26),
                titleContentColor = Color.White,
                navigationIconContentColor = Color.White
            ), title = {
                Text(
                    when {
                        currentRoute?.startsWith("house_share_details") == true -> "House Share Details"
                        currentRoute == "house_share" -> "House Share"
                        currentRoute == "personal" -> "Personal"
                        currentRoute == "profile" -> "Profile"

                        else -> "ColoGestion"
                    }, style = TextStyle(fontSize = 20.sp)
                )
            }, navigationIcon = {
                if (currentRoute?.startsWith("house_share_details") == true) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back"
                        )

                    }
                }
            })
        },
        bottomBar = {
            NavigationBar(containerColor = Color.DarkGray) {
                NavigationBarItem(selected = currentRoute?.startsWith(
                    "house_share"
                ) ?: false, onClick = {
                    navController.navigate("house_share") {
                        popUpTo(navController.graph.startDestinationRoute!!) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }, icon = {
                    Icon(
                        Icons.Outlined.Home, contentDescription = "Home", tint = Color.White
                    )
                }, label = {
                    Text(
                        "House share", style = TextStyle(
                            color = Color.White, fontSize = 15.sp
                        )
                    )
                })
                NavigationBarItem(selected = currentRoute == "personal", onClick = {
                    navController.navigate("personal") {
                        popUpTo(navController.graph.startDestinationRoute!!) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }, icon = {
                    Icon(
                        Icons.Outlined.Menu, contentDescription = "My tasks", tint = Color.White
                    )
                }, label = {
                    Text(
                        "Personal", style = TextStyle(
                            color = Color.White, fontSize = 15.sp
                        )
                    )
                })
                NavigationBarItem(selected = currentRoute == "profile", onClick = {
                    navController.navigate("profile") {
                        popUpTo(navController.graph.startDestinationRoute!!) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }, icon = {
                    Icon(
                        Icons.Outlined.Person, contentDescription = "Profile", tint = Color.White
                    )
                }, label = {
                    Text(
                        "Profile", style = TextStyle(
                            color = Color.White, fontSize = 15.sp
                        )
                    )
                })
            }

        },
        floatingActionButton = {
            if (currentRoute == "house_share") {
                FloatingActionButton(onClick = {}) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                }
            }
        },
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.Black,
    ) { innerPadding ->
        val houseShareViewModel: HouseShareViewModel = viewModel()
        val groceryViewModel: GroceryViewModel = viewModel()
        val taskViewModel: TaskViewModel = viewModel()
        val eventViewModel: EventViewModel = viewModel()
        val dueViewModel: DueViewModel = viewModel()
        val userViewModel: UserViewModel = viewModel()

        NavHost(
            navController,
            startDestination = "house_share",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("house_share") {
                HouseShareListScreen(
                    navController, houseShareViewModel, userViewModel
                )
            }
            composable("house_share_details/{id}") { backStackEntry ->
                val id = backStackEntry.arguments?.getString("id")?.toIntOrNull()
                if (id != null) {
                    HouseShareDetailsScreen(
                        navController,
                        id,
                        houseShareViewModel,
                        groceryViewModel,
                        taskViewModel,
                        eventViewModel,
                        dueViewModel
                    )
                }
            }
            composable("house_share_details/grocery/{id}") { backStackEntry ->
                val id = backStackEntry.arguments?.getString("id")?.toIntOrNull()
                if (id != null) {
                    GroceryListScreen(id, groceryViewModel)
                }
            }
            composable("personal") {
                PersonalDashboardScreen(
                    userViewModel, taskViewModel, dueViewModel
                )
            }
            composable("profile") { ProfileScreen() }
            composable("house_share_details/add_due") {
                DueFormScreen(navController, houseShareViewModel, dueViewModel)
            }
            composable("house_share_details/add_event") {
                EventFormScreen(navController, houseShareViewModel, eventViewModel)
            }
            composable("house_share_details/add_grocery") {
                GroceryListFormScreen(navController, houseShareViewModel, groceryViewModel)
            }

        }
    }
}
