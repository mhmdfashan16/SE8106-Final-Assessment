package com.example.mobileassessment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.mobileassessment.data.AppDatabase
import com.example.mobileassessment.navigation.AppNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userDao = AppDatabase.getDatabase(applicationContext).userDao()

        setContent {
            MaterialTheme {
                Surface {
                    AppNavigation(userDao = userDao)
                }
            }
        }
    }
}
