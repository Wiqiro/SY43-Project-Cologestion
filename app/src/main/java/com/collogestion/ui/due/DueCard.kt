package com.collogestion.ui.due

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.collogestion.data.Due
import com.collogestion.ui.user.UserViewModel

@Composable
fun DueCard(dues: List<Due>) {
    Spacer(modifier = Modifier.height(15.dp))
    Column(
        modifier = Modifier
            .width((LocalConfiguration.current.screenWidthDp * 0.85).dp)
            .clip(shape = RoundedCornerShape(10.dp))
            .background(color = Color(0xFF211F26))
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(text = "Dues", style = TextStyle(color = Color.White, fontSize = 25.sp))
        dues.forEach { item -> DueItem(item) }
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = "My total spending = 35€",
            style = TextStyle(color = Color.White, fontSize = 20.sp)
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = "Total spending by the project= 100€",
            style = TextStyle(color = Color.White, fontSize = 20.sp)
        )

    }
}

@Composable
fun DueItem(item: Due, userViewModel: UserViewModel = viewModel()) {

    Spacer(modifier = Modifier.height(15.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(10.dp))
            .background(color = Color.DarkGray)
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "${item.title} - ${item.amount}€   (${item.debtor} -> ${item.creditor})",
            style = TextStyle(color = Color.White, fontSize = 20.sp),
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
        )
    }
}