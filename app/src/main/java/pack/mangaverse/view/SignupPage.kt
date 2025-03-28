package pack.mangaverse.view

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import pack.mangaverse.data.models.Utilisateur
import pack.mangaverse.destinations.HomePageDestination

@Destination
@Composable
fun SignupPage(
    navigator: DestinationsNavigator,
    modifier: Modifier = Modifier
) {
    var pseudo by remember { mutableStateOf(TextFieldValue("")) }
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    val firestore = FirebaseFirestore.getInstance()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Inscription",
            style = MaterialTheme.typography.headlineLarge,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = pseudo,
            onValueChange = { pseudo = it },
            label = { Text("Pseudo") },
            modifier = Modifier.fillMaxWidth(),
            isError = pseudo.text.isEmpty() && !isLoading,
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.Gray,
                cursorColor = Color.White
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth(),
            isError = email.text.isEmpty() && !isLoading,
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.Gray,
                cursorColor = Color.White
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Mot de passe") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth(),
            isError = password.text.isEmpty() && !isLoading,
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.Gray,
                cursorColor = Color.White
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = Color.Red,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        Button(
            onClick = {
                if (email.text.isNotEmpty() && password.text.isNotEmpty() && pseudo.text.isNotEmpty()) {
                    isLoading = true
                    auth.createUserWithEmailAndPassword(email.text, password.text)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val user = auth.currentUser
                                user?.let { firebaseUser ->
                                    val userDoc = firestore.collection("utilisateur").document(firebaseUser.uid)

                                    val newUser = Utilisateur(
                                        uid = firebaseUser.uid,
                                        pseudo = pseudo.text,
                                        pdp = "https://m.media-amazon.com/images/M/MV5BMTQ5Nzg2MTgwMl5BMl5BanBnXkFtZTcwNTA0NjcxMw@@._V1_FMjpg_UX1000_.jpg",
                                        favoris = emptyList()
                                    )

                                    userDoc.set(newUser)
                                        .addOnSuccessListener {
                                            Toast.makeText(context, "Compte créé avec succès", Toast.LENGTH_SHORT).show()
                                            navigator.navigate(HomePageDestination())
                                        }
                                        .addOnFailureListener { e ->
                                            errorMessage = "Erreur Firestore: ${e.message}"
                                            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                                        }
                                }
                            } else {
                                errorMessage = task.exception?.message ?: "Erreur inconnue"
                                Toast.makeText(context, "Erreur : $errorMessage", Toast.LENGTH_SHORT).show()
                            }
                            isLoading = false
                        }
                } else {
                    errorMessage = "Veuillez remplir tous les champs"
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            enabled = !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = Color.White,
                    strokeWidth = 2.dp
                )
            } else {
                Text("S'inscrire")
            }
        }
    }
}
