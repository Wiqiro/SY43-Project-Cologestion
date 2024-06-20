package com.collogestion.ui.due

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
fun DueFormScreen(
    navController: NavController,
    houseShareViewModel: HouseShareViewModel = viewModel(),
    dueViewModel: DueViewModel = viewModel()
) {
    val houseShareUiState by houseShareViewModel.uiState.collectAsState()
    if (houseShareUiState.selectedHouseShare == null || houseShareUiState.selectedHouseShare?.users.isNullOrEmpty()) {
        return
    }

    val dueUiState by dueViewModel.uiState.collectAsState()

    val users = houseShareUiState.selectedHouseShare!!.users

    var amountInput by remember { mutableStateOf("") }
    var titleInput by remember { mutableStateOf("") }
    var creditorInput by remember { mutableStateOf(users.first().firstname) }
    var debtorInput by remember { mutableStateOf(users.first().firstname) }

    var error by remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        BasicTextField(value = titleInput,
            onValueChange = { titleInput = it },
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
                    if (titleInput.isEmpty()) {
                        Text(
                            "Enter text",
                            style = TextStyle(color = Color.White, fontSize = 15.sp)
                        )
                    }
                    innerTextField()
                }
            })

        BasicTextField(value = amountInput,
            onValueChange = { amountInput = it },
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
                    if (amountInput.isEmpty()) {
                        Text(
                            "Enter due amount",
                            style = TextStyle(color = Color.White, fontSize = 15.sp)
                        )
                    }
                    innerTextField()
                }
            })

        Text("Debtor", style = TextStyle(color = Color.White, fontSize = 15.sp))
        DropdownMenuField(label = "Debtor",
            options = houseShareUiState.selectedHouseShare!!.users.map { it.firstname },
            selectedOption = debtorInput,
            onOptionSelected = { debtorInput = it })

        Text("Creditor", style = TextStyle(color = Color.White, fontSize = 15.sp))
        DropdownMenuField(label = "Creditor",
            options = houseShareUiState.selectedHouseShare!!.users.map { it.firstname },
            selectedOption = creditorInput,
            onOptionSelected = { creditorInput = it })

        Spacer(modifier = Modifier.height(16.dp))
        if (error) {
            Text("Error, please try again", color = Color.Red)
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                dueViewModel.addDue(
                    titleInput,
                    amountInput.toDouble(),
                    users.find { it.firstname == creditorInput }?.id ?: 1,
                    users.find { it.firstname == debtorInput }?.id ?: 1,
                    houseShareUiState.selectedHouseShare!!.id
                )
                if (dueUiState.errorMessage != null) {
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
