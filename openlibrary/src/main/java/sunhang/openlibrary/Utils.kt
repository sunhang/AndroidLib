package sunhang.openlibrary

import android.content.Context
import android.content.res.Configuration
import android.os.Build

fun dumpDeviceInfo(): String {
    return "BOARD:${Build.BOARD}\n" +
            "BOOTLOADER:${Build.BOOTLOADER}\n" +
            "BRAND:${Build.BRAND}\n" +
            "DEVICE:${Build.DEVICE}\n" +
            "DISPLAY:${Build.DISPLAY}\n" +
            "FINGERPRINT:${Build.FINGERPRINT}\n" +
            "HARDWARE:${Build.HARDWARE}\n" +
            "HOST:${Build.HOST}\n" +
            "ID:${Build.ID}\n" +
            "MANUFACTURER:${Build.MANUFACTURER}\n" +
            "MODEL:${Build.MODEL}\n" +
            "PRODUCT:${Build.PRODUCT}\n" +
            "TAGS:${Build.TAGS}\n" +
            "TIME:${Build.TIME}\n" +
            "TYPE:${Build.TYPE}\n" +
            "UNKNOWN:${Build.UNKNOWN}\n" +
            "USER:${Build.USER}\n"
}

/**
 * 没有线程安全，一般用在main线程
 */
fun <T> uiLazy(initializer: () -> T) = lazy(LazyThreadSafetyMode.NONE, initializer)

/**
 * 返回竖屏时的屏幕宽度
 */
fun getScreenWidthOfPortraitMode(context: Context): Int {
    val width = context.resources.displayMetrics.widthPixels
    val height = context.resources.displayMetrics.heightPixels

    // 返回屏幕在竖屏时的宽度
    return if (width < height) width else height
}

val Context.screenWidth: Int
    get() = resources.displayMetrics.widthPixels

val Context.screenHeight: Int
    get() = resources.displayMetrics.heightPixels

val Context.isPortrait: Boolean
    get() = resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT

fun methodStack(): String {
    val elements = Thread.currentThread().stackTrace
    val stringBuilder = StringBuilder()

    // 前三个暂时用不上
    for (i in 3 until elements.size) {
        val element = elements[i]

        val clsName = element.className
        val methodName = element.methodName
        stringBuilder.append("${clsName}#${methodName}+${element.lineNumber}")
        stringBuilder.append("\n")
    }

    return stringBuilder.toString()
}

fun <T> T.checkAndModify(condition: Boolean, modify: (T) -> T): T {
    return if (condition) {
        modify(this)
    } else this
}
