package www.bizpro.com.tw.library.utils

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class DateUtilsUnitTest {
    private val format: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    private val calendar: Calendar = Calendar.getInstance()

    @Test
    fun getDateYetParseReturnStringTest() {
        //arrange
        val realAnswer = "2022-02-08 14:20:00"
        val fakeAnswer = "2022-02-08 14:21:00"
        //act
        val resultOne = format.format(DateUtils.getDate("2022-02-08-14-20-00")!!)
        val resultTwo = DateUtils.getDate("")
        val resultThree = DateUtils.getDate("20-02-08-22-")
        //assert-One
        assertEquals(realAnswer, resultOne)
        assertNotEquals(fakeAnswer, resultOne)
        //assert-Two
        assertEquals(null, resultTwo)
        //assert-Three
        assertEquals(null, resultThree)
    }

    @Test
    fun getDateAlreadyParseReturnStringTest() {
        //act
        val resultOne = DateUtils.getDate("yyyy-MM-dd", "2022-02-08")!!
        val resultTwo = DateUtils.getDate("yyyy-MM-dd HH:ss:mm", "2022-02-08 14:2000")
        val resultThree = DateUtils.getDate("yyyy-MM-d HH:ss:mm", "2022-02-08 14:20:00")
        //assert-One
        assertEquals("2022-02-08", resultOne)
        assertNotEquals(null, resultOne)
        //assert-Two
        assertEquals(null, resultTwo)
        //assert-Three
        assertEquals("2022-02-8 14:20:00", resultThree)
        assertNotEquals(null, resultThree)
    }

    @Test
    fun parseDate() {
        //arrange
        calendar.set(2022, 1, 8, 0, 0, 0)
        //act
        val resultOne: Date? = DateUtils.parseDate("yyyy-MM-dd", "2022-02-08")
        val resultTwo: Date? = DateUtils.parseDate("yyyy-MM-dd HH", "2022-02-08")
        val resultThree: Date? = DateUtils.parseDate("", "")
        //assert
        assertEquals(calendar.time.toString(), resultOne.toString())
        assertEquals(null, resultTwo)
        assertEquals(null, resultThree)
    }

    @Test
    fun getToday() {
        //arrange
        val year = calendar.get(Calendar.YEAR)
        val month = DateUtils.addZero(calendar.get(Calendar.MONTH) + 1)
        val day = DateUtils.addZero(calendar.get(Calendar.DAY_OF_MONTH))
        //act
        val resultOne = DateUtils.getToday(null)
        val resultTwo = DateUtils.getToday("/")
        val resultThree = DateUtils.getToday("\\")
        //assert
        assertEquals("$year-$month-$day", resultOne)
        assertEquals("$year/$month/$day", resultTwo)
        assertNotEquals("$year-$month-$day", resultTwo)
        assertEquals("$year\\$month\\$day", resultThree)
    }

    @Test
    fun countDay() {
        //act
        val resultOne =
            DateUtils.countDay(DateUtils.getDate("2022-09-20-14-00-00")!!, Calendar.YEAR, 1)
        val resultTwo =
            DateUtils.countDay(DateUtils.getDate("2022-09-20-14-00-00")!!, Calendar.MONTH, -10)
        val resultThree = DateUtils.countDay(
            DateUtils.getDate("2022-09-20-14-00-00")!!,
            Calendar.DAY_OF_MONTH,
            11
        )
        //assert
        assertEquals(
            DateUtils.getDate("2023-09-20-14-00-00")!!.time.toString(),
            resultOne.time.toString()
        )
        assertEquals(
            DateUtils.getDate("2021-11-20-14-00-00")!!.time.toString(),
            resultTwo.time.toString()
        )
        assertEquals(
            DateUtils.getDate("2022-10-01-14-00-00")!!.time.toString(),
            resultThree.time.toString()
        )
        assertNotEquals(
            DateUtils.getDate("2022-10-02-14-00-00")!!.time.toString(),
            resultThree.time.toString()
        )
    }

}