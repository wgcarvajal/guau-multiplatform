package com.carpisoft.guau.core.ui.screens.foot

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import guau.composeapp.generated.resources.Res
import guau.composeapp.generated.resources.home
import guau.composeapp.generated.resources.pets
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun Foot(
    selectedIndex: Int
) {
    NavigationBar {
        NavigationBarItem(
            selected = selectedIndex == 1,
            onClick = {},
            icon = {
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = null
                )
            },
            label = {
                Text(text = stringResource(Res.string.home))
            }
        )

        NavigationBarItem(
            selected = selectedIndex == 2,
            onClick = {},
            icon = {
                Icon(
                    imageVector = Icons.Filled.Pets,
                    contentDescription = null
                )
            },
            label = {
                Text(text = stringResource(Res.string.pets))
            }
        )
    }
    /*Column {
        BottomNavigation(backgroundColor = Color.White, elevation = 0.dp) {
            BottomNavigationItem(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight(),
                selected = selectedIndex == 0,
                onClick = {
                    if (selectedIndex != 0) {
                        if (isKiosk) {
                            kioskOnClick()
                        } else {
                            homeOnClick()
                        }
                    }
                },
                label = {
                    if (isKiosk) {
                        Text(
                            text = stringResource(id = R.string.but_main_enable_kiosk),
                            fontSize = footUiParameters.fontSize
                        )
                    } else {
                        Text(
                            text = stringResource(id = R.string.menu_kiosk_home),
                            fontSize = footUiParameters.fontSize
                        )
                    }
                },
                selectedContentColor = BackgroundColor,
                unselectedContentColor = TextColor,
                icon = {
                    if (isKiosk) {
                        Icon(
                            modifier = Modifier.size(footUiParameters.sizeIcon),
                            painter = painterResource(id = R.drawable.ic_kiosk),
                            contentDescription = ""
                        )

                    } else {
                        Icon(
                            modifier = Modifier.size(footUiParameters.sizeIcon),
                            painter = painterResource(id = R.drawable.ic_home_status),
                            contentDescription = ""
                        )
                    }
                })
            CustomBottomNavigationItem(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight(),
                selected = selectedIndex == 1,
                onClick = {
                    if (selectedIndex != 1) {
                        activityRegisterOnClick()
                    }
                },
                label = {
                    Text(
                        text = stringResource(id = R.string.kiosk_Activity),
                        fontSize = footUiParameters.fontSize
                    )
                },
                selectedContentColor = BackgroundColor,
                unselectedContentColor = TextColor,
                icon = {
                    Icon(
                        modifier = Modifier.size(footUiParameters.sizeIcon),
                        painter = painterResource(id = R.drawable.ic_ico_actividad),
                        contentDescription = ""
                    )
                })
            if (showMetrics) {
                CustomBottomNavigationItem(
                    modifier = Modifier
                        .wrapContentWidth()
                        .wrapContentHeight(),
                    selected = selectedIndex == 2,
                    onClick = {
                        if (selectedIndex != 2) {
                            metricsOnClick()
                        }
                    },
                    label = {
                        Text(
                            text = stringResource(id = R.string.but_main_metrics),
                            fontSize = footUiParameters.fontSize
                        )
                    },
                    selectedContentColor = BackgroundColor,
                    unselectedContentColor = TextColor,
                    icon = {
                        Icon(
                            modifier = Modifier.size(footUiParameters.sizeIcon),
                            painter = painterResource(id = R.drawable.ic_metrics),
                            contentDescription = ""
                        )
                    })
            }

            if (showKiosk) {
                CustomBottomNavigationItem(
                    modifier = Modifier
                        .wrapContentWidth()
                        .wrapContentHeight(),
                    selected = selectedIndex == 3,
                    onClick = {
                        if (selectedIndex != 3) {
                            enableKioskOnClick()
                        }
                    },
                    label = {
                        Text(
                            text = stringResource(id = R.string.but_main_enable_kiosk),
                            fontSize = footUiParameters.fontSize
                        )
                    },
                    selectedContentColor = BackgroundColor,
                    unselectedContentColor = TextColor,
                    icon = {
                        Icon(
                            modifier = Modifier.size(footUiParameters.sizeIcon),
                            painter = painterResource(id = R.drawable.ic_kiosk),
                            contentDescription = ""
                        )
                    })
            }

            if (isKiosk) {
                CustomBottomNavigationItem(
                    modifier = Modifier
                        .wrapContentWidth()
                        .wrapContentHeight(),
                    selected = selectedIndex == 4,
                    onClick = {
                        if (selectedIndex != 4) {
                            kioskSettingsOnClick()
                        }
                    },
                    label = {
                        Text(
                            text = stringResource(id = R.string.menu_settings),
                            fontSize = footUiParameters.fontSize
                        )
                    },
                    selectedContentColor = BackgroundColor,
                    unselectedContentColor = TextColor,
                    icon = {
                        Icon(
                            modifier = Modifier.size(footUiParameters.sizeIcon),
                            painter = painterResource(id = R.drawable.ic_setting),
                            contentDescription = ""
                        )
                    })
            }

            CustomBottomNavigationItem(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight(),
                selected = selectedIndex == 5,
                onClick = {
                    if (selectedIndex != 5) {
                        notificationOnClick()
                    }
                },
                label = {
                    Text(
                        text = stringResource(id = R.string.but_main_notifications),
                        fontSize = footUiParameters.fontSize
                    )
                },
                selectedContentColor = BackgroundColor,
                unselectedContentColor = TextColor,
                icon = {
                    if (badgeNotification > 0) {
                        Column() {
                            BadgedBox(badge = {
                                Badge(
                                    backgroundColor = BadgeColor,
                                    contentColor = Color.White
                                ) {
                                    Text(text = badgeNotification.toString())
                                }
                            }) {
                                Icon(
                                    modifier = Modifier.size(footUiParameters.sizeIcon),
                                    painter = painterResource(id = R.drawable.ic_notification),
                                    contentDescription = ""
                                )
                            }
                        }

                    } else {
                        Icon(
                            modifier = Modifier.size(footUiParameters.sizeIcon),
                            painter = painterResource(id = R.drawable.ic_notification),
                            contentDescription = ""
                        )
                    }
                })
        }
    }*/
}