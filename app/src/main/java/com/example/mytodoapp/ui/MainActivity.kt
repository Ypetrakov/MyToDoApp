package com.example.mytodoapp.ui

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.mytodoapp.NavGraphs
import com.example.mytodoapp.appCurrentDestinationAsState
import com.example.mytodoapp.destinations.Destination
import com.example.mytodoapp.destinations.DirectionDestination
import com.example.mytodoapp.destinations.ForgottenTodosScreenDestination
import com.example.mytodoapp.destinations.PreferencesScreenDestination
import com.example.mytodoapp.destinations.SignInScreenDestination
import com.example.mytodoapp.destinations.StartingScreenDestination
import com.example.mytodoapp.destinations.TodoListScreenDestination
import com.example.mytodoapp.feature_auth.presentation.sign_in.GoogleAuthUiClient
import com.example.mytodoapp.feature_auth.presentation.sign_in.SignInScreen
import com.example.mytodoapp.feature_auth.presentation.sign_in.SignInViewModel
import com.example.mytodoapp.feature_auth.presentation.sign_in.StartingScreen
import com.example.mytodoapp.feture_todo.data.local.PreferencesManager
import com.example.mytodoapp.feture_todo.domain.use_case.TodoUseCase
import com.example.mytodoapp.utils.Destinations
import com.example.mytodoapp.feture_todo.presentation.forgotten_todos.ForgottenTodosScreen
import com.example.mytodoapp.feture_todo.presentation.preferences.PreferencesScreen
import com.example.mytodoapp.feture_todo.presentation.todolist.TodoListScreen
import com.example.mytodoapp.startAppDestination
import com.example.mytodoapp.ui.theme.MyToDoAppTheme
import com.example.mytodoapp.utils.LANGUAGE
import com.example.mytodoapp.utils.LanguageOption
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.manualcomposablecalls.composable
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.navigation.popUpTo
import dagger.hilt.EntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.intellij.lang.annotations.Language
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var googleAuthUiClient: GoogleAuthUiClient

    @Inject
    lateinit var todoUseCase: TodoUseCase

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface TodoUseCaseEntryPoint {
        val todoUseCase: TodoUseCase
    }

    private fun determineInitialDestination(intent: Intent): DirectionDestination {
        val flags = intent.flags
        if (flags and Intent.FLAG_ACTIVITY_NEW_TASK != 0 && flags and Intent.FLAG_ACTIVITY_CLEAR_TASK != 0)
            return PreferencesScreenDestination
        return StartingScreenDestination
    }

    override fun attachBaseContext(newBase: Context) {
        val config = Configuration(newBase.resources?.configuration)
        val todoUseCase = EntryPointAccessors.fromApplication(
            newBase,
            TodoUseCaseEntryPoint::class.java
        ).todoUseCase
        val locale = Locale(
            when (todoUseCase.getLanguage()) {
                LanguageOption.Ukrainian -> "uk"
                LanguageOption.English -> "en_US"
            }
        )
        //Locale.setDefault(currentLocale)
        config.setLocale(locale)
        super.attachBaseContext(newBase.createConfigurationContext(config))
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            MyToDoAppTheme {
                Log.d("Locale", resources.configuration.locales.toString())
                Surface(modifier = Modifier.fillMaxSize()) {
                    val drawerWidth = LocalConfiguration.current.screenWidthDp * 0.75
                    val coroutineScope = rememberCoroutineScope()
                    val navController = rememberNavController()
                    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                    val currentDestination: Destination? =
                        navController.appCurrentDestinationAsState().value
                            ?: NavGraphs.root.startAppDestination

                    var currentUser by remember {
                        mutableStateOf(googleAuthUiClient.getSignInUser())
                    }

                    LaunchedEffect(key1 = currentUser, block = {})
                    ModalNavigationDrawer(
                        drawerState = drawerState,
                        gesturesEnabled = drawerState.isOpen,
                        drawerContent = {
                            ModalDrawerSheet(
                                Modifier.width(drawerWidth.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    IconButton(onClick = {
                                        coroutineScope.launch {
                                            drawerState.close()
                                        }
                                    }) {
                                        Icon(
                                            imageVector = Icons.Default.ArrowBack,
                                            contentDescription = "ArrowBack"
                                        )

                                    }
                                    Text(text = currentUser?.username ?: "")
                                    Spacer(modifier = Modifier.weight(1f))
                                    AsyncImage(
                                        model = currentUser?.profilePicture,
                                        contentDescription = "",
                                        modifier = Modifier.clip(CircleShape),
                                        contentScale = ContentScale.Crop
                                    )
                                    Spacer(modifier = Modifier.width(20.dp))
                                }

                                Divider()
                                Destinations.values().forEach { destination ->
                                    if (destination == Destinations.Preferences) {
                                        Divider()
                                    }
                                    NavigationDrawerItem(
                                        label = { Text(stringResource(id = destination.label)) },
                                        selected = currentDestination == destination.direction,
                                        icon = {
                                            Icon(
                                                imageVector = destination.icon,
                                                contentDescription = stringResource(id = destination.label)
                                            )
                                        },
                                        onClick = {
                                            if (currentDestination != destination.direction) {
                                                coroutineScope.launch {
                                                    navController.navigate(destination.direction) {
                                                        popUpTo(NavGraphs.root) {
                                                            saveState = true
                                                            inclusive = true
                                                        }
                                                    }
                                                    drawerState.close()
                                                }

                                            }
                                        })
                                }
                                Divider()
                                NavigationDrawerItem(
                                    label = {
                                        Text(text = "Log out")
                                    },
                                    icon = {
                                        Icon(
                                            imageVector = Icons.Default.Logout,
                                            contentDescription = "LogOut"
                                        )
                                    },
                                    selected = false,
                                    onClick = {
                                        coroutineScope.launch {
                                            googleAuthUiClient.signOut()
                                            navController.navigate(SignInScreenDestination) {
                                                popUpTo(NavGraphs.root)
                                            }
                                            drawerState.close()
                                        }
                                    })

                            }
                        }
                    ) {
                        DestinationsNavHost(
                            navGraph = NavGraphs.root,
                            navController = navController,
                            startRoute = determineInitialDestination(intent)
                        ) {
                            composable(SignInScreenDestination) {
                                val signInViewModel: SignInViewModel = hiltViewModel()
                                val state by signInViewModel.state.collectAsStateWithLifecycle()

                                val launcher = rememberLauncherForActivityResult(
                                    contract = ActivityResultContracts.StartIntentSenderForResult(),
                                    onResult = { result ->
                                        if (result.resultCode == RESULT_OK) {
                                            lifecycleScope.launch {
                                                val signInResult =
                                                    googleAuthUiClient.signInWithIntent(
                                                        intent = result.data ?: return@launch
                                                    )
                                                signInViewModel.onSignInResult(signInResult)
                                            }
                                        }
                                    }
                                )
                                LaunchedEffect(key1 = state.isSignInSuccessful) {
                                    if (state.isSignInSuccessful) {
                                        Toast.makeText(
                                            applicationContext,
                                            "SignInSuccessfull",
                                            Toast.LENGTH_LONG
                                        ).show()
                                        currentUser = googleAuthUiClient.getSignInUser()
                                        navController.navigate(TodoListScreenDestination) {
                                            popUpTo(NavGraphs.root)
                                        }
                                        signInViewModel.resetState()
                                    }
                                }
                                SignInScreen(
                                    state = state,
                                    navigator = destinationsNavigator,
                                    onSingClick = {
                                        lifecycleScope.launch {
                                            val signInIntentIntentSender =
                                                googleAuthUiClient.signIn()
                                            launcher.launch(
                                                IntentSenderRequest.Builder(
                                                    signInIntentIntentSender ?: return@launch
                                                ).build()
                                            )
                                        }
                                    }
                                )
                            }
                            composable(TodoListScreenDestination) {
                                TodoListScreen(
                                    drawerState = drawerState,
                                    navigator = destinationsNavigator
                                )
                            }

                            composable(ForgottenTodosScreenDestination) {
                                ForgottenTodosScreen(
                                    drawerState = drawerState,
                                )
                            }
                            composable(StartingScreenDestination) {
                                LaunchedEffect(key1 = Unit) {
                                    delay(1000)
                                    if (googleAuthUiClient.getSignInUser() != null) {
                                        navController.navigate(TodoListScreenDestination) {
                                            popUpTo(NavGraphs.root)
                                        }
                                    } else {
                                        navController.navigate(SignInScreenDestination) {
                                            Log.d("Nav", "Sign in")
                                            popUpTo(NavGraphs.root)
                                        }
                                    }
                                }
                                StartingScreen(navigator = destinationsNavigator)
                            }
                            composable(PreferencesScreenDestination) {
                                PreferencesScreen(
                                    drawerState = drawerState,
                                    onChangeSettings = {
                                        finish()
                                        val intent =
                                            Intent(this@MainActivity, MainActivity::class.java)
                                        intent.flags =
                                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                                        startActivity(intent)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

