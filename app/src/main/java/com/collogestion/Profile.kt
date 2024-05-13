package com.collogestion

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.TextUnit
import androidx.core.content.ContextCompat


@SuppressLint("ResourceAsColor")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun Profile() {
        Surface(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
            color = Color.Black) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .width((LocalConfiguration.current.screenWidthDp * 0.85).dp)
            ) {
                androidx.compose.material3.Card(
                    modifier = Modifier
                        .fillMaxWidth()) {
                    Column(
                        modifier = Modifier
                            .background(color = Color(0xFF211F26))
                            .fillMaxWidth()
                            .padding(20.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically,) {
                            Image(
                                painterResource(id = R.drawable.profile),
                                contentDescription = "Profile Picture",
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )
                            Spacer(modifier = Modifier.width(20.dp))
                            Text(text = "UserName", style = TextStyle(color = Color.White, fontSize = 30.sp))
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(text = stringResource(id = R.string.number_of_project), style = TextStyle(color = Color.LightGray, fontSize = 20.sp))
                        Text(text = stringResource(id = R.string.total_spending), style = TextStyle(color = Color.LightGray, fontSize = 20.sp))
                        Text(text = stringResource(id = R.string.number_of_realised_task), style = TextStyle(color = Color.LightGray, fontSize = 20.sp))
                    }
                }
                Spacer(modifier = Modifier.height(15.dp))
                Row(modifier = Modifier.fillMaxWidth().height(25.dp), horizontalArrangement = Arrangement.Center) {
                    Text(text = stringResource(id = R.string.theme), style = TextStyle(color = Color.White, fontSize = 20.sp))
                }
                Spacer(modifier = Modifier.height(10.dp))
                androidx.compose.material3.Card(
                    modifier = Modifier
                        .background(color = Color.Transparent)
                        .fillMaxWidth()) {
                    var selectedIndex by remember { mutableIntStateOf(0) }
                    val options = listOf(stringResource(id = R.string.dark), stringResource(id = R.string.light))
                    SingleChoiceSegmentedButtonRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = Color(0xFF211F26))
                    )
                    {
                        options.forEachIndexed { index, label ->
                            SegmentedButton(
                                modifier = Modifier.padding(15.dp),
                                shape = CircleShape,
                                onClick = { selectedIndex = index },
                                selected = index == selectedIndex
                            ) {
                                Text(label)
                            }
                        }
                    }

                    }
                Spacer(modifier = Modifier.height(15.dp))
                Row(modifier = Modifier.fillMaxWidth().height(25.dp), horizontalArrangement = Arrangement.Center) {
                    Text(text = stringResource(id = R.string.notifications), style = TextStyle(color = Color.White, fontSize = 20.sp))
                }
                Spacer(modifier = Modifier.height(10.dp))
                androidx.compose.material3.Card(
                    modifier = Modifier
                        .background(color = Color.Transparent)
                        .fillMaxWidth()) {
                    var indexSelected by remember { mutableIntStateOf(0) }
                    val choices = listOf(stringResource(id = R.string.activated), stringResource(id = R.string.deactivated))
                    SingleChoiceSegmentedButtonRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = Color(0xFF211F26))
                    )
                    {
                        choices.forEachIndexed { index, label ->
                            SegmentedButton(
                                modifier = Modifier.padding(15.dp),
                                shape = CircleShape,
                                onClick = { indexSelected = index },
                                selected = index == indexSelected
                            ) {
                                Text(label)
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(50.dp))
                androidx.compose.material3.Card(
                    modifier = Modifier
                        .background(color = Color.Black)
                        .border(BorderStroke(width = 0.dp, color = Color(0xFF211F26)),
                            )
                        .fillMaxWidth()) {
                    var emailText by rememberSaveable { mutableStateOf("") }
                    TextField(
                        value = emailText,
                        onValueChange = { emailText = it },
                        label = { Text(stringResource(id = R.string.email)) },
//                        readOnly = true,
                        placeholder = { Text(stringResource(id = R.string.email_placeholder)) }
                    )
                }
                Spacer(modifier = Modifier.height(15.dp))
                androidx.compose.material3.Card(
                    modifier = Modifier
                        .background(color = Color.Black)
                        .border(BorderStroke(width = 0.dp, color = Color(0xFF211F26)),
                        )
                        .fillMaxWidth()) {
                    var phoneText by rememberSaveable { mutableStateOf("") }
                    TextField(
                        value = phoneText,
                        onValueChange = { phoneText = it },
                        label = { Text(stringResource(id = R.string.phone)) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        placeholder = { Text(stringResource(id = R.string.phone_placeholder)) }
                    )
                }
            }

        }
}