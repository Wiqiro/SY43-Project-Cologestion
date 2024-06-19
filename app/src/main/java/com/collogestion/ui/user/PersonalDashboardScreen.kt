package com.collogestion.ui.user

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.collogestion.ui.due.DueCard
import com.collogestion.ui.due.DueViewModel
import com.collogestion.ui.task.TaskCard
import com.collogestion.ui.task.TaskViewModel


@Composable
fun PersonalDashboardScreen(
    userViewModel: UserViewModel = viewModel(),
    taskViewModel: TaskViewModel = viewModel(),
    dueViewModel: DueViewModel = viewModel()
) {
    val userUiState by userViewModel.uiState.collectAsState()
    LaunchedEffect(Unit) {
        userViewModel.loadUser()
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
            .verticalScroll(rememberScrollState())
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TaskCard(taskUiState.tasks)
        DueCard(dues = dueUiState.dues, dueViewModel= dueViewModel)
    }
}