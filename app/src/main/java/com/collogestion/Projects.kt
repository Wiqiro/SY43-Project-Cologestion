package com.collogestion

import com.collogestion.data.HouseShare
import com.collogestion.data.HouseShareData
import com.collogestion.ui.theme.ColloGestionTheme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.collogestion.services.HouseSharesService


@Composable
fun Card(houseShare: HouseShare, onCardClick: () -> Unit) {
    Column(Modifier
//            .border(border = BorderStroke(width = 1.dp, color = Color.Red), shape = RoundedCornerShape(30.0.dp))
        .width((LocalConfiguration.current.screenWidthDp * 0.85).dp)
        .height((LocalConfiguration.current.screenWidthDp * 0.5).dp)
        .clip(shape = RoundedCornerShape(10.dp))
        .clickable { onCardClick() }
        .background(color = Color(0xFF211F26)),
//            .padding(10.dp),
//            .border(shape = RoundedCornerShape(5.dp)),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally) {
//        Image(
//            painterResource(id = houseShare.image),
//            contentDescription = "Group image",
//            modifier = Modifier.size(80.dp),
//            contentScale = ContentScale.Crop
//        )
//        Spacer(modifier = Modifier.height(10.dp))
        Text(text = houseShare.name, style = TextStyle(color = Color.White, fontSize = 20.sp))
    }
}

@Composable
@Preview
fun Projects() {
    val houseShare = remember { mutableStateOf(listOf<HouseShare>()) }
    HouseSharesService.getUsersHouseShares(1) { houseShares -> houseShare.value = houseShares }

    val (selectedProject, setSelectedProject) = remember { mutableStateOf<HouseShare?>(null) }
    ColloGestionTheme {
        if (selectedProject != null) {
            Project(project = selectedProject) {
                setSelectedProject(null) // Clear selected project to go back to project list
            }
        } else {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                houseShare.value.forEach { houseShare ->
                    Card(houseShare = houseShare) {
                        setSelectedProject(houseShare) // Set selected project to show project details
                    }
                    Spacer(modifier = Modifier.height(40.dp))
                }
            }
        }
    }
}