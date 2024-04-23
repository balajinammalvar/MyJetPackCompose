package com.example.myjetpackcompose

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.myjetpackcompose.ui.activity.LoginForm
import com.example.myjetpackcompose.ui.theme.MyJetPackComposeTheme

class LoginActivity :ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent{
            MyJetPackComposeTheme {
                LoginForm()
            }
        }
    }
}

