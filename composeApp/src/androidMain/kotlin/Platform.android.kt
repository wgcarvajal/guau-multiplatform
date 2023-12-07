import android.os.Build
import core.utils.constants.PlatformConstants

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

actual fun getPlatformName():String = PlatformConstants.ANDROID