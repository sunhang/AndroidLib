package sunhang.openlibrary

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
}