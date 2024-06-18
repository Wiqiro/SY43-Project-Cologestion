package com.collogestion.ui.user

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.collogestion.network.AuthService
import com.collogestion.network.UsersService
import kotlinx.coroutines.launch

@Composable
fun LoginScreen() {
    var isLogin by rememberSaveable { mutableStateOf(true) }

    Scaffold(
        Modifier
            .fillMaxSize(), containerColor = Color.Black
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            if (isLogin) {
                LoginForm()
            } else {
                RegisterForm(onSwitch = { isLogin = !isLogin })
            }
            Spacer(modifier = Modifier.height(15.dp))
            Button(onClick = { isLogin = !isLogin }) {
                Text(if (isLogin) "I dont have an account" else "I already have an account")
            }
        }
    }
}

@Composable
fun LoginForm() {
    var emailText by rememberSaveable { mutableStateOf("") }
    var passwordText by rememberSaveable { mutableStateOf("") }
    val lifecycleScope = LocalLifecycleOwner.current.lifecycleScope

    var loginFailed: Boolean by rememberSaveable { mutableStateOf(false) }

    Column {
        InputField(
            value = emailText,
            placeholder = "Email",
            keyboardType = KeyboardType.Email,
            onValueChange = { emailText = it }
        )
        Spacer(modifier = Modifier.height(15.dp))
        InputField(
            value = passwordText,
            placeholder = "Password",
            keyboardType = KeyboardType.Password,
            onValueChange = { passwordText = it }
        )
        Spacer(modifier = Modifier.height(15.dp))
        if (loginFailed) {
            Text("Incorrect username or password. Try again.", color = Color.Red)
        }
        Spacer(modifier = Modifier.height(15.dp))
        Button(
            onClick = {
                lifecycleScope.launch {
                    AuthService.login(emailText, passwordText)
                    loginFailed = !AuthService.isLoggedIn
                }
            }) {
            Text("Login")
        }

    }
}

@Composable
fun RegisterForm(onSwitch: () -> Unit) {
    var firstNameText by rememberSaveable { mutableStateOf("") }
    var lastNameText by rememberSaveable { mutableStateOf("") }
    var emailText by rememberSaveable { mutableStateOf("") }
    var phoneText by rememberSaveable { mutableStateOf("") }
    var passwordText by rememberSaveable { mutableStateOf("") }

    val lifecycleScope = LocalLifecycleOwner.current.lifecycleScope

    var registerFailed: Boolean by rememberSaveable { mutableStateOf(false) }


    Column {
        InputField(
            value = firstNameText,
            placeholder = "First Name",
            keyboardType = KeyboardType.Text,
            onValueChange = { firstNameText = it }
        )
        Spacer(modifier = Modifier.height(15.dp))
        InputField(
            value = lastNameText,
            placeholder = "Last Name",
            keyboardType = KeyboardType.Text,
            onValueChange = { lastNameText = it }
        )
        Spacer(modifier = Modifier.height(15.dp))
        InputField(
            value = emailText,
            placeholder = "Email",
            keyboardType = KeyboardType.Email,
            onValueChange = { emailText = it }
        )
        Spacer(modifier = Modifier.height(15.dp))
        InputField(
            value = phoneText,
            placeholder = "Phone",
            keyboardType = KeyboardType.Phone,
            onValueChange = { phoneText = it }
        )
        Spacer(modifier = Modifier.height(15.dp))
        InputField(
            value = passwordText,
            placeholder = "Password",
            keyboardType = KeyboardType.Password,
            onValueChange = { passwordText = it }
        )
        Spacer(modifier = Modifier.height(15.dp))
        if (registerFailed) {
            Text("Registration failed. Try again.", color = Color.Red)
        }
        Spacer(modifier = Modifier.height(15.dp))
        Button(
            onClick = {
                lifecycleScope.launch {
                    val result =
                        runCatching {
                            UsersService.addUser(
                                firstNameText,
                                lastNameText,
                                emailText,
                                phoneText,
                                passwordText
                            )
                        }
                    result.onSuccess {
                        AuthService.login(emailText, passwordText)
                        onSwitch()
                    }.onFailure {
                        registerFailed = true
                    }
                }
            }) {
            Text("Register")
        }
    }

}

@Composable
fun InputField(
    value: String,
    placeholder: String,
    keyboardType: KeyboardType,
    onEnter: () -> Unit = {},
    onValueChange: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .background(color = Color.Black)
            .border(
                BorderStroke(width = 0.dp, color = Color(0xFF211F26)),
            )
            .fillMaxWidth()
    ) {
        TextField(value = value,
            onValueChange = onValueChange,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = { onEnter() }
            ),
            visualTransformation = if (keyboardType == KeyboardType.Password) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },

            placeholder = { Text(placeholder) })
    }
}