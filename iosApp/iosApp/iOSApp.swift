import SwiftUI
import GoogleSignIn
import shared

@main
struct iOSApp: App {
    var body: some Scene {
        WindowGroup {
            ZStack {
                Color.white.ignoresSafeArea(.all) // status bar color
                ContentView(
                    loginWithGoogle: loginWithGoogle,
                    signOutWithGoogle: signOutWithGoogle
                )
            }.onOpenURL(perform: { url in
                GIDSignIn.sharedInstance.handle(url)
            }).preferredColorScheme(.light)
        }
    }
}

func loginWithGoogle(){
    guard let presentingViewController = (UIApplication.shared.connectedScenes.first as? UIWindowScene)?.windows.first?.rootViewController else {return}
    GIDSignIn.sharedInstance.signIn(withPresenting: presentingViewController){result,error in
        if let error = error {
            print("error: \(error.localizedDescription)")
            return
        }
        if let result = result{
            let user = result.user
            Main_iosKt.onLoginWithGoogle(token: user.idToken!.tokenString)
        }
    }
}

func signOutWithGoogle(){
    GIDSignIn.sharedInstance.signOut()
    Main_iosKt.onSignOutWithGoogle()
}
