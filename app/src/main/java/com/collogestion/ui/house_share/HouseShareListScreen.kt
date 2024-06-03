package com.collogestion.ui.house_share

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.collogestion.data.HouseShare
import com.collogestion.ui.theme.ColloGestionTheme


@Composable
fun HouseShareCard(houseShare: HouseShare, onCardClick: () -> Unit) {
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
fun HouseShareListScreen(
    navController: NavController,
    houseShareViewModel: HouseShareViewModel = viewModel()
) {
    val houseShareUiState by houseShareViewModel.uiState.collectAsState()
    houseShareViewModel.loadUsersHouseShares(1)

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
                houseShareUiState.houseShares.forEach { houseShare ->
                    HouseShareCard(houseShare = houseShare) {
                        navController.navigate("house_share_details")
                    }
                    Spacer(modifier = Modifier.height(40.dp))
                }
            }
        }
    }
}