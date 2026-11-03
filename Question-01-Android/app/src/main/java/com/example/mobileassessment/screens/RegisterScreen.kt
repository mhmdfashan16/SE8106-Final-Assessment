package com.example.mobileassessment.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import com.example.mobileassessment.data.User
import com.example.mobileassessment.data.UserDao
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(
    userDao: UserDao,
    onRegistrationSuccess: () -> Unit,
    onBackToLogin: () -> Unit
) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("User Registration", fontSize = 27.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(20.dp))

        OutlinedTextField(
            value = username, onValueChange = { username = it },
            label = { Text("Username") }, modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(Modifier.height(10.dp))
        OutlinedTextField(
            value = email, onValueChange = { email = it },
            label = { Text("Email") }, modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(Modifier.height(10.dp))
        OutlinedTextField(
            value = password, onValueChange = { password = it },
            label = { Text("Password") }, modifier = Modifier.fillMaxWidth(),
            singleLine = true, visualTransformation = PasswordVisualTransformation()
        )
        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                if (username.isBlank() || email.isBlank() || password.isBlank()) {
                    message = "Please fill in all fields."
                    isError = true
                } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()) {
                    message = "Please enter a valid email address."
                    isError = true
                } else {
                    scope.launch {
                        if (userDao.findByUsername(username.trim()) != null) {
                            message = "Username already exists."
                            isError = true
                        } else {
                            userDao.insertUser(
                                User(
                                    username = username.trim(),
                                    email = email.trim(),
                                    password = password
                                )
                            )
                            message = "Registration successful."
                            isError = false
                            onRegistrationSuccess()
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) { Text("Register") }

        TextButton(onClick = onBackToLogin) { Text("Back to Login") }

        if (message.isNotBlank()) {
            Text(message, color = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary)
        }
    }
}
