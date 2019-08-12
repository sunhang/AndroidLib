package sunhang.openlibrary

import android.content.Context
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