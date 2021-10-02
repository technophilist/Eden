package com.example.eden.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Surface
import androidx.compose.ui.ExperimentalComposeUiApi
import com.example.eden.ui.screens.LoginScreen
import com.example.eden.ui.screens.SignUpScreen
import com.example.eden.ui.screens.WelcomeScreen
import com.example.eden.ui.theme.EdenAppTheme
import com.google.accompanist.pager.ExperimentalPagerApi

class MainActivity : ComponentActivity() {
    @ExperimentalComposeUiApi
    @ExperimentalPagerApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EdenAppTheme {
                Surface {
                    LoginScreen()
                }
            }
        }
    }
}

