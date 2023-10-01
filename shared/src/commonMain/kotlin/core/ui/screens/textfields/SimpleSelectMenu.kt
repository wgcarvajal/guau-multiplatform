package core.ui.screens.textfields

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.toSize
import ui.theme.OnSurface
import ui.theme.OnSurfaceVariant
import ui.theme.Outline

@Composable
fun SimpleSelectMenu(
    modifier: Modifier,
    value: String,
    label: String,
    placeholder: String,
    list: List<Any>,
    expanded: Boolean,
    arrowOnClick: () -> Unit,
    onClickItem: (Any) -> Unit,
    onDismissRequest: () -> Unit,
    getText: (Any) -> String
) {

    var mTextFieldSize by remember { mutableStateOf(Size.Zero) }
    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown
    Column(modifier = modifier){
        SimpleTextField(
            modifier = Modifier.fillMaxWidth().onGloballyPositioned { coordinates ->
                mTextFieldSize = coordinates.size.toSize()
            },
            value = value,
            onValueChange = {},
            label = label,
            placeholder = placeholder,
            trailingIcon = {
                IconButton(onClick = arrowOnClick) {
                    Icon(
                        imageVector = icon,
                        contentDescription = ""
                    )
                }
            },
            enabled = false,
            readOnly = true,
            colors = OutlinedTextFieldDefaults.colors(
                disabledBorderColor = Outline,
                disabledLabelColor = OnSurfaceVariant,
                disabledTrailingIconColor = OnSurfaceVariant,
                disabledPlaceholderColor = OnSurfaceVariant,
                disabledTextColor = OnSurface
            )
        )
        DropdownMenu(
            modifier = Modifier.width(with(
                LocalDensity.current
            ) {
                mTextFieldSize.width.toDp()
            }),
            expanded = expanded,
            onDismissRequest = onDismissRequest
        ) {
            list.forEach { item ->
                DropdownMenuItem(
                    text = {
                        Text(text = getText(item))
                    },
                    onClick = {
                        onClickItem(item)
                    }
                )
            }
        }
    }

}