package com.collogestion.ui.user

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.collogestion.R
import com.collogestion.network.AuthService
import com.collogestion.network.UsersService
import kotlinx.coroutines.launch


@SuppressLint("ResourceAsColor")
@Composable
@Preview
fun ProfileScreen(userViewModel: UserViewModel = viewModel()) {
    val userUiState by userViewModel.uiState.collectAsState()

    var oldPasswordText by rememberSaveable { mutableStateOf("") }
    var newPasswordText by rememberSaveable { mutableStateOf("") }

    var passwordChangeError: Boolean by rememberSaveable { mutableStateOf(false) }

    val lifecycleScope = LocalLifecycleOwner.current.lifecycleScope

    LaunchedEffect(Unit) {
        userViewModel.loadUser()
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(10.dp),
        color = Color.Black
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width((LocalConfiguration.current.screenWidthDp * 0.85).dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .background(color = Color(0xFF211F26))
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Image(
                            painterResource(id = R.drawable.profile),
                            contentDescription = "Profile Picture",
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.width(20.dp))
                        Text(
                            text = "${userUiState.user?.firstname} ${userUiState.user?.lastname}",
                            style = TextStyle(color = Color.White, fontSize = 30.sp)
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Email: ${userUiState.user?.mail}",
                        style = TextStyle(color = Color.LightGray, fontSize = 20.sp)
                    )
                    Text(
                        text = "Phone: ${userUiState.user?.phone}",
                        style = TextStyle(color = Color.LightGray, fontSize = 20.sp)
                    )
                    Text(
                        text = "House shares: ${userUiState.user?.houseShares?.size}",
                        style = TextStyle(color = Color.LightGray, fontSize = 20.sp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(15.dp))

            Button(onClick = { AuthService.logout() }) {
                Text("Logout")
            }

            Spacer(modifier = Modifier.height(50.dp))

            Text("Change password", style = TextStyle(color = Color.White, fontSize = 20.sp))

            Spacer(modifier = Modifier.height(15.dp))

            Card(
                modifier = Modifier
                    .background(color = Color.Black)
                    .border(
                        BorderStroke(width = 0.dp, color = Color(0xFF211F26)),
                    )
                    .fillMaxWidth()
            ) {
                Column {
                    TextField(
                        value = oldPasswordText,
                        onValueChange = { oldPasswordText = it },
                        label = { Text("Old password") },
                    )

                }
            }

            Spacer(modifier = Modifier.height(15.dp))

            Card(
                modifier = Modifier
                    .background(color = Color.Black)
                    .border(
                        BorderStroke(width = 0.dp, color = Color(0xFF211F26)),
                    )
                    .fillMaxWidth()
            ) {
                Column {
                    TextField(
                        value = newPasswordText,
                        onValueChange = { newPasswordText = it },
                        label = { Text("New password") },
                    )
                }
            }

            if (passwordChangeError) {
                Spacer(modifier = Modifier.height(15.dp))
                Text("Failed to change password", color = Color.Red)
            }

            Spacer(modifier = Modifier.height(15.dp))

            Button(onClick = {
                lifecycleScope.launch {
                    val success = UsersService.changePassword(newPasswordText, oldPasswordText)
                    if (!success) passwordChangeError = true
                    else {
                        oldPasswordText = ""
                        newPasswordText = ""
                        passwordChangeError = false
                    }
                }
            }) {
                Text("Change password")
            }


        }
    }
}