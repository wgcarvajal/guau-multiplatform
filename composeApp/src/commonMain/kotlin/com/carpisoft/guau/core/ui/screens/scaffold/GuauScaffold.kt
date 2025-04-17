package com.carpisoft.guau.core.ui.screens.scaffold

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.carpisoft.guau.core.ui.screens.foot.Foot
import com.carpisoft.guau.core.ui.screens.header.HeadScaffold
import com.carpisoft.guau.core.ui.screens.search.SearchAndContentTemplateScreen
import com.carpisoft.guau.core.utils.constants.PlatformConstants
import com.carpisoft.guau.getPlatformName

@Composable
private fun GuauScaffold(
    tabNavigator: TabNavigator? = null,
    title: String,
    showTopBar: Boolean,
    showNavigation: Boolean,
    showExitCenter: Boolean,
    showAddActionButton: Boolean,
    showAccountOptions: Boolean,
    onClickAddActionButton: () -> Unit = {},
    signOffOnClick: () -> Unit = {},
    onExitVet: () -> Unit = {},
    onBack: () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        floatingActionButton = if ((getPlatformName() == PlatformConstants.ANDROID || getPlatformName() == PlatformConstants.JVM) && showAddActionButton) {
            {
                FloatingActionButton(
                    shape = RoundedCornerShape(50),
                    onClick = onClickAddActionButton
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Add,
                        contentDescription = "Add FAB",
                        tint = Color.White,
                    )
                }
            }
        } else {
            {}
        },
        topBar = if (showTopBar) {
            {
                HeadScaffold(
                    title = title,
                    showAccountOptions = showAccountOptions,
                    showNavigation = showNavigation,
                    showExitCenter = showExitCenter,
                    showButtonAddOnTopBar = showAddActionButton,
                    titleFontSize = 16.sp,
                    iconSize = 20.dp,
                    appBarHeight = 40.dp,
                    dropdownMenuWidth = 200.dp,
                    signOffOnClick = signOffOnClick,
                    onExitVet = onExitVet,
                    onBackOnClick = onBack,
                    onAddOnClick = onClickAddActionButton
                )
            }
        } else {
            {}
        },
        bottomBar = if (tabNavigator != null) {
            {
                Foot(
                    tabNavigator = tabNavigator
                )
            }
        } else {
            {}
        }
    ) { contentPadding ->
        content(contentPadding)
    }
}

@Composable
fun GuauScaffoldTab(
    tabNavigator: TabNavigator,
    title: String,
    showExitCenter: Boolean = true,
    showAddActionButton: Boolean = false,
    showAccountOptions: Boolean = true,
    onClickAddActionButton: () -> Unit = {},
    onExitVet: () -> Unit = {},
    signOffOnClick: () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    GuauScaffold(
        tabNavigator = tabNavigator,
        title = title,
        showTopBar = true,
        showNavigation = false,
        showExitCenter = showExitCenter,
        showAddActionButton = showAddActionButton,
        showAccountOptions = showAccountOptions,
        onClickAddActionButton = onClickAddActionButton,
        signOffOnClick = signOffOnClick,
        onExitVet = onExitVet,
        content = content
    )
}

@Composable
fun GuauScaffoldTabWithSearch(
    tabNavigator: TabNavigator,
    title: String,
    loadingMore: Boolean,
    searchText: String,
    showExitCenter: Boolean = true,
    showAddActionButton: Boolean = false,
    showAccountOptions: Boolean = true,
    onClickAddActionButton: () -> Unit = {},
    onExitVet: () -> Unit = {},
    signOffOnClick: () -> Unit = {},
    onLoadMore: () -> Unit,
    onValueChange: (String) -> Unit,
    onEmptyOnClick: () -> Unit,
    content: @Composable () -> Unit
) {
    GuauScaffold(
        tabNavigator = tabNavigator,
        title = title,
        showTopBar = false,
        showNavigation = false,
        showExitCenter = showExitCenter,
        showAddActionButton = showAddActionButton,
        showAccountOptions = showAccountOptions,
        onClickAddActionButton = onClickAddActionButton,
        signOffOnClick = signOffOnClick,
        onExitVet = onExitVet
    ) {
        val focusManager = LocalFocusManager.current
        val keyboardController = LocalSoftwareKeyboardController.current
        var activeSearch by rememberSaveable { mutableStateOf(false) }
        val state = rememberLazyListState()
        SearchAndContentTemplateScreen(
            title = title,
            listState = state,
            searchText = searchText,
            activeSearch = activeSearch,
            loadingMore = loadingMore,
            keyboardController = keyboardController,
            focusManager = focusManager,
            showAdd = showAddActionButton,
            showNavigation = false,
            onLoadMore = onLoadMore,
            onBackOnClick = {},
            searchOnClick = { activeSearch = true },
            onValueChange = onValueChange,
            onEmptyOnClick = onEmptyOnClick,
            onCloseOnClick = { activeSearch = false },
            addOnClick = onClickAddActionButton
        ) {
            content()
        }
    }
}

@Composable
fun GuauScaffoldSimple(
    title: String,
    showAddActionButton: Boolean = false,
    showAccountOptions: Boolean = false,
    showExitCenter: Boolean = false,
    onClickAddActionButton: () -> Unit = {},
    onBack: () -> Unit,
    signOffOnClick: () -> Unit = {},
    onExitVet: () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    GuauScaffold(
        title = title,
        showTopBar = true,
        showNavigation = true,
        showExitCenter = showExitCenter,
        showAddActionButton = showAddActionButton,
        showAccountOptions = showAccountOptions,
        onBack = onBack,
        onClickAddActionButton = onClickAddActionButton,
        content = content
    )
}

@Composable
fun GuauScaffoldSimpleWithSearch(
    listState: LazyListState,
    title: String,
    showAddActionButton: Boolean = false,
    loadingMore: Boolean,
    searchText: String,
    onClickAddActionButton: () -> Unit = {},
    onLoadMore: () -> Unit,
    onValueChange: (String) -> Unit,
    onEmptyOnClick: () -> Unit,
    onBack: () -> Unit,
    content: @Composable () -> Unit
) {
    GuauScaffold(
        title = "",
        showTopBar = false,
        showNavigation = false,
        showExitCenter = false,
        showAddActionButton = showAddActionButton,
        showAccountOptions = false,
        onClickAddActionButton = onClickAddActionButton
    ){
        val focusManager = LocalFocusManager.current
        val keyboardController = LocalSoftwareKeyboardController.current
        var activeSearch by rememberSaveable { mutableStateOf(false) }
        SearchAndContentTemplateScreen(
            title = title,
            listState = listState,
            searchText = searchText,
            activeSearch = activeSearch,
            loadingMore = loadingMore,
            keyboardController = keyboardController,
            focusManager = focusManager,
            showAdd = showAddActionButton,
            showNavigation = true,
            onLoadMore = onLoadMore,
            onBackOnClick = onBack,
            searchOnClick = { activeSearch = true },
            onValueChange = onValueChange,
            onEmptyOnClick = onEmptyOnClick,
            onCloseOnClick = { activeSearch = false },
            addOnClick = onClickAddActionButton
        ) {
            content()
        }
    }
}

