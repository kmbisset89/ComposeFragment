package com.tactware.composefragment

import android.os.Bundle
import android.view.View
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.commit
import com.tactware.composefragment.ui.FlyoutFragment
import com.tactware.composefragment.ui.MainUI
import com.tactware.composefragment.ui.theme.ComposeFragmentTheme

@ExperimentalAnimationApi
class MainActivity : FragmentActivity() {
    companion object {
        private const val FLYOUT_FRAGMENT_TAG = "FLYOUT_FRAGMENT_TAG"
    }

    private val flyoutId by lazy { View.generateViewId() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeFragmentTheme {
                MainUI(
                    mainUI = {

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
}

