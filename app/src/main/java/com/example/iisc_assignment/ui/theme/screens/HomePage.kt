package com.example.iisc_assignment.ui.theme.screens
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.iisc_assignment.ui.theme.navigation.NavigationRoute
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(
    onLogout: () -> Unit = {},
    onDiscoveryEnable: () -> Unit = {},
    navController: NavController
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    // Gradient Brush
    val gradientBrush = Brush.verticalGradient(
        colors = listOf(Color(0xFF66BB6A), Color(0xFF43A047))
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                // Drawer Header
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        "RoadGrid",
                        style = MaterialTheme.typography.headlineSmall.copy(fontSize = 22.sp),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Divider()

                // Drawer Items
                NavigationDrawerItem(
                    label = { Text("Home") },
                    selected = false,
                    icon = { Icon(Icons.Default.Home, contentDescription = null) },
                    onClick = { scope.launch { drawerState.close() } }
                )
                NavigationDrawerItem(
                    label = { Text("Settings") },
                    selected = false,
                    icon = { Icon(Icons.Default.Settings, contentDescription = null) },
                    onClick = { scope.launch { drawerState.close() } }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Dashboard") },
                    actions = {
                        IconButton(onClick = {
                            navController.navigate(NavigationRoute.LOGIN) {
                                popUpTo(NavigationRoute.HOME) { inclusive = true }
                            }
                            onLogout()
                        }) {
                            Icon(Icons.Default.ExitToApp, contentDescription = "Logout")
                        }
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    if (drawerState.isClosed) drawerState.open() else drawerState.close()
                                }
                            }
                        ) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                )
            },
            bottomBar = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Button(
                        onClick = {
                            navController.navigate(NavigationRoute.DEVICES) {
                                popUpTo(NavigationRoute.HOME) { inclusive = true }
                            }
                            onDiscoveryEnable()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp),
                        shape = MaterialTheme.shapes.large
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "Enable Discovery",
                                style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp)
                            )
                            Icon(Icons.Default.ArrowForward, contentDescription = "Next")
                        }
                    }
                }
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .background(brush = gradientBrush)
                    .padding(16.dp)
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    "Welcome to RoadGrid 🚗⚡",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White
                )
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text("Quick Actions", style = MaterialTheme.typography.titleMedium)
                        Text(
                            "Use the discovery button below to scan for nearby devices and chargers.",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}