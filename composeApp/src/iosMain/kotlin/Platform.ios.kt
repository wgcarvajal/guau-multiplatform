import platform.UIKit.UIDevice
import core.utils.constants.PlatformConstants

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()

actual fun getPlatformName():String = PlatformConstants.IOS