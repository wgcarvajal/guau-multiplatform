package com.carpisoft.guau.core.ui.screens.buttons

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import guau.composeapp.generated.resources.Res
import guau.composeapp.generated.resources.next
import guau.composeapp.generated.resources.save
import org.jetbrains.compose.resources.stringResource

@Composable
fun BoxScope.NextButtonIntoBox(nextOnclick: () -> Unit, enabled: Boolean = true) {
    Button(
        enabled = enabled,
        onClick = nextOnclick,
        modifier = Modifier.align(Alignment.BottomEnd)
            .padding(end = 20.dp, bottom = 20.dp)
    ) {
        Text(text = stringResource(Res.string.next))
    }
}


@Composable
fun BoxScope.SaveButtonIntoBox(enabled: Boolean, saveOnclick: () -> Unit) {
    Button(
        enabled = enabled,
        onClick = saveOnclick,
        modifier = Modifier.align(Alignment.BottomEnd)
            .padding(end = 20.dp, bottom = 20.dp)
    ) {
        Text(text = stringResource(Res.string.save))
    }
}