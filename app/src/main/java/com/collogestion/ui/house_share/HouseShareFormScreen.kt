package com.collogestion.ui.house_share

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.collogestion.data.User

@Composable
fun HouseShareFormScreen(
    navController: NavController,
    houseShareViewModel: HouseShareViewModel = viewModel(),
) {
    val houseShareUiState by houseShareViewModel.uiState.collectAsState()

    val update = houseShareUiState.selectedHouseShare != null
    var nameInput by remember { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (update) {
            Text(
                text = houseShareUiState.selectedHouseShare?.name ?: "",
                style = TextStyle(color = Color.White, fontSize = 30.sp),
                modifier = Modifier.padding(10.dp)
            )
            houseShareUiState.selectedHouseShare!!.users.forEach {
                HouseShareMember(it, houseShareUiState.selectedHouseShare!!.id, houseShareViewModel)
            }

        }
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
                            if (update) "Add user email" else "Enter house share name",
                            style = TextStyle(color = Color.White, fontSize = 15.sp)
                        )
                    }
                    innerTextField()
                }
            })
        Button(onClick = {
            if (update) {
                houseShareViewModel.addUserToHouseShare(nameInput, houseShareUiState.selectedHouseShare!!.id)
            } else {
                houseShareViewModel.addHouseShare(nameInput)
                if (houseShareUiState.errorMessage == null) {
                    navController.navigateUp()
                }
            }
        }) {
            Row(
                horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()
            ) {
                Text("Submit")
            }

        }
    }


}

@Composable
fun HouseShareMember(
    user: User,
    houseShareId: Int,
    houseShareViewModel: HouseShareViewModel = viewModel()
) {
    Row {
        Text(
            text = "${user.firstname} ${user.lastname}",
            style = TextStyle(color = Color.White, fontSize = 20.sp)
        )
        Button(onClick = {
            houseShareViewModel.removeUserFromHouseShare(user.id, houseShareId)
        }) {
            Text("Remove")
        }
    }
}