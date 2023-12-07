import core.utils.constants.PlatformConstants

class JVMPlatform: Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"
}

actual fun getPlatform(): Platform = JVMPlatform()

actual fun getPlatformName():String = PlatformConstants.JVM