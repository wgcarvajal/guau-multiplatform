package com.carpisoft.guau

import MainView
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class MainActivity : /*PreComposeActivity()*/AppCompatActivity() {

    private val signInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken("987785305857-mq7o4d7l3pt2ispufecqt09fe271p4c1.apps.googleusercontent.com") // Request id token if you intend to verify google user from your backend server
        .requestEmail()
        .build()
    lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent() {
            /* val context = LocalContext.current.applicationContext
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
             MainView(
                 database = createDatabase(context = context),
                 dataStore = createDataStore(context = context),
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
                 })*/
            MainView()
        }
    }
}