package com.collogestion.ui.grocery

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.collogestion.data.GroceryList
import com.collogestion.data.GroceryListItem
import com.collogestion.data.User
import com.collogestion.ui.theme.ColloGestionTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroceryListScreen(groceryList: GroceryList, members: List<String>) {
    var selectedMembers by remember { mutableStateOf(members) }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black
                ),
                title = {
                    Row {
                        Text(text = groceryList.name, style = TextStyle(fontSize = 20.sp, color = Color.White))
                        if (selectedMembers.isNotEmpty()) {
                            Text(text = " - ", style = TextStyle(fontSize = 20.sp, color = Color.White))
                            LazyRow {
                                items(selectedMembers) { member ->
                                    Text(text = "$member, ", style = TextStyle(fontSize = 20.sp, color = Color.White))
                                }
                            }
                        }
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
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    items(groceryList.items) { item ->
                        GroceryItemCard(item)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Persons responsible for shopping:",
                    style = TextStyle(fontSize = 18.sp, color = Color.White)
                )

                MembersCheckboxMenu(
                    selectedMembers = selectedMembers,
                    members = members,
                    onMembersSelected = { selectedMembers = it }
                )
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
        Row(verticalAlignment = Alignment.CenterVertically,) {
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

@Composable
fun MembersCheckboxMenu(
    selectedMembers: List<String>,
    members: List<String>,
    onMembersSelected: (List<String>) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val selectedSet = remember { mutableStateOf(selectedMembers.toMutableSet()) }

    Box(modifier = Modifier.fillMaxWidth()) {
        TextButton(
            onClick = { expanded = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Select Members", color = Color.White)
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            members.forEach { member ->
                val isSelected = selectedSet.value.contains(member)
                DropdownMenuItem(
                    text = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Checkbox(
                                checked = isSelected,
                                onCheckedChange = null
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = member, color = Color.Black)
                        }
                    },
                    onClick = {
                        if (isSelected) {
                            selectedSet.value.remove(member)
                        } else {
                            selectedSet.value.add(member)
                        }
                        onMembersSelected(selectedSet.value.toList())
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GroceryListPreview() {
    val members: List<String> = listOf("Alice", "Bob", "Charlie", "dez", "Bfesob", "Chafrsrlie", "Alfsice", "Bfeslob", "Chfesarlie")
    ColloGestionTheme {
        val groceryList = GroceryList(
            name = "Weekly Groceries",
            items = listOf(
                GroceryListItem(id = 0, name = "Milk", quantity = 2, bought = false),
                GroceryListItem(id = 0, name = "Other0", quantity = 2, bought = false),
                GroceryListItem(id = 0, name = "Other1", quantity = 2, bought = false),
                GroceryListItem(id = 0, name = "Other2", quantity = 2, bought = false),
                GroceryListItem(id = 0, name = "Other3", quantity = 2, bought = false),
                GroceryListItem(id = 0, name = "Other4", quantity = 2, bought = false),
                GroceryListItem(id = 0, name = "Other5", quantity = 2, bought = false),
                GroceryListItem(id = 0, name = "Other6", quantity = 2, bought = false),

                ),
            members = members,
            id = 0,
            houseShareId = 0,
        )
        GroceryListScreen(groceryList = groceryList, members = members)
    }
}
