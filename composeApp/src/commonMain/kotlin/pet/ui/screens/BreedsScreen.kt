package pet.ui.screens

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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.carpisoft.guau.SharedRes
import core.domain.usecase.GetMessageErrorUseCase
import core.ui.constants.ScreenEnum
import core.ui.model.ErrorUi
import core.ui.model.UiStructureProperties
import core.ui.screens.dialogs.TwoButtonDialog
import core.ui.screens.itemlist.ItemBreed
import core.ui.screens.loading.SimpleLoading
import core.ui.screens.search.SearchAndContentTemplateScreen
import core.ui.screens.snackbars.DecoupledSnackBar
import dev.icerock.moko.resources.compose.stringResource

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BreedsScreen(
    uiStructureProperties: UiStructureProperties,
    addPetViewModel: AddPetViewModel,
    onBackOnClick: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    LaunchedEffect(key1 = 1) {
        uiStructureProperties.onShowTopBar(false)
        uiStructureProperties.onShowBottomBar(false)
        uiStructureProperties.showActionNavigation(true)
        uiStructureProperties.onShowExitCenter(true)
        uiStructureProperties.onSetTitle(ScreenEnum.Breeds)
        uiStructureProperties.showAddActionButton(false)
        uiStructureProperties.showActionAccountOptions(false)
        uiStructureProperties.showActionNext(false)
        uiStructureProperties.onEnabledNextAction(false)
        uiStructureProperties.onShowActionBottom(false)
        uiStructureProperties.onShowSaveAction(false)
        uiStructureProperties.onEnabledSaveAction(false)
    }

    val loading by addPetViewModel.loading.collectAsState()
    val loadingMore by addPetViewModel.loadingMore.collectAsState()
    val breeds by addPetViewModel.breeds.collectAsState()
    val showError by addPetViewModel.showError.collectAsState()
    val searchText by addPetViewModel.searchText.collectAsState()
    var activeSearch by rememberSaveable { mutableStateOf(false) }
    val state = rememberLazyGridState()
    if (loading) {
        SimpleLoading()
    } else if (!showError) {
        SearchAndContentTemplateScreen(
            title = stringResource(SharedRes.strings.breeds),
            gridState = state,
            searchText = searchText,
            activeSearch = activeSearch,
            loadingMore = loadingMore,
            keyboardController = keyboardController,
            focusManager = focusManager,
            onLoadMore = { addPetViewModel.onLoadMoreBreedData() },
            onBackOnClick = {
                addPetViewModel.resetBreeds()
                onBackOnClick()
            },
            searchOnClick = { activeSearch = true },
            onValueChange = { addPetViewModel.onValueSearchBreed(it) },
            onEmptyOnClick = { addPetViewModel.onValueSearchBreed("") },
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
                        onClick = {
                            addPetViewModel.selectedBreed(breed = breed)
                            addPetViewModel.resetBreeds()
                            onBackOnClick()
                        }
                    )
                }
            }
        }
    }
    TwoButtonDialog(
        show = showError,
        message = GetMessageErrorUseCase(errorUi = addPetViewModel.getErrorUi() ?: ErrorUi()),
        confirmButtonText = stringResource(SharedRes.strings.retry),
        cancelButtonText = stringResource(SharedRes.strings.cancel),
        confirmButton = {
            addPetViewModel.dismissErrorDialog()
            addPetViewModel.loadBreedData()
        },
        onDismissRequest = {
            addPetViewModel.dismissErrorDialog()
            onBackOnClick()
        })

    LaunchedEffect(key1 = 1) {
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
    val errorMessage = GetMessageErrorUseCase(errorUi = addPetViewModel.getErrorUi() ?: ErrorUi())
    val actionLabel = stringResource(SharedRes.strings.hide)
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

