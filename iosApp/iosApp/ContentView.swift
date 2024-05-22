import UIKit
import GoogleSignIn
import SwiftUI
import ComposeApp

struct ComposeView: UIViewControllerRepresentable {
    var loginWithGoogle:()->Void
    var signOutWithGoogle:()-> Void
    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController(
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



