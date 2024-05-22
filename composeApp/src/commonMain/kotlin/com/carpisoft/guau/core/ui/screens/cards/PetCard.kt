package com.carpisoft.guau.core.ui.screens.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.carpisoft.guau.core.utils.date.DateTime
import guau.composeapp.generated.resources.Res
import guau.composeapp.generated.resources.age
import guau.composeapp.generated.resources.breed
import guau.composeapp.generated.resources.customer
import guau.composeapp.generated.resources.gender
import guau.composeapp.generated.resources.identification
import guau.composeapp.generated.resources.month_string
import guau.composeapp.generated.resources.months_string
import guau.composeapp.generated.resources.name
import guau.composeapp.generated.resources.pet_kind
import guau.composeapp.generated.resources.year_string
import guau.composeapp.generated.resources.years_string
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun PetCard(
    modifier: Modifier,
    name: String,
    kind: String,
    breed: String,
    gender: String,
    date: Long,
    customer: String,
    identificationNumber: String,
    deleteOnClick: () -> Unit
) {
    Box(
        modifier = modifier.shadow(
            elevation = 2.dp,
            shape = RoundedCornerShape(10)
        )
            .clip(shape = RoundedCornerShape(10))
            .background(color = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(top = 30.dp, bottom = 20.dp, start = 20.dp, end = 20.dp)
        ) {
            Row() {
                Text(
                    text = "${stringResource(Res.string.pet_kind)}: ",
                    fontWeight = FontWeight.Medium
                )
                Text(text = kind)
            }
            Row() {
                Text(
                    text = "${stringResource(Res.string.breed)}: ",
                    fontWeight = FontWeight.Medium
                )
                Text(text = breed)
            }
            Row() {
                Text(
                    text = "${stringResource(Res.string.gender)}: ",
                    fontWeight = FontWeight.Medium
                )
                Text(text = gender)
            }
            Row() {
                Text(
                    text = "${stringResource(Res.string.name)}: ",
                    fontWeight = FontWeight.Medium
                )
                Text(text = name)
            }
            Row() {
                Text(
                    text = "${stringResource(Res.string.age)}:",
                    fontWeight = FontWeight.Medium
                )
                val year = DateTime.ageCalculateYears(date)
                Text(
                    text = if (year > 0) {
                        if (year == 1) {
                            "$year ${stringResource(Res.string.year_string)}"
                        } else {
                            "$year ${stringResource(Res.string.years_string)}"
                        }
                    } else {
                        val month = DateTime.ageCalculateMonths(date)
                        if (month == 1) {
                            "$month ${stringResource(Res.string.month_string)}"
                        } else {
                            "$month ${stringResource(Res.string.months_string)}"
                        }
                    }
                )
            }
            Spacer(modifier.height(10.dp))
            Row() {
                Text(
                    text = "${stringResource(Res.string.customer)}:",
                    fontWeight = FontWeight.Medium
                )
                Text(text = customer)
            }

            Row() {
                Text(
                    text = "${stringResource(Res.string.identification)}:",
                    fontWeight = FontWeight.Medium
                )
                Text(text = identificationNumber)
            }
        }

        IconButton(modifier = Modifier.align(Alignment.TopEnd), onClick = deleteOnClick) {
            Icon(imageVector = Icons.Filled.Delete, contentDescription = null, tint = Color.Red)
        }
    }

}