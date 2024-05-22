package com.carpisoft.guau.core.ui.screens.itemlist

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DisabledByDefault
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import com.carpisoft.guau.ui.theme.NavyBlue

@Composable
fun ItemPetType(
    text: String,
    imageUrl: String,
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
                .background(color = NavyBlue)
        ) {
            KamelImage(
                modifier = Modifier.height(110.dp).padding(top = 8.dp),
                resource = asyncPainterResource(imageUrl),
                contentDescription = "pet kind",
                contentScale = ContentScale.FillHeight
            )
            Text(
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                text = text,
                color = Color.White,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun ItemWithRemove(
    modifier: Modifier,
    text: String,
    imageUrl: String,
    onRemove: () -> Unit
) {
    Box(modifier = modifier) {
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
            KamelImage(
                modifier = Modifier.height(300.dp).padding(top = 8.dp),
                resource = asyncPainterResource(imageUrl),
                contentDescription = "pet kind",
                contentScale = ContentScale.FillHeight
            )
            Text(
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                text = text,
                color = Color.Black,
                fontSize = 22.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium
            )
        }
        IconButton(
            modifier = Modifier.align(Alignment.TopEnd).padding(top = 8.dp, end = 8.dp),
            onClick = onRemove,
        ) {
            Icon(
                modifier = Modifier.size(50.dp),
                imageVector = Icons.Filled.DisabledByDefault,
                contentDescription = "remove kind",
                tint = Color.Red
            )
        }
    }
}

