package com.srthk.codeforcestracker.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.srthk.codeforcestracker.data.db.AppDatabase
import com.srthk.codeforcestracker.data.db.AvailableContests
import com.srthk.codeforcestracker.data.network.API
import com.srthk.codeforcestracker.data.network.SafeApiRequest
import com.srthk.codeforcestracker.util.ApiException
import com.srthk.codeforcestracker.util.Constants.MIN_INTERVAL
import com.srthk.codeforcestracker.util.InternetNotAvailableException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class AppRepository(
    private val api: API,
    private val db: AppDatabase,
    private val prefs: PreferenceProvider
) : SafeApiRequest() {
    private val contests = MutableLiveData<List<AvailableContests>>()
    var isInternetAvailable = true
    var isApiException = false

    init {
        contests.observeForever {
            saveAvailableContests(it)
        }
    }

    suspend fun getAvailableContests(): LiveData<List<AvailableContests>> =
        withContext(Dispatchers.IO) {
            fetchData()
            db.getAppDao().getAvailableContests()
        }

    private suspend fun fetchData() {
        val lastSaved = prefs.getLastSavedAt()
        if (lastSaved == null || fetchNeeded(LocalDateTime.parse(lastSaved))) {
            try {
                val response = apiRequest { api.getAvailableContests() }
                contests.postValue(response.result)
            } catch (e: InternetNotAvailableException) {
                Log.d("repo error", e.message!!)
                isInternetAvailable = false
            } catch (e: ApiException) {
                isApiException = true
            }

        }
    }

    private fun fetchNeeded(dataSavedAt: LocalDateTime): Boolean =
        ChronoUnit.MINUTES.between(dataSavedAt, LocalDateTime.now()) > MIN_INTERVAL
//        Duration.between(dataSavedAt, LocalDateTime.now()) > MIN_INTERVAL

    private

    fun saveAvailableContests(it: List<AvailableContests>) {
        CoroutineScope(Dispatchers.IO).launch {
            prefs.savelastSavedAt(LocalDateTime.now().toString())
            db.getAppDao().saveAvailableContests(it)
        }

    }
}