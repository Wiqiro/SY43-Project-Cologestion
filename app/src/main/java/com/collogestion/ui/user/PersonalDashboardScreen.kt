package com.collogestion.ui.user

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.collogestion.data.HouseShare
import com.collogestion.ui.due.DueCard
import com.collogestion.ui.due.DueViewModel
import com.collogestion.ui.event.EventCard
import com.collogestion.ui.event.EventViewModel
import com.collogestion.ui.grocery.GroceryListCard
import com.collogestion.ui.grocery.GroceryViewModel
import com.collogestion.ui.task.TaskCard
import com.collogestion.ui.task.TaskViewModel


@Composable
fun PersonalDashboardScreen(
    userViewModel: UserViewModel = viewModel(),
    taskViewModel: TaskViewModel = viewModel(),
    dueViewModel: DueViewModel = viewModel()
) {
    val userUiState by userViewModel.uiState.collectAsState()

    //TODO: remove and make model passed by parent
    LaunchedEffect(Unit) {
        userViewModel.loadUser(1)
    }

    val userId: Int = userUiState.user?.id ?: return

    val taskUiState by taskViewModel.uiState.collectAsState()
    LaunchedEffect(Unit) {
        taskViewModel.loadUserTasks(userId)
    }


    val dueUiState by dueViewModel.uiState.collectAsState()
    LaunchedEffect(Unit) {
        dueViewModel.loadHouseShareDues(userId)
    }

    Column(
        Modifier
            .fillMaxWidth()
            .padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TaskCard(taskUiState.tasks)
        DueCard(dueUiState.dues)
    }
}