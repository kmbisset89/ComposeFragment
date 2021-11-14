package com.tactware.composefragment

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.commit
import com.tactware.composefragment.ui.FlyoutFragment
import com.tactware.composefragment.ui.MainFragment
import com.tactware.composefragment.ui.MainUI
import com.tactware.composefragment.ui.theme.ComposeFragmentTheme

@ExperimentalAnimationApi
class MainActivity : FragmentActivity() {
    companion object {
        private const val FLYOUT_FRAGMENT_TAG = "FLYOUT_FRAGMENT_TAG"
        private const val MAIN_FRAGMENT_TAG = "MAIN_FRAGMENT_TAG"
    }

    private val flyoutId by lazy { View.generateViewId() }
    private val mainId by lazy { View.generateViewId() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeFragmentTheme {
                MainUI(
                    mainUI = {
                        AndroidView(factory = {
                            FrameLayout(it).apply {
                                id = mainId
                                launchFragment()
                            }
                        })
                    },
                    flyoutId = flyoutId,
                    viewCreationCallback = {
                        supportFragmentManager.commit {
                            add(
                                flyoutId,
                                supportFragmentManager.fragmentFactory.instantiate(
                                    classLoader,
                                    FlyoutFragment::class.java.name
                                ),
                                FLYOUT_FRAGMENT_TAG
                            )
                        }
                    },
                    fragmentRemovalCallback = {
                        supportFragmentManager.fragments.find { it.tag == FLYOUT_FRAGMENT_TAG }
                            ?.let {
                                supportFragmentManager.commit {
                                    remove(it)
                                }
                            }
                    }
                )
            }
        }
    }

    private fun launchFragment() {
        supportFragmentManager.commit {
            add(
                mainId,
                supportFragmentManager.fragmentFactory.instantiate(
                    classLoader,
                    MainFragment::class.java.name
                ),
                MAIN_FRAGMENT_TAG
            )
        }
    }
}

