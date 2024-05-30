package com.collogestion.ui.grocery

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
import com.collogestion.data.GroceryList

@Composable
fun GroceryListCard(groceryLists: List<GroceryList>) {
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
        Text(text = "Grocery lists", style = TextStyle(color = Color.White, fontSize = 25.sp))
        groceryLists.forEach { list -> GroceryListItemCard(list) }
    }
}

//TODO: Add the possibility to click on the grocery list to redirect to the grocery list page
@Composable
fun GroceryListItemCard(groceryList: GroceryList) {
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
            text = groceryList.name,
            style = TextStyle(color = Color.White, fontSize = 20.sp),
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
        )
    }
}