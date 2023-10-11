package core.ui.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.unit.dp
import core.ui.screens.collection.InfiniteGridHandler
import core.ui.screens.collection.InfiniteListHandler
import core.ui.screens.loading.CustomLoading
import core.utils.constants.PlatformConstants
import getPlatformName
import ui.theme.BackgroundHead
import ui.theme.Green100

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchAndContentTemplateScreen(
    title: String,
    gridState: LazyGridState? = null,
    listState: LazyListState? = null,
    searchText: String,
    activeSearch: Boolean,
    loadingMore: Boolean,
    showAdd:Boolean = false,
    keyboardController: SoftwareKeyboardController?,
    focusManager: FocusManager,
    onLoadMore: () -> Unit,
    onBackOnClick: () -> Unit,
    searchOnClick: () -> Unit,
    addOnClick: () -> Unit = {},
    onValueChange: (String) -> Unit,
    onEmptyOnClick: () -> Unit,
    onCloseOnClick: () -> Unit,
    content: @Composable BoxScope.() -> Unit
) {
    Box(modifier = Modifier.fillMaxSize())
    {
        Box(
            modifier = Modifier.fillMaxWidth()
                .background(BackgroundHead)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(start = 5.dp, end = 10.dp)
                    .height(48.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBackOnClick, colors = IconButtonDefaults.iconButtonColors(
                        containerColor = Color.Transparent,
                        contentColor = Green100,
                        disabledContainerColor = Color.Transparent,
                        disabledContentColor = Color.Gray
                    )
                ) {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        imageVector = Icons.Default.ArrowBackIos,
                        contentDescription = ""
                    )
                }
                if (activeSearch) {
                    SearchBar(
                        modifier = Modifier.fillMaxWidth().padding(top = 5.dp, bottom = 5.dp),
                        searchText = searchText,
                        isEmpty = searchText.isEmpty(),
                        onValueChange = onValueChange,
                        onEmptyOnClick = onEmptyOnClick,
                        onCloseOnClick = onCloseOnClick
                    )
                } else {
                    Text(
                        modifier = Modifier.padding(start = 20.dp),
                        text = title,
                        color = Color.Black
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        if(getPlatformName() == PlatformConstants.IOS && showAdd){
                            IconButton(
                                onClick = addOnClick, colors = IconButtonDefaults.iconButtonColors(
                                    containerColor = Color.Transparent,
                                    contentColor = Color.Gray,
                                    disabledContainerColor = Color.Transparent,
                                    disabledContentColor = Color.Gray
                                )
                            ) {
                                Icon(
                                    modifier = Modifier.size(20.dp),
                                    imageVector = Icons.Default.Add,
                                    contentDescription = ""
                                )
                            }
                        }
                        IconButton(
                            onClick = searchOnClick, colors = IconButtonDefaults.iconButtonColors(
                                containerColor = Color.Transparent,
                                contentColor = Color.Gray,
                                disabledContainerColor = Color.Transparent,
                                disabledContentColor = Color.Gray
                            )
                        ) {
                            Icon(
                                modifier = Modifier.size(20.dp),
                                imageVector = Icons.Default.Search,
                                contentDescription = ""
                            )
                        }
                    }
                }
            }

        }
        Box(
            modifier = Modifier.fillMaxSize().padding(
                top = 48.dp, bottom = if (loadingMore) {
                    40.dp
                } else {
                    0.dp
                }
            ).pointerInput(Unit) {
                detectTapGestures(onTap = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                }, onPress = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                })
            }
        ) {
            content()
        }
        if (loadingMore) {
            CustomLoading(
                modifier = Modifier.fillMaxWidth().height(40.dp).align(Alignment.BottomCenter)
            )
        }
        if(gridState!=null) {
            InfiniteGridHandler(
                gridState = gridState,
                onLoadMore = onLoadMore
            )
        }

        if(listState!=null){
            InfiniteListHandler(
                listState = listState,
                onLoadMore = onLoadMore
            )
        }
    }
}