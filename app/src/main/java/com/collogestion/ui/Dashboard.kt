package com.collogestion.ui


import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.collogestion.ui.house_share.HouseShareListScreen
import com.collogestion.ui.user.PersonalDashboardScreen
import com.collogestion.ui.user.ProfileScreen

@SuppressLint("ResourceAsColor")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dashboard(page: Int, pageName: String) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF211F26),
                    titleContentColor = Color.White,
                ),
                title = {
                    Text(pageName, style = TextStyle(fontSize = 20.sp))
                }
            )
        },
        floatingActionButton = {
            if (page == 0) {
                FloatingActionButton(onClick = {}) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Column(
                Modifier
                    .background(color = Color.Black)
                    .fillMaxSize()
                    .padding(15.dp)
                    .verticalScroll(
                        rememberScrollState()
                    )
            ) {
                when (page) {
                    0 -> HouseShareListScreen()
                    1 -> PersonalDashboardScreen()
                    2 -> ProfileScreen()
                }
            }
        }
    }
}