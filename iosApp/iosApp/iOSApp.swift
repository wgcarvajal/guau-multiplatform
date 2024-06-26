import SwiftUI
import GoogleSignIn
import ComposeApp

@main
struct iOSApp: App {

    init() {
        MainViewControllerKt.doInitKoin()
    }
    var body: some Scene {
        WindowGroup {
                ContentView(
                    loginWithGoogle: loginWithGoogle,
                    signOutWithGoogle: signOutWithGoogle
                ).onOpenURL(perform: { url in
                    GIDSignIn.sharedInstance.handle(url)
                })
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
            MainViewControllerKt.onLoginWithGoogle(token: user.idToken!.tokenString)
        }
    }
}

func signOutWithGoogle(){
    GIDSignIn.sharedInstance.signOut()
    MainViewControllerKt.onSignOutWithGoogle()
}
