package www.bizpro.com.tw.library.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

object DateUtils {

    private var format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.TAIWAN)
    private var calendar: Calendar = Calendar.getInstance()


    /**
     * 取得現在時間
     */
    fun getNowTime(): Date {
        return calendar.time
    }

    /**
     * 日期 -> 字串
     */
    fun formatDateToString(date: Date): String {
        return format.format(date)
    }

    /**
     * 日期 -> 字串 (自訂規則)
     * @param ruleStr  ex: yyyy-MM-dd HH:mm:ss
     * @param date
     */
    fun formatDateToString(ruleStr: String, date: Date): String {
        val format = SimpleDateFormat(ruleStr, Locale.TAIWAN)
        return format.format(date)
    }

    /**
     * yyyy-MM-dd HH:mm:ss
     * ex : 1663319926284 -> 2022-09-16 17:18:46
     */
    fun getTimeToDate(formatRule: String, timeStamp: Long): String {
        val rule = SimpleDateFormat(formatRule, Locale.TAIWAN)
        calendar.time = Date(timeStamp)
        return rule.format(calendar.time)
    }

    /**
     * 傳遞格式2022-02-08-00-00-00
     * 年-月-日-時-分-秒
     */
    fun getDate(dateStr: String): Date? {
        val regex =
            "[0-9]{4}-([0][0-9]|[1][0-2])-([0-2][0-9]|[3][0-1])-([0-1][0-9]|[2][0-4])-[0-5][0-9]-[0-5][0-9]"
        return if (Pattern.matches(regex, dateStr)) {
            val array = dateStr.split("-")
            val year = array[0]
            val month = array[1]
            val dayOfMonth = array[2]
            val hour = array[3]
            val minute = array[4]
            val second = array[5]
//            Log.d("Develop", "$year-$month-$dayOfMonth-$hour-$minute-$second")
            calendar.set(
                year.toInt(),
                month.toInt() - 1,
                dayOfMonth.toInt(),
                hour.toInt(),
                minute.toInt(),
                second.toInt()
            )
            calendar.time
        } else {
            null
        }
    }

    /**
     * 自訂格式轉日期
     * @param ruleStr ex:yyyy-MM-dd
     * @param parseStr ex: 2022-02-22
     * return String
     */
    fun getDate(ruleStr: String, parseStr: String): String? {
        return try {
            val format = SimpleDateFormat(ruleStr)
            val date = format.parse(parseStr)
            calendar.time = date
            return format.format(calendar.time)
        } catch (e: ParseException) {
            null
        }
    }

    /**
     * 自訂格式轉日期
     * @param parseStr ex: 2022-02-22 14:00:00
     * return Date
     */
    fun parseDate(ruleStr: String, parseStr: String): Date? {
        return try {
            val format = SimpleDateFormat(ruleStr)
            val date = format.parse(parseStr)
            calendar.time = date
            return calendar.time
        } catch (e: ParseException) {
            null
        }
    }

    /**
     * 比較第一個日期是否在第二個之前
     */
    fun compareDateIsAfter(targetDate: Date, date: Date): Boolean {
        return targetDate.after(date)
    }

    fun compareDateIsBefore(targetDate: Date, date: Date): Boolean {
        return targetDate.before(date)
    }

    /**
     * 取得今日日期 (String)
     * @param interval 間格符號 預設:-
     */
    fun getToday(interval: String?): String {
        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH]
        val day = calendar[Calendar.DAY_OF_MONTH]
        return if (interval == null) {
            year.toString() + "-" + addZero(month + 1) + "-" + addZero(day)
        } else {
            year.toString() + interval + addZero(month + 1) + interval + addZero(day)
        }
    }

    fun addZero(number: Int): String { //數字小於10補0
        return if (number < 10) "0$number" else "" + number
    }

    /**
     * 日期增減計算
     * @param type  ex: Calender.YEAR
     * @param count  ex: 1 or -1
     */
    fun countDay(targetDate: Date, type: Int, count: Int): Date {
        calendar.time = targetDate
        when (type) {
            Calendar.YEAR -> calendar.add(Calendar.YEAR, count)
            Calendar.MONTH -> calendar.add(Calendar.MONTH, count)
            Calendar.DAY_OF_MONTH -> calendar.add(Calendar.DAY_OF_MONTH, count)
            Calendar.HOUR -> calendar.add(Calendar.HOUR, count)
            Calendar.MINUTE -> calendar.add(Calendar.MINUTE, count)
            Calendar.SECOND -> calendar.add(Calendar.SECOND, count)
        }
        return calendar.time
    }


}