import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import pack.mangaverse.destinations.LoginPageDestination
import pack.mangaverse.destinations.SignupPageDestination

@Destination
@Composable
fun LogPage(navigator: DestinationsNavigator) {
    val context = LocalContext.current
    var isConnected by remember { mutableStateOf(true) }
    var retryTrigger by remember { mutableIntStateOf(0) }

    LaunchedEffect(retryTrigger) {
        isConnected = checkInternetConnection(context)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            AppHeader(navigator)

            Spacer(modifier = Modifier.height(32.dp))

            if (!isConnected) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    Text(
                        text = "Aucune connexion Internet",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Button(
                        onClick = { retryTrigger++ },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.DarkGray,
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .height(50.dp)
                            .clip(RoundedCornerShape(8.dp))
                    ) {
                        Text("Réessayer", fontSize = 16.sp)
                    }
                }
            } else {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    Text(
                        text = "Bienvenue sur MangaVerse",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Button(
                        onClick = { navigator.navigate(LoginPageDestination) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.DarkGray,
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .height(50.dp)
                            .clip(RoundedCornerShape(8.dp))
                    ) {
                        Text("Connexion", fontSize = 16.sp)
                    }

                    Button(
                        onClick = { navigator.navigate(SignupPageDestination) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color.Black
                        ),
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .height(50.dp)
                            .clip(RoundedCornerShape(8.dp))
                    ) {
                        Text("Inscription", fontSize = 16.sp)
                    }
                }
            }
        }
    }
}

fun checkInternetConnection(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

    return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
}
