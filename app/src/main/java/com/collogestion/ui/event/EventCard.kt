package com.collogestion.ui.event

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.collogestion.data.Event
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun EventCard(events: List<Event>) {
    Spacer(modifier = Modifier.height(15.dp))
    Column(
        modifier =
        Modifier
            .width((LocalConfiguration.current.screenWidthDp * 0.85).dp)
            .clip(shape = RoundedCornerShape(10.dp))
            .background(color = Color(0xFF211F26))
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(text = "Events", style = TextStyle(color = Color.White, fontSize = 25.sp))
        events.forEach { item -> EventItem(item) }

    }
}

@Composable
fun EventItem(item: Event) {
    val sdf = SimpleDateFormat("dd-MM-yy HH:mm", Locale.getDefault())
    val formattedDate = sdf.format(Date(item.date.toLong()))

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
            text = "${item.title} - $formattedDate",
            style = TextStyle(color = Color.White, fontSize = 20.sp),
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
        )
    }
}