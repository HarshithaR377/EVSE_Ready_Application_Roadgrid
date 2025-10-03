package com.example.iisc_assignment.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.iisc_assignment.ui.theme.navigation.NavigationRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChargerTypeScreen(navController: NavController) {
    var selectedType by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Choose Charger Type",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(Modifier.height(32.dp))

            // AC Charger Button
            Button(
                onClick = {
                    navController.navigate(NavigationRoute.DEVICES) {
                        popUpTo(NavigationRoute.ChargerType) { inclusive = true }
                    }
                },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedType == "AC")
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = if (selectedType == "AC")
                        MaterialTheme.colorScheme.onPrimary
                    else
                        MaterialTheme.colorScheme.onSurface
                )
            ) {
                Text("AC Charger")
            }

            // DC Charger Button
            Button(
                onClick = { selectedType = "DC" },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedType == "DC")
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = if (selectedType == "DC")
                        MaterialTheme.colorScheme.onPrimary
                    else
                        MaterialTheme.colorScheme.onSurface
                )
            ) {
                Text("DC Charger")
            }
        }
        Button(
            onClick = {},
            enabled = selectedType != null,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Next")
        }
    }
}
