package com.carpisoft.guau.pet.ui.screens

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import com.carpisoft.guau.admission.ui.util.AdmissionHelper
import com.carpisoft.guau.core.domain.usecase.GetMessageErrorUseCase
import com.carpisoft.guau.core.ui.constants.ScreenEnum
import com.carpisoft.guau.core.ui.model.ErrorUi
import com.carpisoft.guau.core.ui.model.UiStructureProperties
import com.carpisoft.guau.core.ui.screens.dialogs.TwoButtonDialog
import com.carpisoft.guau.core.ui.screens.itemlist.ItemPet
import com.carpisoft.guau.core.ui.screens.loading.SimpleLoading
import com.carpisoft.guau.core.ui.screens.scaffold.GuauScaffoldSimpleWithSearch
import com.carpisoft.guau.core.ui.screens.snackbars.DecoupledSnackBar
import com.carpisoft.guau.pet.domain.model.PetResp
import guau.composeapp.generated.resources.Res
import guau.composeapp.generated.resources.cancel
import guau.composeapp.generated.resources.hide
import guau.composeapp.generated.resources.pets
import guau.composeapp.generated.resources.retry
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel


class PetsScreen : Screen {

    companion object {
        const val TAG = "PetsScreen"
    }

    override val key: ScreenKey
        get() = TAG

    @Composable
    override fun Content() {
        val petsViewModel = koinViewModel<PetsViewModel>()
        val admissionHelper = koinInject<AdmissionHelper>()
        val navigator: Navigator? = LocalNavigator.current
        val loading by petsViewModel.loading.collectAsState()
        val loadingMore by petsViewModel.loadingMore.collectAsState()
        val showError by petsViewModel.showError.collectAsState()
        val pets by petsViewModel.pets.collectAsState()
        val searchText by petsViewModel.searchText.collectAsState()
        Screen(
            pets = pets,
            searchText = searchText,
            loading = loading,
            loadingMore = loadingMore,
            showError = showError,
            onSelectPet = {
                admissionHelper.setPetSelected(petResp = it)
                navigator?.pop()
            },
            onValueChange = {},
            onEmptyOnClick = {},
            onLoadMore = {},
            addOnClick = { navigator?.push(item = SelectPetTypeScreen()) },
            onBackOnClick = {
                navigator?.pop()
            })
        TwoButtonDialog(
            show = showError,
            message = GetMessageErrorUseCase(errorUi = petsViewModel.getErrorUi() ?: ErrorUi()),
            confirmButtonText = stringResource(Res.string.retry),
            cancelButtonText = stringResource(Res.string.cancel),
            confirmButton = {
                petsViewModel.dismissErrorDialog()
                petsViewModel.loadPetsData()
            },
            onDismissRequest = {
                petsViewModel.dismissErrorDialog()
                //onBackOnClick()
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
        val actionLabel = stringResource(Res.string.hide)
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
}

@Composable
private fun Screen(
    pets: List<PetResp>,
    searchText: String,
    loading: Boolean,
    loadingMore: Boolean,
    showError: Boolean,
    onSelectPet: ((PetResp) -> Unit)? = null,
    onValueChange: (String) -> Unit,
    onEmptyOnClick: () -> Unit,
    onLoadMore: () -> Unit,
    addOnClick: () -> Unit = {},
    onBackOnClick: () -> Unit
) {
    if (loading) {
        SimpleLoading()
    } else if (!showError) {
        val state = rememberLazyListState()
        GuauScaffoldSimpleWithSearch(
            listState = state,
            title = stringResource(Res.string.pets),
            searchText = searchText,
            loadingMore = loadingMore,
            onLoadMore = onLoadMore,
            onValueChange = onValueChange,
            onEmptyOnClick = onEmptyOnClick,
            showAddActionButton = true,
            onBack = onBackOnClick,
            onClickAddActionButton = addOnClick
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
                        petType = pet.breed.species.name,
                        onClick = {
                            if (onSelectPet != null) {
                                onSelectPet(pet)
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun PetsScreen(
    uiStructureProperties: UiStructureProperties,
    petsViewModel: PetsViewModel,
    onBackOnClick: () -> Unit,
    onSelectPet: ((PetResp) -> Unit)? = null,
    addOnClick: () -> Unit
) {
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

    Screen(
        pets = pets,
        searchText = searchText,
        loading = loading,
        loadingMore = loadingMore,
        showError = showError,
        onSelectPet = onSelectPet,
        onValueChange = {},
        onEmptyOnClick = {},
        onLoadMore = {},
        addOnClick = {},
        onBackOnClick = {

        })
    TwoButtonDialog(
        show = showError,
        message = GetMessageErrorUseCase(errorUi = petsViewModel.getErrorUi() ?: ErrorUi()),
        confirmButtonText = stringResource(Res.string.retry),
        cancelButtonText = stringResource(Res.string.cancel),
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
    val actionLabel = stringResource(Res.string.hide)
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