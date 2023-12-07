package core.ui.screens.cards

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
import com.carpisoft.guau.SharedRes
import core.utils.date.DateTime
import dev.icerock.moko.resources.compose.stringResource

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
                    text = "${stringResource(SharedRes.strings.pet_kind)}: ",
                    fontWeight = FontWeight.Medium
                )
                Text(text = kind)
            }
            Row() {
                Text(
                    text = "${stringResource(SharedRes.strings.breed)}: ",
                    fontWeight = FontWeight.Medium
                )
                Text(text = breed)
            }
            Row() {
                Text(
                    text = "${stringResource(SharedRes.strings.gender)}: ",
                    fontWeight = FontWeight.Medium
                )
                Text(text = gender)
            }
            Row() {
                Text(
                    text = "${stringResource(SharedRes.strings.name)}: ",
                    fontWeight = FontWeight.Medium
                )
                Text(text = name)
            }
            Row() {
                Text(
                    text = "${stringResource(SharedRes.strings.age)}:",
                    fontWeight = FontWeight.Medium
                )
                val year = DateTime.ageCalculateYears(date)
                Text(
                    text = if (year > 0) {
                        if (year == 1) {
                            "$year ${stringResource(SharedRes.strings.year_string)}"
                        } else {
                            "$year ${stringResource(SharedRes.strings.years_string)}"
                        }
                    } else {
                        val month = DateTime.ageCalculateMonths(date)
                        if (month == 1) {
                            "$month ${stringResource(SharedRes.strings.month_string)}"
                        } else {
                            "$month ${stringResource(SharedRes.strings.months_string)}"
                        }
                    }
                )
            }
            Spacer(modifier.height(10.dp))
            Row() {
                Text(
                    text = "${stringResource(SharedRes.strings.customer)}:",
                    fontWeight = FontWeight.Medium
                )
                Text(text = customer)
            }

            Row() {
                Text(
                    text = "${stringResource(SharedRes.strings.identification)}:",
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