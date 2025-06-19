package com.example.schedulemanagerapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.schedulemanagerapp.ui.components.Routes
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }
    val snackbarHostState = remember { SnackbarHostState() }


    Scaffold(
        topBar = { TopAppBar(title = { Text("Register") }) },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            var welcomeMessage by remember { mutableStateOf<String?>(null) }

            Button(onClick = {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener {
                        val userEmail = FirebaseAuth.getInstance().currentUser?.email ?: ""
                        welcomeMessage = "Welcome $userEmail!"
                        navController.navigate(Routes.COURSE_LIST) {
                            popUpTo(Routes.LOGIN) { inclusive = true }
                        }
                    }
                    .addOnFailureListener { error = it.message }
            }) {
                Text("Register")
            }
            error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            LaunchedEffect(welcomeMessage) {
                welcomeMessage?.let {
                    snackbarHostState.showSnackbar(it)
                    welcomeMessage = null
                }
            }

        }

    }

}
