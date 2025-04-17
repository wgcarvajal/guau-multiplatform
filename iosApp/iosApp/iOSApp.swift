import SwiftUI
import SwiftSDK
import GoogleSignIn
import ComposeApp

@main
struct iOSApp: App {

    init() {
        MainViewControllerKt.doInitKoin()
        Backendless.shared.initApp(applicationId: "A8772C77-85B7-4381-A95C-8F4DDA5B8189", apiKey: "9F8D3A23-0F04-4944-B297-12D6B067E040")
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
