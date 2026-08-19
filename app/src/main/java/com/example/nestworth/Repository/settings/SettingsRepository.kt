package com.example.nestworth.Repository.settings

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.settingsDataStore by preferencesDataStore(
    name = "app_settings"
)

private val THEME_KEY = stringPreferencesKey("theme")
private val CURRENCY_KEY = stringPreferencesKey("currency")
private val TIME_RANGE_KEY = stringPreferencesKey("time_range")


class SettingsRepository(
    private val context: Context
) {

    val settings: Flow<AppSettings> =
        context.settingsDataStore.data.map { preferences ->

            AppSettings(
                themeMode = preferences[THEME_KEY]
                    ?.let { ThemeMode.valueOf(it) }
                    ?: ThemeMode.DARK,

                currency = preferences[CURRENCY_KEY]
                    ?.let { Currency.valueOf(it) }
                    ?: Currency.EUR,

                timeRange = preferences[TIME_RANGE_KEY]
                    ?.let { TimeRange.valueOf(it) }
                    ?: TimeRange.MAX
            )
        }

    suspend fun setTheme(theme: ThemeMode) {
        context.settingsDataStore.edit { preferences ->
            preferences[THEME_KEY] = theme.name
        }
    }

    suspend fun setCurrency(currency: Currency) {
        context.settingsDataStore.edit { preferences ->
            preferences[CURRENCY_KEY] = currency.name
        }
    }

    suspend fun setTimeRange(timeRange: TimeRange) {
        context.settingsDataStore.edit { preferences ->
            preferences[TIME_RANGE_KEY] = timeRange.name
        }
    }
}