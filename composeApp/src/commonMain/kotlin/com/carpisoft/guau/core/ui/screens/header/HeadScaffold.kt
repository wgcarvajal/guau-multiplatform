package com.carpisoft.guau.core.ui.screens.header

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.TransitEnterexit
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.carpisoft.guau.core.utils.constants.PlatformConstants
import com.carpisoft.guau.getPlatformName
import guau.composeapp.generated.resources.Res
import guau.composeapp.generated.resources.exit_center
import guau.composeapp.generated.resources.my_profile
import guau.composeapp.generated.resources.sign_off
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import com.carpisoft.guau.ui.theme.BackgroundHead
import com.carpisoft.guau.ui.theme.Green100


@OptIn(ExperimentalResourceApi::class)
@Composable
fun HeadScaffold(
    title: String,
    showAccountOptions: Boolean,
    showNavigation: Boolean,
    showExitCenter: Boolean,
    showButtonAddOnTopBar: Boolean,
    titleFontSize: TextUnit,
    iconSize: Dp,
    appBarHeight: Dp,
    dropdownMenuWidth: Dp,
    signOffOnClick: () -> Unit,
    onExitVet: () -> Unit,
    onBackOnClick: () -> Unit,
    onAddOnClick: () -> Unit,
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
                IconButton(
                    onClick = onBackOnClick, colors = IconButtonDefaults.iconButtonColors(
                        containerColor = Color.Transparent,
                        contentColor = Green100,
                        disabledContainerColor = Color.Transparent,
                        disabledContentColor = Color.Gray
                    )
                ) {
                    Icon(
                        modifier = Modifier.size(iconSize),
                        imageVector = Icons.AutoMirrored.Filled.ArrowBackIos,
                        contentDescription = ""
                    )
                }
            }
        } else {
            null
        },
        actions = {
            if (getPlatformName() == PlatformConstants.IOS && showButtonAddOnTopBar) {
                IconButton(onClick = onAddOnClick) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = ""
                    )
                }
            }
            if (showAccountOptions) {
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
                            Text(text = stringResource(Res.string.my_profile))
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
                                Text(text = stringResource(Res.string.exit_center))
                            }
                        })
                    }
                    Divider()
                    DropdownMenuItem(onClick = {
                        openMenu = false
                        signOffOnClick()
                    }, text = {
                        Row() {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Logout,
                                contentDescription = ""
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(text = stringResource(Res.string.sign_off))
                        }
                    })
                }
            }
        }
    )
}