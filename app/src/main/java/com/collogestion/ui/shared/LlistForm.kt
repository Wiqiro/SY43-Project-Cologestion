package com.collogestion.ui.shared

import android.app.TimePickerDialog
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ListForm(
    itemList: MutableList<Any>,
    showFloatInput: Boolean,
    showDropdownInputs: Boolean,
    showDateTimePicker: Boolean
) {
    var textInput by remember { mutableStateOf(TextFieldValue("")) }
    var floatInput by remember { mutableStateOf(TextFieldValue("")) }
    var buyer by remember { mutableStateOf("") }
    var forWho by remember { mutableStateOf("") }
    var selectedDateTime by remember { mutableStateOf(LocalDateTime.now()) }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()) {
        BasicTextField(
            value = textInput,
            onValueChange = { textInput = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .background(color = Color.Gray, shape = RoundedCornerShape(15.dp))
                        .padding(16.dp)
                ) {
                    if (textInput.text.isEmpty()) {
                        Text("Enter text", style = TextStyle(color = Color.White, fontSize = 15.sp))
                    }
                    innerTextField()
                }
            }
        )

        if (showFloatInput) {
            BasicTextField(
                value = floatInput,
                onValueChange = { floatInput = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                decorationBox = { innerTextField ->
                    Row(
                        modifier = Modifier
                            .background(color = Color.Gray, shape = RoundedCornerShape(15.dp))
                            .padding(16.dp)
                    ) {
                        if (floatInput.text.isEmpty()) {
                            Text("Enter float value", style = TextStyle(color = Color.White, fontSize = 15.sp))
                        }
                        innerTextField()
                    }
                }
            )
        }

        // Dropdown Inputs
        if (showDropdownInputs) {
            DropdownMenuField(
                label = "Buyer",
                options = listOf("Option 1", "Option 2", "Option 3"),
                selectedOption = buyer,
                onOptionSelected = { buyer = it }
            )

            DropdownMenuField(
                label = "For who",
                options = listOf("Option A", "Option B", "Option C"),
                selectedOption = forWho,
                onOptionSelected = { forWho = it }
            )
        }

        if (showDateTimePicker) {
            DateTimePickerField(
                selectedDateTime = selectedDateTime,
                onDateTimeSelected = { selectedDateTime = it }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                itemList.add(textInput.text)
                if (showFloatInput) {
                    itemList.add(floatInput.text.toFloatOrNull() ?: 0f)
                }
                if (showDropdownInputs) {
                    itemList.add(buyer)
                    itemList.add(forWho)
                }
                if (showDateTimePicker) {
                    itemList.add(selectedDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                }
            }
        ) {
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                Text("Submit")
            }
        }
    }
}

@Composable
fun DropdownMenuField(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .background(color = Color.Gray, shape = RoundedCornerShape(15.dp))) {
        TextButton(
            onClick = { expanded = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = selectedOption.ifEmpty { label }, style = TextStyle(color = Color.White))
            Icon(Icons.Filled.KeyboardArrowDown, contentDescription = "Down Arrow", tint = Color.White)
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DateTimePickerField(
    selectedDateTime: LocalDateTime,
    onDateTimeSelected: (LocalDateTime) -> Unit
) {
    val context = LocalContext.current
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
    val formattedDate = selectedDateTime.format(formatter)

    Box(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        TextButton(
            onClick = {
                val calendar = Calendar.getInstance()
                val datePickerDialog = android.app.DatePickerDialog(
                    context,
                    { _, year, month, dayOfMonth ->
                        val newDate = LocalDateTime.of(
                            year,
                            month + 1,
                            dayOfMonth,
                            selectedDateTime.hour,
                            selectedDateTime.minute
                        )
                        onDateTimeSelected(newDate)
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                )
                datePickerDialog.setOnDismissListener {
                    val timePickerDialog = TimePickerDialog(
                        context,
                        { _, hourOfDay, minute ->
                            val newDateTime = selectedDateTime.withHour(hourOfDay).withMinute(minute)
                            onDateTimeSelected(newDateTime)
                        },
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        true
                    )
                    timePickerDialog.show()
                }
                datePickerDialog.show()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = formattedDate)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun ListFormPreview() {
    ListForm(
        itemList = mutableListOf(),
        showFloatInput = true,
        showDropdownInputs = true,
        showDateTimePicker = true
    )
}
