package com.srthk.codeforcestracker.util

import android.content.Context
import android.widget.Toast
import com.srthk.codeforcestracker.util.Constants.DAY_DIV
import com.srthk.codeforcestracker.util.Constants.HOUR_DIV
import kotlinx.coroutines.*
import java.io.IOException
import java.util.*

class ApiException(message: String) : IOException(message)
class InternetNotAvailableException(message: String) : IOException(message)

class XtraFunctions {



    fun toDateTime(time: Int): Date =
        Date(time.toLong() * 1000)
}

fun <T> lazyDeferred(block: suspend CoroutineScope.() -> T): Lazy<Deferred<T>> {
    return lazy {
        GlobalScope.async(start = CoroutineStart.LAZY) {
            block.invoke(this)
        }
    }
}

fun Context.toast(m: String) = Toast.makeText(this, m, Toast.LENGTH_LONG).show()