package com.tactware.composefragment.ui

import android.widget.FrameLayout
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.FragmentContainerView

@ExperimentalAnimationApi
@Composable
fun MainUI(
    mainUI: @Composable () -> Unit,
    flyoutId: Int,
    viewCreationCallback: () -> Unit,
    fragmentRemovalCallback: () -> Unit
) {
    val flyoutState = rememberSaveable {
        mutableStateOf(false)
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .background(color = MaterialTheme.colors.secondary)
            ) {
                Icon(
                    Icons.Default.Menu,
                    contentDescription = "Show Menu",
                    modifier = Modifier
                        .size(55.dp)
                        .clickable {
                            flyoutState.value = !flyoutState.value
                        }
                        .align(Alignment.CenterVertically)
                        .padding(start = 4.dp)
                )
            }
            mainUI()
        }
        AnimatedVisibility(
            visible = flyoutState.value.also { if (!it) fragmentRemovalCallback() },
            modifier = Modifier
                .fillMaxHeight(.8f)
                .fillMaxWidth(.4f)
                .align(Alignment.CenterStart)
                .padding(start = 8.dp)
        ) {
            AndroidView(modifier = Modifier.fillMaxSize(), factory = {
                FrameLayout(it).apply {
                    id = flyoutId
                    viewCreationCallback()
                }
            })
        }
    }
}