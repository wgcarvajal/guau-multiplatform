package core.ui.screens.header

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.TransitEnterexit
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.carpisoft.guau.SharedRes
import core.utils.constants.PlatformConstants
import dev.icerock.moko.resources.compose.stringResource
import getPlatformName
import ui.theme.BackgroundHead


@Composable
fun HeadScaffold(
    title: String,
    showNavigation: Boolean,
    showExitCenter: Boolean,
    showButtonAddOnTopBar:Boolean,
    titleFontSize: TextUnit,
    iconSize: Dp,
    appBarHeight: Dp,
    dropdownMenuWidth: Dp,
    signOffOnClick: () -> Unit,
    onExitVet:()->Unit,
    onBackOnClick: () -> Unit,
    onAddOnClick:()->Unit,
) {
    var openMenu by rememberSaveable { mutableStateOf(false) }
    MyTopAppBar(
        title = {
            Text(
                text = title,
                fontSize = titleFontSize
            )

        },
        appBarHeight = appBarHeight,
        backgroundColor = BackgroundHead,
        navigationIcon = if (showNavigation) {
            {
                IconButton(onClick = onBackOnClick) {
                    Icon(
                        modifier = Modifier.size(iconSize),
                        imageVector = Icons.Default.ArrowBackIos,
                        contentDescription = "",
                    )
                }
            }
        } else {
            null
        },
        actions = {
            if(getPlatformName() == PlatformConstants.IOS && showButtonAddOnTopBar) {
                IconButton(onClick = onAddOnClick) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = ""
                    )
                }
            }
            IconButton(onClick = { openMenu = !openMenu }) {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = ""
                )
            }
            DropdownMenu(
                modifier = Modifier.width(dropdownMenuWidth),
                expanded = openMenu,
                onDismissRequest = { openMenu = false }) {

                DropdownMenuItem(onClick = {
                    openMenu = false


                }, text = {
                    Row() {
                        Icon(imageVector = Icons.Filled.Person, contentDescription = "")
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(text = stringResource(SharedRes.strings.my_profile))
                    }

                })
                if (showExitCenter) {
                    Divider()
                    DropdownMenuItem(onClick = {
                        openMenu = false
                        onExitVet()
                    }, text = {
                        Row() {
                            Icon(
                                imageVector = Icons.Filled.TransitEnterexit,
                                contentDescription = ""
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(text = stringResource(SharedRes.strings.exit_center))
                        }
                    })
                }
                Divider()
                DropdownMenuItem(onClick = {
                    openMenu = false
                    signOffOnClick()
                }, text = {
                    Row() {
                        Icon(imageVector = Icons.Filled.Logout, contentDescription = "")
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(text = stringResource(SharedRes.strings.sign_off))
                    }
                })
            }
        }
    )
}