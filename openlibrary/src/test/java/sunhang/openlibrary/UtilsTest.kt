package sunhang.openlibrary

import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class UtilsTest {
    @Test
    fun methodStackTest() {
        val msg = methodStack()
        println(msg)
    }

    @Test
    fun checkAndModifyTest() {
        val statusBarHeight = 32 // we assume the value of statusbar's height is 32
        val navigationBarHeight = 128 // we assume the value of nav's height is 123
        val screenHeight = 1920
        val statusBarVisible = true
        val navigationBarVisible = false

        val height = screenHeight.checkAndModify(statusBarVisible) {
            it - statusBarHeight
        }.checkAndModify(navigationBarVisible) {
            it - navigationBarHeight
        }

        assertEquals(screenHeight - statusBarHeight/* - navigationBarHeight*/, height)
    }
}