package com.collogestion.ui.grocery

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.collogestion.data.GroceryList
import com.collogestion.data.GroceryListItem
import com.collogestion.ui.theme.ColloGestionTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroceryListScreen(
    id: Int,
    groceryViewModel: GroceryViewModel = viewModel()
) {
    val groceryUiState by groceryViewModel.uiState.collectAsState()
    groceryViewModel.selectGroceryList(id)

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black
                ),
                title = {
                    Row {
                        Text(
                            text = groceryUiState.selectedGroceryList?.name ?: "Unnamed",
                            style = TextStyle(fontSize = 20.sp, color = Color.White)
                        )
                    }
                }
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)  // Background noir
                    .padding(padding)
                    .padding(16.dp)
            ) {
                if (!groceryUiState.selectedGroceryList?.items.isNullOrEmpty()) {
                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    ) {
                        items(groceryUiState.selectedGroceryList?.items!!) { item ->
                            GroceryItemCard(item)
                        }
                    }
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /* TODO: Handle FAB click */ }) {
                Icon(Icons.Filled.Add, contentDescription = "Add Item", tint = Color.White)
            }
        },
        floatingActionButtonPosition = FabPosition.End,
//        isFloatingActionButtonDocked = true
    )
}

@Composable
fun GroceryItemCard(item: GroceryListItem) {
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
            Checkbox(
                checked = isChecked,
                onCheckedChange = {
                    isChecked = it
                    item.bought = it
                }
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(text = "${item.quantity} x ${item.name}", color = Color.White)
        }

        Button(
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color.White
            ),
            modifier = Modifier.background(color = Color.Transparent)
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
