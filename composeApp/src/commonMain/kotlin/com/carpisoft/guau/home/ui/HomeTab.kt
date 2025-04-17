package com.carpisoft.guau.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.VectorPainter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.carpisoft.guau.admission.ui.screens.AdmissionScreen
import com.carpisoft.guau.core.ui.screens.itemlist.ItemHome
import com.carpisoft.guau.main
import com.carpisoft.guau.main.ui.states.MainAction
import com.carpisoft.guau.ui.theme.NavyBlue
import com.carpisoft.guau.ui.theme.Orange
import com.carpisoft.guau.ui.theme.Purple
import guau.composeapp.generated.resources.Res
import guau.composeapp.generated.resources.admissions
import guau.composeapp.generated.resources.admit
import guau.composeapp.generated.resources.appointment
import guau.composeapp.generated.resources.calendar
import guau.composeapp.generated.resources.home
import guau.composeapp.generated.resources.vaccine
import guau.composeapp.generated.resources.vaccines
import org.jetbrains.compose.resources.stringResource

object HomeTab : Tab {
    @Composable
    override fun Content() {
        Screen(onAdmission = { main.send(action = MainAction.NavigateScreen(tag = AdmissionScreen.TAG)) })
    }


    override val options: TabOptions
        @Composable
        get() {
            val painter: VectorPainter = rememberVectorPainter(image = Icons.Filled.Home)
            val title: String = stringResource(resource = Res.string.home)
            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = painter
                )
            }
        }

    @Composable
    private fun Screen(onAdmission: () -> Unit) {
        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize()
                .padding(top = 10.dp, bottom = 5.dp, start = 10.dp, end = 10.dp),
            columns = GridCells.Fixed(count = 2),
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            item {
                ItemHome(
                    text = stringResource(Res.string.admissions),
                    backgroundColor = NavyBlue,
                    imageResource = Res.drawable.admit,
                    onClick = onAdmission
                )
            }
            item {
                ItemHome(
                    text = stringResource(Res.string.appointment),
                    backgroundColor = Orange,
                    imageResource = Res.drawable.calendar,
                    onClick = {}
                )
            }
            item {
                ItemHome(
                    text = stringResource(Res.string.vaccines),
                    backgroundColor = Purple,
                    imageResource = Res.drawable.vaccine,
                    onClick = {}
                )
            }
        }
    }
}