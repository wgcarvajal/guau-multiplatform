package com.carpisoft.guau.core.ui.screens.itemlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ItemHome(
    text: String,
    backgroundColor: Color,
    imageResource: DrawableResource,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth()
            .height(120.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
                .padding(top = 20.dp)
                .clickable { onClick() }
                .border(
                    width = 5.dp,
                    color = Color.White,
                    shape = RoundedCornerShape(10)
                )
                .shadow(elevation = 2.dp, shape = RoundedCornerShape(10))
                .clip(shape = RoundedCornerShape(10))
                .background(color = backgroundColor)
        )
        Image(
            modifier = Modifier.fillMaxHeight(),
            painter = painterResource(imageResource),
            contentScale = ContentScale.FillHeight,
            contentDescription = null
        )
        Text(
            modifier = Modifier.align(alignment = Alignment.CenterEnd)
                .padding(top = 30.dp, end = 15.dp),
            text = text,
            color = Color.White,
            fontSize = 16.sp
        )
    }
}