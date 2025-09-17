package com.example.iisc_assignment.ui.theme.presentation

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


val userss = mutableMapOf<String, String>() // In-memory for demo
val phoneOtpMap = mutableMapOf<String, String>() // Store phone & OTP temporarily

@Preview
@Composable
fun LoginScreen() {
    var userName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

    fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    Box(
        Modifier
            .fillMaxSize()
            .background(color = Color.Cyan),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .border(2.dp, Color.Gray, shape = RoundedCornerShape(20.dp)) // Add border first
                .background(color = Color.White, shape = RoundedCornerShape(20.dp)) // Then background
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Login", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = userName,
                onValueChange = { userName = it },
                label = { Text("User Name", color =Color.Black) },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Blue,
                    unfocusedBorderColor = Color.Gray
                ))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password", color = Color.Black) },
                visualTransformation = PasswordVisualTransformation(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Green,
                    unfocusedBorderColor = Color.Gray
                )
            )

            Spacer(Modifier.height(16.dp))

            Button(onClick = {
                error = when {
                    userName.isBlank() || password.isBlank() -> "Fields cannot be empty"
                    !isValidEmail(userName) -> "Please enter a valid email address"
                    userss[userName] == password -> {

                        ""
                    }

                    else -> "Invalid credentials"
                }
            }
                ,  colors = ButtonDefaults.buttonColors(Color(0xFF0077B6))
            ) {
                Text("Login", color = Color.White, fontWeight = FontWeight.Bold)
            }

            TextButton(onClick = {  }) {
                Text("Register",textDecoration = TextDecoration.Underline)
            }

            if (error.isNotEmpty()) {
                Spacer(Modifier.height(8.dp))
                Text(error, color = Color.Red)
            }
        }
    }
}