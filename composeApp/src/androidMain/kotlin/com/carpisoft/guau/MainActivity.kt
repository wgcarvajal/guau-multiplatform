package com.carpisoft.guau

import App
import App2
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import core.data.db.createDatabase
import core.data.preferences.createDataStore
import core.google.onLoginWithGoogle
import core.google.onSignOutWithGoogle

class MainActivity : ComponentActivity() {
    
    private val signInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken("987785305857-mq7o4d7l3pt2ispufecqt09fe271p4c1.apps.googleusercontent.com") // Request id token if you intend to verify google user from your backend server
        .requestEmail()
        .build()
    lateinit var googleSignInClient: GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current.applicationContext
            googleSignInClient = GoogleSignIn.getClient(context, signInOptions)
            val authResultLauncher =
                rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result ->
                    if (result.resultCode == RESULT_OK) {
                        val intent = result.data
                        if (result.data != null) {
                            val task = GoogleSignIn.getSignedInAccountFromIntent(intent)
                            try {
                                val account = task.getResult(ApiException::class.java)
                                if (account != null) {
                                    Log.i("token", account.idToken!!)
                                    onLoginWithGoogle(token = account.idToken!!)
                                } else {
                                    Log.i("wilsiton", "Google sign in failed")
                                }
                            } catch (e: ApiException) {
                                Log.i("wilsiton", e.message ?: "")
                            }
                        }
                    } else {
                        Log.i("wilsiton", "Google sign in failed")
                    }
                }
            App(
                database = createDatabase(context = context),
                datastore = createDataStore(context = context),
                finishCallback = {
                    finish()
                                 },
                loginWithGoogle = {
                    authResultLauncher.launch(googleSignInClient.signInIntent)
                                  },
                signOutWithGoogle = {
                    googleSignInClient.signOut().addOnCompleteListener {
                        onSignOutWithGoogle()
                    }
                })
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App2()
}