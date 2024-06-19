package com.collogestion.ui.house_share

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.collogestion.ui.due.DueCard
import com.collogestion.ui.due.DueViewModel
import com.collogestion.ui.event.EventCard
import com.collogestion.ui.event.EventViewModel
import com.collogestion.ui.grocery.GroceryListCard
import com.collogestion.ui.grocery.GroceryViewModel
import com.collogestion.ui.task.TaskCard
import com.collogestion.ui.task.TaskViewModel

@Composable
fun HouseShareDetailsScreen(
    navController: NavController,
    houseShareId: Int,
    houseShareViewModel: HouseShareViewModel = viewModel(),
    groceryViewModel: GroceryViewModel = viewModel(),
    taskViewModel: TaskViewModel = viewModel(),
    eventViewModel: EventViewModel = viewModel(),
    dueViewModel: DueViewModel = viewModel()
) {
    val houseShareUiState by houseShareViewModel.uiState.collectAsState()
    LaunchedEffect(Unit) {
        houseShareViewModel.loadHouseShare(houseShareId)
    }
    val houseShare = houseShareUiState.houseShares.find { it.id == houseShareId }

    val groceryUiState by groceryViewModel.uiState.collectAsState()
    LaunchedEffect(Unit) {
        groceryViewModel.loadHouseShareGroceryLists(houseShareId)
    }

    val taskUiState by taskViewModel.uiState.collectAsState()
    LaunchedEffect(Unit) {
        taskViewModel.loadHouseShareTasks(houseShareId)
    }

    val eventUiState by eventViewModel.uiState.collectAsState()
    LaunchedEffect(Unit) {
        eventViewModel.loadHouseShareEvents(houseShareId)
    }

    val dueUiState by dueViewModel.uiState.collectAsState()
    LaunchedEffect(Unit) {
        dueViewModel.loadHouseShareDues(houseShareId)
    }

    if (taskUiState.errorMessage != null) {
        Text(
            text = taskUiState.errorMessage!!,
            style = TextStyle(color = Color.Red, fontSize = 20.sp)
        )
    }

    Column(
        Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Text(
            text = houseShare?.name ?: "",
            style = TextStyle(color = Color.White, fontSize = 30.sp)
        )
        GroceryListCard(navController, groceryUiState.groceryLists)
        TaskCard(taskUiState.tasks)
        EventCard(navController, houseShareId, eventUiState.events, eventViewModel)
        DueCard(navController, houseShareId, dueUiState.dues, dueViewModel)
    }
}


