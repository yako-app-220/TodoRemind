package com.yako.todoreminder

import androidx.room.TypeConverter
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Date

class DateConverters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?) : Long? {
        return date?.time
    }
}

class TimeConverters {
    @TypeConverter
    fun stringToTime(value: String): LocalTime? {
        return if (value == "") {
            null
        } else {
            LocalTime.parse(value, DateTimeFormatter.ofPattern("HH:mm"))
        }
    }

    @TypeConverter
    fun timeToString(time: LocalTime?) : String {
        //return time?.let {it?.toString()}
        val ldt: LocalTime
        return if (time == null) {
            return ""
        } else {
            return time.format(DateTimeFormatter.ofPattern("HH:mm"))
        }
    }
}