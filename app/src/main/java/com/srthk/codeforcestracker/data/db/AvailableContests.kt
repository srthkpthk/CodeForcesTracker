package com.srthk.codeforcestracker.data.db

import android.annotation.SuppressLint
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.srthk.codeforcestracker.util.Constants.DAY_DIV
import com.srthk.codeforcestracker.util.Constants.HOUR_DIV
import java.text.SimpleDateFormat
import java.util.*

@Entity
data class AvailableContests(
    val durationSeconds: Int,
    val frozen: Boolean,
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val phase: String,
    val relativeTimeSeconds: Int,
    var startTimeSeconds: Int,
    val type: String
) {
    @SuppressLint("SimpleDateFormat")
    fun toTime(): String =
        SimpleDateFormat("dd/MM/yyy hh:mm a").format(Date(startTimeSeconds.toLong() * 1000))

    fun duration() = if (durationSeconds / HOUR_DIV > 24) {
        "${durationSeconds / DAY_DIV} Days"
    } else {
        "${durationSeconds / HOUR_DIV} Hour(s)"
    }

}