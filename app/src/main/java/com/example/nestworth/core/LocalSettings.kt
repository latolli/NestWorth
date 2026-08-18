package com.example.nestworth.core

import androidx.compose.runtime.staticCompositionLocalOf
import com.example.nestworth.Repository.settings.AppSettings

val LocalAppSettings = staticCompositionLocalOf<AppSettings> {
    error("AppSettings not provided")
}