package com.collogestion.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.collogestion.R
import com.collogestion.network.AuthService
import com.collogestion.ui.Dashboard
import com.collogestion.ui.theme.ColloGestionTheme
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AuthService.initialize(this)
        lifecycleScope.launch {
            AuthService.login("string@email.com", "string")
        }
        setContent {
            ColloGestionTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = Color.DarkGray
                ) {
                    var selectedItem by remember { mutableIntStateOf(0) }
                    Scaffold(
                        bottomBar = {
                            NavigationBar(
                                containerColor = Color.DarkGray
                            ) {
                                val outlinedIconsList = listOf(
                                    Icons.Outlined.Home,
                                    Icons.Outlined.List,
                                    Icons.Outlined.AccountCircle
                                )
                                val items = listOf(
                                    resources.getString(R.string.project_nav_bar_title),
                                    resources.getString(R.string.personal_task_nav_bar_title),
                                    resources.getString(R.string.profile_nav_bar_title)
                                )
                                items.forEachIndexed { index, item ->
                                    NavigationBarItem(
                                        icon = {
                                            Icon(
                                                outlinedIconsList[index],
                                                contentDescription = item,
                                                tint = Color.White,
                                                modifier = Modifier.size(25.dp)
                                            )
                                        },
                                        label = {
                                            Text(
                                                item,
                                                style = TextStyle(
                                                    color = Color.White,
                                                    fontSize = 15.sp
                                                )
                                            )
                                        },
                                        selected = selectedItem == index,
                                        onClick = { selectedItem = index }
                                    )
                                }
                            }
                        },
                    ) { innerPadding ->
                        Column(
                            modifier = Modifier
                                .padding(innerPadding),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                        ) {
                            when (selectedItem) {
                                0 -> Dashboard(0, resources.getString(R.string.project_page_title))
                                1 -> Dashboard(
                                    1,
                                    resources.getString(R.string.personal_task_page_title)
                                )

                                2 -> Dashboard(2, resources.getString(R.string.profile_page_title))
                            }
                        }
                    }

                }
            }
        }
    }
}