package com.example.androidcore.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class UserPreferenceRepository @Inject constructor(private val dataStore: DataStore<Preferences>) {

    private object PreferenceKeys {
        val IS_USER_LOGGED_IN = booleanPreferencesKey("is_user_logged_in")
    }

    val isUserLoggedIn: Flow<Boolean> = dataStore.data.catch { exception ->
        if(exception is IOException) emit(emptyPreferences())
        else throw exception
    }.map { preferences->
        preferences[PreferenceKeys.IS_USER_LOGGED_IN]?:false
    }

    suspend fun setUserLoggedIn(isLoggedIn:Boolean){
        dataStore.edit { preference ->
            try {
                preference[PreferenceKeys.IS_USER_LOGGED_IN] = isLoggedIn
            }catch (e:IOException){
                throw e
            }
        }

    }
}