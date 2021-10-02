package com.example.eden.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.Light
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.unit.sp
import com.example.eden.R

val NunitoSans = FontFamily(
    Font(R.font.nunito_sans_bold, Bold),
    Font(R.font.nunito_sans_light, Light),
    Font(R.font.nunito_sans_semi_bold, SemiBold)
)

val Typography = Typography(
    defaultFontFamily = NunitoSans,
    h1 = TextStyle(fontSize = 18.sp, letterSpacing = 0.sp,fontWeight = Bold),
    h2 = TextStyle(fontSize = 14.sp, letterSpacing = 0.15.sp , fontWeight = Bold),
    subtitle1 = TextStyle(fontSize = 16.sp, letterSpacing = 0.sp,fontWeight = Light),
    body1 = TextStyle(fontSize = 14.sp, letterSpacing = 0.sp,fontWeight = Light),
    body2 = TextStyle(fontSize = 12.sp, letterSpacing = 0.sp,fontWeight = Light),
    button = TextStyle(fontSize = 14.sp, letterSpacing = 1.sp,fontWeight = SemiBold),
    caption = TextStyle(fontSize = 12.sp, letterSpacing = 0.sp,fontWeight = SemiBold)
)
