package com.collogestion.ui.grocery

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import com.collogestion.data.GroceryListItem

@Composable
fun GroceryListScreen(
    id: Int, groceryViewModel: GroceryViewModel = viewModel()
) {
    val groceryUiState by groceryViewModel.uiState.collectAsState()
    groceryViewModel.selectGroceryList(id)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)  // Background noir
            .padding(16.dp)
    ) {
        Text(
            text = groceryUiState.selectedGroceryList?.name ?: "Unnamed",
            style = TextStyle(fontSize = 20.sp, color = Color.White)
        )
        ItemInput(id, groceryViewModel)

        if (!groceryUiState.selectedGroceryList?.items.isNullOrEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                items(groceryUiState.selectedGroceryList?.items!!) { item ->
                    GroceryItemCard(item, groceryViewModel)
                }
            }
        }
    }
}

@Composable
fun GroceryItemCard(item: GroceryListItem, groceryViewModel: GroceryViewModel = viewModel()) {
    var isChecked by remember { mutableStateOf(item.bought) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(color = Color.Gray, shape = RoundedCornerShape(15.dp))  // Background gris
            .border(width = 2.dp, color = Color.Black, shape = RoundedCornerShape(15.dp))
            .padding(8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = isChecked, onCheckedChange = {
                isChecked = it
                item.bought = it
            })

            Spacer(modifier = Modifier.width(8.dp))

            Text(text = "${item.quantity} x ${item.name}", color = Color.White)
        }

        Button(
            onClick = { groceryViewModel.deleteGroceryItem(item.id) }, colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent, contentColor = Color.White
            ), modifier = Modifier.background(color = Color.Transparent)
        ) {
            Icon(
                Icons.Filled.Clear,
                contentDescription = "Clear Button",
                tint = Color.White,
                modifier = Modifier.background(color = Color.Transparent)
            )
        }
    }
}

@Composable
fun ItemInput(
    id: Int,
    groceryViewModel: GroceryViewModel = viewModel()
) {
    var nameInput by remember { mutableStateOf("") }
    var quantityInput by remember { mutableStateOf("") }
    Row(verticalAlignment = Alignment.CenterVertically) {
        BasicTextField(
            value = nameInput,
            onValueChange = { nameInput = it },
            modifier = Modifier
                .weight(1f)
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
                            "Item name",
                            style = TextStyle(color = Color.White, fontSize = 15.sp)
                        )
                    }
                    innerTextField()
                }
            },
        )
        BasicTextField(
            value = quantityInput,
            onValueChange = { quantityInput = it },
            modifier = Modifier
                .weight(1f)
                .padding(8.dp),

            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .background(
                            color = Color.Gray, shape = RoundedCornerShape(15.dp)
                        )
                        .padding(16.dp)
                ) {
                    if (quantityInput.isEmpty()) {
                        Text(
                            "Item quantity",
                            style = TextStyle(color = Color.White, fontSize = 15.sp)
                        )
                    }
                    innerTextField()
                }
            },
        )
        IconButton(
            onClick = { groceryViewModel.addGroceryItem(id, nameInput, quantityInput.toInt()) },
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.secondary,
                    shape = RoundedCornerShape(15.dp)
                )
                .padding(1.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add", tint = Color.White)
        }
    }
}