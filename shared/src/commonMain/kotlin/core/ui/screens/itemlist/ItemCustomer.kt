package core.ui.screens.itemlist

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ItemCustomer(
    identificationType: String,
    identification: String,
    name: String,
    onClick: () -> Unit
) {

    Box(modifier = Modifier.fillMaxWidth()
        .padding(top = 5.dp, bottom = 5.dp, start = 10.dp, end = 10.dp)
        .clickable {
            onClick()
        }
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
                .border(
                    width = 5.dp,
                    color = Color.White,
                    shape = RoundedCornerShape(10)
                )
                .shadow(elevation = 2.dp, shape = RoundedCornerShape(10))
                .clip(shape = RoundedCornerShape(10))
                .background(color = Color.White)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 8.dp, start = 10.dp, end = 8.dp, bottom = 8.dp),
                text = "$identificationType: $identification",
                color = Color.Black,
                fontSize = 14.sp,
                textAlign = TextAlign.Start
            )
            Text(
                modifier = Modifier.fillMaxWidth()
                    .padding(start = 10.dp, end = 8.dp, bottom = 8.dp),
                text = name,
                color = Color.Black,
                fontSize = 14.sp,
                textAlign = TextAlign.Start
            )
        }
    }
}