package com.vats.customvideo.utils

interface IVideoViewActionListener{
    fun OnSetVideoSource(path: String?)
}

fun formatVideoTime(time: Int): String {
    val timeInSecond = (time / 1000f).toInt()
    val second = timeInSecond % 60
    var secondString = "$second"
    if (second < 10) {
        secondString = "0$second"
    }
    val timeInMinute = timeInSecond / 60
    val minute = timeInMinute % 60
    val timeInHours = timeInMinute / 60
    var minuteString = "$minute"
    if (timeInHours > 0 && minute < 9) {
        minuteString = "0$minute"
    }
    var timeString = ""
    if (timeInHours > 0) {
        timeString = "$timeInHours:"
    }
    timeString += "$minuteString:$secondString"
    return timeString
}