package com.collogestion

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
import androidx.compose.material3.CardColors
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.collogestion.data.HouseShare
import com.collogestion.data.HouseShareData
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@Composable
fun ProjectZone(houseShare: HouseShare) {
    val shoppingList: List<String> = listOf("Apple", "Banana", "Orange", "Toilet paper", "Soap", "Shampoo", "Banana", "Orange", "Vinegar", "Olive oil", "salt", "pepper")
    val houseWorkList: List<String> = listOf("Clean toilet", "Dishes", "Etc...")
    val spendingList: List<String> = listOf("Course : 20€", "McDo : 15€", "Etc...")
    Column(
        Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = houseShare.name, style = TextStyle(color = Color.White, fontSize = 30.sp))
        ListCard(title = "Shopping List", listOfItem = shoppingList)
        ListCard(title = "House Work", listOfItem = houseWorkList)
        ListCard(title = "Spending", listOfItem = spendingList)
    }
}

@Composable
fun ListCard(title: String, listOfItem : List<String>) {
    val isSpending : Boolean = title == "Spending"
    Spacer(modifier = Modifier.height(15.dp))
    Column(modifier =
    Modifier
        .width((LocalConfiguration.current.screenWidthDp * 0.85).dp)
//        .height((LocalConfiguration.current.screenWidthDp * 0.8).dp)
        .clip(shape = RoundedCornerShape(10.dp))
        .background(color = Color(0xFF211F26))
        .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(text = title, style = TextStyle(color = Color.White, fontSize = 25.sp))
        listOfItem.forEach{item -> ItemCard(item, isSpending = isSpending) }
        if(title == "Spending") {
            Spacer(modifier = Modifier.height(15.dp))
            Text(text = "My total spending = 35€", style = TextStyle(color = Color.White, fontSize = 20.sp))
            Spacer(modifier = Modifier.height(5.dp))
            Text(text = "Total spending by the project= 100€", style = TextStyle(color = Color.White, fontSize = 20.sp))
        }
    }
}

@Composable
fun ItemCard(item: String, isSpending: Boolean) {
    var checkedState by remember { mutableStateOf(false) }
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
            text = item,
            style = TextStyle(color = Color.White, fontSize = 20.sp),
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
        )
        if(!isSpending) {
            Checkbox(
                checked = checkedState,
                onCheckedChange = { checkedState = it },
                modifier = Modifier.padding(end = 8.dp),
                colors = CheckboxDefaults.colors(
                    checkedColor = Color.Blue,
                    uncheckedColor = Color.Red
                )
            )
        }
    }
}