package com.santimattius.android.startup

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.santimattius.android.feature.FeatureActivity
import com.santimattius.android.startup.service.AppService
import com.santimattius.android.startup.service.CrashTrackerService
import com.santimattius.android.startup.ui.theme.AndroidStartupTheme
import org.koin.android.ext.android.inject


class LoginActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidStartupTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    KoinLoginScreen()
                }
            }
        }
    }
}

@Composable
fun KoinLoginScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            var userName by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }

            Image(
                painter = painterResource(id = R.drawable.koin_new_logo),
                contentDescription = "Koin Logo",
                modifier = Modifier.size(80.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = userName,
                onValueChange = { newUserName ->
                    userName = newUserName
                },
                label = { Text("Username, email or mobile number") },
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { newPassword ->
                    password = newPassword
                },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
            )

            Spacer(modifier = Modifier.height(16.dp))

            val currentContext = LocalContext.current
            Button(
                onClick = {
                    if (userName == "santimattius" && password == "123456") {
                        currentContext.startActivity(
                            Intent(
                                currentContext,
                                MainActivity::class.java
                            )
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = userName.isNotBlank() && password.isNotBlank()
            ) {
                Text("Log in")
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = {
                Toast.makeText(currentContext, "Coming soon", Toast.LENGTH_SHORT).show()
            }) {
                Text("Forgot password?", color = Color(0xFF007AFF))
            }

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedButton(
                onClick = {
                    Toast.makeText(currentContext, "Coming soon", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Create new account")
            }
        }

        Text(
            text = "santimattius",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
            style = MaterialTheme.typography.bodySmall,
        )
    }
}


@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    AndroidStartupTheme {
        KoinLoginScreen()
    }
}