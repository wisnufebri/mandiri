package com.test.wisnufebri.util

fun appendZeroBeforeNumber(num: Int): String {
    return if (num < 10) "0$num" else num.toString()
}