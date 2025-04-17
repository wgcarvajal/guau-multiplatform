package com.carpisoft.guau.pet.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import com.carpisoft.guau.core.ui.screens.buttons.NextButtonIntoBox
import com.carpisoft.guau.core.ui.screens.itemlist.ItemWithRemove
import com.carpisoft.guau.core.ui.screens.scaffold.GuauScaffoldSimple
import com.carpisoft.guau.pet.domain.model.BreedResp
import com.carpisoft.guau.pet.ui.util.PetHelper
import guau.composeapp.generated.resources.Res
import guau.composeapp.generated.resources.add_pet
import guau.composeapp.generated.resources.select
import guau.composeapp.generated.resources.select_breed
import guau.composeapp.generated.resources.two_of_four
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

class SelectBreedScreen : Screen {
    @Composable
    override fun Content() {
        val navigator: Navigator? = LocalNavigator.current
        val addPetViewModel = koinViewModel<AddPetViewModel>()
        val petHelper = koinInject<PetHelper>()
        val breedSelected by addPetViewModel.breedSelected.collectAsState()
        Screen(
            breedSelected = breedSelected,
            selectOnClick = { navigator?.push(item = BreedsScreen()) },
            onRemove = {
                petHelper.setBreedPetSelected(breedResp = null)
                addPetViewModel.removeSelectedBreed()
            }, nextOnclick = {
                navigator?.push(item = PetDataScreen())
            }, onBack = {
                navigator?.pop()
            })
        LaunchedEffect(key1 = 1) {
            if (petHelper.getBreedPetSelected() != null) {
                addPetViewModel.selectedBreed(breed = petHelper.getBreedPetSelected()!!)
            }
        }
    }
}

@Composable
private fun Screen(
    breedSelected: BreedResp,
    selectOnClick: () -> Unit,
    onRemove: () -> Unit,
    nextOnclick: () -> Unit,
    onBack: () -> Unit
) {
    GuauScaffoldSimple(
        title = stringResource(Res.string.add_pet),
        onBack = onBack
    ) { paddingValues ->
        Column(modifier = Modifier.fillMaxSize().padding(paddingValues = paddingValues)) {
            Text(
                modifier = Modifier.fillMaxWidth().padding(top = 10.dp, start = 10.dp, end = 10.dp),
                text = "${stringResource(Res.string.select_breed)} ${
                    stringResource(
                        Res.string.two_of_four
                    )
                }",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
            Box(modifier = Modifier.fillMaxSize()) {
                if (breedSelected.id == -1) {
                    Button(
                        modifier = Modifier.fillMaxWidth().align(Alignment.Center)
                            .padding(start = 10.dp, end = 10.dp),
                        onClick = selectOnClick
                    ) {
                        Text(
                            text = stringResource(Res.string.select)
                        )
                    }
                } else {
                    ItemWithRemove(
                        modifier = Modifier.align(Alignment.TopCenter)
                            .padding(top = 20.dp, start = 20.dp, end = 20.dp),
                        text = breedSelected.name,
                        imageUrl = breedSelected.image,
                        onRemove = onRemove
                    )
                    NextButtonIntoBox(nextOnclick = nextOnclick)
                }
            }
        }
    }

}