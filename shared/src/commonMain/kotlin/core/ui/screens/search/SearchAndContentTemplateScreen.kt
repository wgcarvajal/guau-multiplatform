package core.ui.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ui.theme.BackgroundHead

@Composable
fun SearchAndContentTemplateScreen(
    title: String,
    searchText:String,
    activeSearch: Boolean,
    onBackOnClick: () -> Unit,
    searchOnClick: () -> Unit,
    onValueChange: (String) -> Unit,
    onEmptyOnClick: () -> Unit,
    onCloseOnClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize())
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
                        contentColor = Color.Blue,
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
                        onCloseOnClick = onCloseOnClick)
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
        content()
    }
}