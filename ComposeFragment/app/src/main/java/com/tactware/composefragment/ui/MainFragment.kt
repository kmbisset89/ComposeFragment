package com.tactware.composefragment.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import java.util.concurrent.atomic.AtomicInteger

class MainFragment : Fragment() {
    companion object {
        val atomicInteger = AtomicInteger()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                Column {
                    Text(
                        "Main Fragment was created : ${atomicInteger.incrementAndGet()}",
                        fontSize = 24.sp
                    )
                }
            }
        }
    }
}