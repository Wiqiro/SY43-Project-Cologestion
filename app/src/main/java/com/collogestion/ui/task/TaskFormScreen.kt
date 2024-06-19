package com.collogestion.ui.task

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.collogestion.ui.house_share.HouseShareViewModel
import com.collogestion.ui.shared.DropdownMenuField

@Composable
fun TaskFormScreen(
    navController: NavController,
    houseShareViewModel: HouseShareViewModel = viewModel(),
    taskViewModel: TaskViewModel = viewModel()
) {
    val houseShareUiState by houseShareViewModel.uiState.collectAsState()
    if (houseShareUiState.selectedHouseShare == null || houseShareUiState.selectedHouseShare?.users.isNullOrEmpty()) {
        return
    }

    val taskUiState by taskViewModel.uiState.collectAsState()

    val users = houseShareUiState.selectedHouseShare!!.users

    var nameInput by remember { mutableStateOf("") }
    var assigneeInput by remember { mutableStateOf(users.first().firstname) }

    var error by remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        BasicTextField(value = nameInput,
            onValueChange = { nameInput = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),

            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .background(
                            color = Color.Gray, shape = RoundedCornerShape(15.dp)
                        )
                        .padding(16.dp)
                ) {
                    if (nameInput.isEmpty()) {
                        Text(
                            "Enter task name",
                            style = TextStyle(color = Color.White, fontSize = 15.sp)
                        )
                    }
                    innerTextField()
                }
            })

        Text("Assignee", style = TextStyle(color = Color.White, fontSize = 15.sp))
        DropdownMenuField(label = "Assignee",
            options = houseShareUiState.selectedHouseShare!!.users.map { it.firstname },
            selectedOption = assigneeInput,
            onOptionSelected = { assigneeInput = it })

        Spacer(modifier = Modifier.height(16.dp))
        if (error) {
            Text("Error, please try again", color = Color.Red)
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                Log.w("OMG", users.find { it.firstname == assigneeInput }?.firstname.toString())
                taskViewModel.addTask(
                    nameInput,
                    users.find { it.firstname == assigneeInput }!!.id,
                    houseShareUiState.selectedHouseShare!!.id
                )
                if (taskUiState.errorMessage != null) {
                    error = true
                } else {
                    navController.navigateUp()
                }

            }) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Submit")
            }
        }
    }
}
