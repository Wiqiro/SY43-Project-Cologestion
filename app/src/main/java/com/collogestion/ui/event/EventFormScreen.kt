package com.collogestion.ui.event

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
import com.collogestion.ui.shared.DateTimePickerField
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date

@Composable
fun EventFormScreen(
    navController: NavController,
    houseShareViewModel: HouseShareViewModel = viewModel(),
    eventViewModel: EventViewModel = viewModel()
) {
    val houseShareUiState by houseShareViewModel.uiState.collectAsState()
    if (houseShareUiState.selectedHouseShare == null || houseShareUiState.selectedHouseShare?.users.isNullOrEmpty()) {
        return
    }

    val eventUiState = eventViewModel.uiState.collectAsState()

    var titleInput by remember { mutableStateOf("") }
    var durationInput by remember { mutableStateOf("") }
    var dateInput by remember { mutableStateOf(LocalDateTime.now()) }


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
                            "Enter event title",
                            style = TextStyle(color = Color.White, fontSize = 15.sp)
                        )
                    }
                    innerTextField()
                }
            })

        DateTimePickerField(
            selectedDateTime = dateInput,
            onDateTimeSelected = { dateInput = it }
        )

        BasicTextField(value = durationInput,
            onValueChange = { durationInput = it },
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
                    if (durationInput.isEmpty()) {
                        Text(
                            "Enter event duration",
                            style = TextStyle(color = Color.White, fontSize = 15.sp)
                        )
                    }
                    innerTextField()
                }
            })



        Spacer(modifier = Modifier.height(16.dp))
        if (eventUiState.value.errorMessage != null) {
            Text("Error, please try again", color = Color.Red)
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val date = Date.from(
                    dateInput.atZone(ZoneId.systemDefault()).toInstant()
                )
                Log.d("EventFormScreen", date.time.toString())
                eventViewModel.addEvent(
                    date,
                    titleInput,
                    durationInput.toInt(),
                    houseShareUiState.selectedHouseShare!!.id
                )

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
