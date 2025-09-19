package com.example.iisc_assignment.ui.theme.screens

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.iisc_assignment.ui.theme.green

// ✅ Correct users map (username → password)
val userss = mutableMapOf(
    "admin" to "1234"
)

val phoneOtpMap = mutableMapOf<String, String>()
@Composable
fun LoginScreen(navController: NavController) {
    var userName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

    Box(
        Modifier
            .fillMaxSize()
            .background(color = green),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .border(2.dp, Color.Gray, shape = RoundedCornerShape(20.dp))
                .background(color = Color.White, shape = RoundedCornerShape(20.dp))
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Login", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = userName,
                onValueChange = { userName = it },
                label = { Text("Username", color = Color.Black) },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Blue,
                    unfocusedBorderColor = Color.Gray
                )
            )

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

            Button(
                onClick = {
                    error = when {
                        userName.isBlank() || password.isBlank() -> "Fields cannot be empty"
                        userss[userName] == password -> {
                            // ✅ Login successful
                            navController.navigate("devices") {
                                popUpTo("login") { inclusive = true }
                            }
                            ""
                        }
                        else -> "Invalid credentials"
                    }
                },
                colors = ButtonDefaults.buttonColors(Color(0xFF0077B6))
            ) {
                Text("Login", color = Color.White, fontWeight = FontWeight.Bold)
            }

            if (error.isNotEmpty()) {
                Spacer(Modifier.height(8.dp))
                Text(error, color = Color.Red)
            }
        }
    }
}
