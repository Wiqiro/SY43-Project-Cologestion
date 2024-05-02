package com.collogestion

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.collogestion.services.AuthService
import com.collogestion.services.HttpClient
import com.collogestion.services.UsersService
import com.collogestion.ui.theme.ColloGestionTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AuthService.initialize(this)
        setContent {
            ColloGestionTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {

    Column {
        Button(onClick = {
            UsersService.getUser(1) { user ->
                Log.d("User", "${user.firstname} ${user.lastname} ${user.mail} ${user.phone} ${user.profilePicture}")
            }
        }) {
            Text(text = "Query")
        }
        Button(onClick = {
            AuthService.login("string@email.com", "string") {}
        }) {
            Text(text = "Login")
        }
        Button(onClick = {
            AuthService.logout()
        }) {
            Text(text = "Logout")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ColloGestionTheme {
        Greeting("Android")
    }
}