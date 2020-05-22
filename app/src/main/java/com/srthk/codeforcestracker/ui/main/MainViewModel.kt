package com.srthk.codeforcestracker.ui.main

import androidx.lifecycle.ViewModel
import com.srthk.codeforcestracker.data.repository.AppRepository
import com.srthk.codeforcestracker.util.lazyDeferred

class MainViewModel(
    repository: AppRepository
) : ViewModel() {
    val availableContests by lazyDeferred { repository.getAvailableContests() }
    val isInternetAvailable by lazy { repository.isInternetAvailable }
    val isApiException by lazy { repository.isApiException }
}
