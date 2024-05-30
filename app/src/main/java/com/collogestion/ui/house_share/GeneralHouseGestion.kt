package com.collogestion.ui.house_share

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.collogestion.data.GroceryList
import com.collogestion.data.HouseShare
import com.collogestion.services.GroceriesService
import com.collogestion.ui.due.DueCard
import com.collogestion.ui.due.DueViewModel
import com.collogestion.ui.event.EventCard
import com.collogestion.ui.event.EventItem
import com.collogestion.ui.event.EventViewModel
import com.collogestion.ui.grocery.GroceryListCard
import com.collogestion.ui.grocery.GroceryViewModel
import com.collogestion.ui.task.TaskCard
import com.collogestion.ui.task.TaskViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Composable
fun ProjectZone(
    houseShare: HouseShare,
    groceryViewModel: GroceryViewModel = viewModel(),
    taskViewModel: TaskViewModel = viewModel(),
    eventViewModel: EventViewModel = viewModel(),
    dueViewModel: DueViewModel = viewModel()
) {
    val groceryUiState by groceryViewModel.uiState.collectAsState()
    LaunchedEffect(Unit) {
        groceryViewModel.loadHouseShareGroceryLists(houseShare.id)
    }

    val taskUiState by taskViewModel.uiState.collectAsState()
    LaunchedEffect(Unit) {
        taskViewModel.loadHouseShareTasks(houseShare.id)
    }

    val eventUiState by eventViewModel.uiState.collectAsState()
    LaunchedEffect(Unit) {
        eventViewModel.loadHouseShareEvents(houseShare.id)
    }

    val dueUiState by dueViewModel.uiState.collectAsState()
    LaunchedEffect(Unit) {
        dueViewModel.loadHouseShareDues(houseShare.id)
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
            .padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = houseShare.name, style = TextStyle(color = Color.White, fontSize = 30.sp))
        GroceryListCard(groceryUiState.groceryLists)
        TaskCard(taskUiState.tasks)
        EventCard(eventUiState.events)
        DueCard(dueUiState.dues)
    }
}


