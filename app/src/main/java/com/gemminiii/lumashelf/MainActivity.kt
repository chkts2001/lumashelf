package com.gemminiii.lumashelf

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.gemminiii.lumashelf.navigation.LumaShelfNavHost
import com.gemminiii.core.ui.theme.LumaShelfTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.gemminiii.lumashelf.ui.theme.LumashelfTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LumaShelfTheme {
                LumaShelfNavHost()
            }
        }
    }
}