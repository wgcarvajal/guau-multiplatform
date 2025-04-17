package com.carpisoft.guau.pet.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import com.carpisoft.guau.core.domain.usecase.GetMessageErrorUseCase
import com.carpisoft.guau.core.ui.model.ErrorUi
import com.carpisoft.guau.core.ui.screens.dialogs.TwoButtonDialog
import com.carpisoft.guau.core.ui.screens.itemlist.ItemBreed
import com.carpisoft.guau.core.ui.screens.loading.SimpleLoading
import com.carpisoft.guau.core.ui.screens.search.SearchAndContentTemplateScreen
import com.carpisoft.guau.core.ui.screens.snackbars.DecoupledSnackBar
import com.carpisoft.guau.pet.domain.model.BreedResp
import com.carpisoft.guau.pet.ui.util.PetHelper
import guau.composeapp.generated.resources.Res
import guau.composeapp.generated.resources.breeds
import guau.composeapp.generated.resources.cancel
import guau.composeapp.generated.resources.hide
import guau.composeapp.generated.resources.retry
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

class BreedsScreen : Screen {
    @Composable
    override fun Content() {
        val navigator: Navigator? = LocalNavigator.current
        val addPetViewModel = koinViewModel<AddPetViewModel>()
        val petHelper = koinInject<PetHelper>()
        val loading by addPetViewModel.loading.collectAsState()
        val loadingMore by addPetViewModel.loadingMore.collectAsState()
        val breeds by addPetViewModel.breeds.collectAsState()
        val showError by addPetViewModel.showError.collectAsState()
        val searchText by addPetViewModel.searchText.collectAsState()
        Screen(
            breeds = breeds,
            loading = loading,
            loadingMore = loadingMore,
            searchText = searchText,
            showError = showError,
            onValueChange = {
                addPetViewModel.onValueSearchBreed(it)
            },
            onEmptyOnClick = {
                addPetViewModel.onValueSearchBreed("")
            },
            onLoadMore = { addPetViewModel.onLoadMoreBreedData() },
            selectOnClick = {
                petHelper.setBreedPetSelected(breedResp = it)
                addPetViewModel.resetBreeds()
                navigator?.pop()
            },
            onBack = {
                addPetViewModel.resetBreeds()
                //onBackOnClick()
            })

        TwoButtonDialog(
            show = showError,
            message = GetMessageErrorUseCase(errorUi = addPetViewModel.getErrorUi() ?: ErrorUi()),
            confirmButtonText = stringResource(Res.string.retry),
            cancelButtonText = stringResource(Res.string.cancel),
            confirmButton = {
                addPetViewModel.dismissErrorDialog()
                addPetViewModel.loadBreedData()
            },
            onDismissRequest = {
                addPetViewModel.dismissErrorDialog()
                //onBackOnClick()
            })

        LaunchedEffect(key1 = 1) {
            addPetViewModel.selectedTypePet(typePet = petHelper.getTypePetSelected()!!)
            addPetViewModel.loadBreedData()
        }
        val snackBarHostState = remember { SnackbarHostState() }
        DecoupledSnackBar(
            snackBarHostState = snackBarHostState,
            containerColor = Color.Red,
            messageColor = Color.White,
            actionColor = Color.White
        )

        val showErrorSnackBar by addPetViewModel.showErrorSnackBar.collectAsState()
        val errorMessage =
            GetMessageErrorUseCase(errorUi = addPetViewModel.getErrorUi() ?: ErrorUi())
        val actionLabel = stringResource(Res.string.hide)
        LaunchedEffect(key1 = showErrorSnackBar) {
            if (showErrorSnackBar) {
                snackBarHostState.showSnackbar(
                    message = errorMessage,
                    actionLabel = actionLabel,
                    duration = SnackbarDuration.Short
                )
                addPetViewModel.resetShowErrorSnackBar()
            }
        }
    }
}

@Composable
private fun Screen(
    breeds: List<BreedResp>,
    searchText: String,
    loading: Boolean,
    loadingMore: Boolean,
    showError: Boolean,
    onValueChange: (String) -> Unit,
    onEmptyOnClick: () -> Unit,
    onLoadMore: () -> Unit,
    selectOnClick: (BreedResp) -> Unit,
    onBack: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    var activeSearch by rememberSaveable { mutableStateOf(false) }
    val state = rememberLazyGridState()
    if (loading) {
        SimpleLoading()
    } else if (!showError) {
        SearchAndContentTemplateScreen(
            title = stringResource(Res.string.breeds),
            gridState = state,
            searchText = searchText,
            activeSearch = activeSearch,
            loadingMore = loadingMore,
            keyboardController = keyboardController,
            focusManager = focusManager,
            onLoadMore = onLoadMore,
            onBackOnClick = onBack,
            searchOnClick = { activeSearch = true },
            onValueChange = onValueChange,
            onEmptyOnClick = onEmptyOnClick,
            onCloseOnClick = { activeSearch = false }
        ) {
            LazyVerticalGrid(
                state = state,
                modifier = Modifier.fillMaxSize()
                    .padding(top = 10.dp, bottom = 10.dp, start = 10.dp, end = 10.dp),
                columns = GridCells.Fixed(count = 2),
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                items(breeds) { breed ->
                    ItemBreed(
                        text = breed.name,
                        imageUrl = breed.image,
                        selected = false,
                        onClick = { selectOnClick(breed) }
                    )
                }
            }
        }
    }
}


