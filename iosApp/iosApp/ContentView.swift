import UIKit
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
    var body: some View {
        ComposeView(
            loginWithGoogle: {},
            signOutWithGoogle: {}
        ).ignoresSafeArea(.all, edges: .bottom) // Compose has own keyboard handler
    }
}



