package pet.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import core.ui.screens.itemlist.ItemPet
import core.ui.screens.loading.SimpleLoading
import core.ui.screens.search.SearchAndContentTemplateScreen
import core.ui.screens.snackbars.DecoupledSnackBar
import dev.icerock.moko.resources.compose.stringResource
import pet.domain.model.PetResp

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PetsScreen(
    uiStructureProperties: UiStructureProperties,
    petsViewModel: PetsViewModel,
    onBackOnClick: () -> Unit,
    onSelectPet: ((PetResp) -> Unit)? = null,
    addOnClick: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    LaunchedEffect(key1 = 1) {
        uiStructureProperties.onShowTopBar(false)
        uiStructureProperties.onShowBottomBar(false)
        uiStructureProperties.showActionNavigation(true)
        uiStructureProperties.onShowExitCenter(true)
        uiStructureProperties.onSetTitle(ScreenEnum.Pets)
        uiStructureProperties.showAddActionButton(true)
        uiStructureProperties.showActionAccountOptions(false)
        uiStructureProperties.showActionNext(false)
        uiStructureProperties.onEnabledNextAction(false)
        uiStructureProperties.onShowActionBottom(false)
        uiStructureProperties.onShowSaveAction(false)
        uiStructureProperties.onEnabledSaveAction(false)
    }
    val loading by petsViewModel.loading.collectAsState()
    val loadingMore by petsViewModel.loadingMore.collectAsState()
    val showError by petsViewModel.showError.collectAsState()
    val pets by petsViewModel.pets.collectAsState()
    val searchText by petsViewModel.searchText.collectAsState()
    var activeSearch by rememberSaveable { mutableStateOf(false) }

    val state = rememberLazyListState()
    if (loading) {
        SimpleLoading()
    } else if (!showError) {
        SearchAndContentTemplateScreen(
            title = stringResource(SharedRes.strings.pets),
            listState = state,
            searchText = searchText,
            activeSearch = activeSearch,
            loadingMore = loadingMore,
            keyboardController = keyboardController,
            focusManager = focusManager,
            showAdd = true,
            onLoadMore = { petsViewModel.onLoadMorePetData() },
            onBackOnClick = {
                petsViewModel.resetPets()
                onBackOnClick()
            },
            searchOnClick = { activeSearch = true },
            onValueChange = { petsViewModel.onValueSearchPet(it) },
            onEmptyOnClick = { petsViewModel.onValueSearchPet("") },
            onCloseOnClick = { activeSearch = false },
            addOnClick = addOnClick
        ) {
            LazyColumn(
                state = state,
                modifier = Modifier.fillMaxSize()
                    .padding(top = 10.dp, bottom = 10.dp, start = 10.dp, end = 10.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                items(pets) { pet ->
                    ItemPet(
                        identification = pet.customer.identification,
                        name = pet.name,
                        breed = pet.breed.name,
                        customer = "${pet.customer.name} ${pet.customer.lastName}",
                        petType = pet.breed.species.name, onClick = {
                            if (onSelectPet != null) {
                                onSelectPet(pet)
                            }
                        }
                    )
                }
            }
        }
    }
    TwoButtonDialog(
        show = showError,
        message = GetMessageErrorUseCase(errorUi = petsViewModel.getErrorUi() ?: ErrorUi()),
        confirmButtonText = stringResource(SharedRes.strings.retry),
        cancelButtonText = stringResource(SharedRes.strings.cancel),
        confirmButton = {
            petsViewModel.dismissErrorDialog()
            petsViewModel.loadPetsData()
        },
        onDismissRequest = {
            petsViewModel.dismissErrorDialog()
            onBackOnClick()
        })

    LaunchedEffect(key1 = 1) {
        petsViewModel.loadPetsData()
    }
    val snackBarHostState = remember { SnackbarHostState() }
    DecoupledSnackBar(
        snackBarHostState = snackBarHostState,
        containerColor = Color.Red,
        messageColor = Color.White,
        actionColor = Color.White
    )
    val showErrorSnackBar by petsViewModel.showErrorSnackBar.collectAsState()
    val errorMessage = GetMessageErrorUseCase(errorUi = petsViewModel.getErrorUi() ?: ErrorUi())
    val actionLabel = stringResource(SharedRes.strings.hide)
    LaunchedEffect(key1 = showErrorSnackBar) {
        if (showErrorSnackBar) {
            snackBarHostState.showSnackbar(
                message = errorMessage,
                actionLabel = actionLabel,
                duration = SnackbarDuration.Short
            )
            petsViewModel.resetShowErrorSnackBar()
        }
    }
}