package com.collogestion

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.collogestion.data.HouseShareData


@Composable
@Preview
fun PersonalTask() {
    val houseShare = HouseShareData.getHouseShare()
    houseShare.forEach {houseShareItem ->  ProjectZone(houseShare = houseShareItem)}
    }