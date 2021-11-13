package com.example.eden.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.ui.ExperimentalComposeUiApi
import com.example.eden.di.EdenApplication
import com.example.eden.ui.screens.EdenApp
import com.example.eden.ui.theme.EdenAppTheme
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.pager.ExperimentalPagerApi

class MainActivity : ComponentActivity() {
    @ExperimentalMaterialApi
    @ExperimentalComposeUiApi
    @ExperimentalPagerApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appContainer = (application as EdenApplication).appContainer
        setContent {
            ProvideWindowInsets {
                EdenAppTheme {
                    Surface {
                        EdenApp(appContainer)
                    }
                }
            }
        }
    }
}

