import UIKit
import GoogleSignIn
import SwiftUI
import shared

struct ComposeView: UIViewControllerRepresentable {
    var loginWithGoogle:()->Void
    var signOutWithGoogle:()-> Void
    func makeUIViewController(context: Context) -> UIViewController {
        Main_iosKt.MainViewController(
            database:CreateDatabase_iosKt.createDatabase(),
            datastore:CreateDataStore_iosKt.createDataStore(),
            loginWithGoogle: loginWithGoogle,
            signOutWithGoogle: signOutWithGoogle
        )
    }
    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    var loginWithGoogle:()->Void
    var signOutWithGoogle:()-> Void
    var body: some View {
        ComposeView(
            loginWithGoogle: loginWithGoogle,
            signOutWithGoogle: signOutWithGoogle
        ).ignoresSafeArea(.all, edges: .bottom) // Compose has own keyboard handler
    }
}



