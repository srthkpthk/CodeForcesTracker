package com.srthk.codeforcestracker.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.srthk.codeforcestracker.data.repository.AppRepository

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(
    private val repository: AppRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = MainViewModel(repository) as  T
}