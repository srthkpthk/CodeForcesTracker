package com.srthk.codeforcestracker

import android.app.Application
import com.srthk.codeforcestracker.data.db.AppDatabase
import com.srthk.codeforcestracker.data.network.API
import com.srthk.codeforcestracker.data.network.NetworkConnectionInterceptor
import com.srthk.codeforcestracker.data.repository.AppRepository
import com.srthk.codeforcestracker.data.repository.PreferenceProvider
import com.srthk.codeforcestracker.ui.main.MainViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class CodeForcesApp : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@CodeForcesApp))

        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { API(instance()) }
        bind() from singleton { AppDatabase(instance()) }
        bind() from singleton { PreferenceProvider(instance()) }
        bind() from singleton { AppRepository(instance(), instance(), instance()) }
        bind() from provider { MainViewModelFactory(instance()) }
    }
}