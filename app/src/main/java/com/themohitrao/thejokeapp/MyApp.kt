package com.themohitrao.thejokeapp

import android.app.Application
import com.themohitrao.core_database.di.databaseModule
import com.themohitrao.core_network.di.networkModule
import com.themohitrao.core_ui.di.uiUtilityModule
import com.themohitrao.main.di.viewModelModule
import com.themohitrao.main_common.di.mainFeatureModule
import com.themohitrao.main_common.worker_util.Sync
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.context.startKoin

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApp)
            workManagerFactory()
            modules(getDependencyList())
        }
        Sync.initialize(this@MyApp)
    }

    private fun getDependencyList() = listOf(
        databaseModule,
        networkModule,
        uiUtilityModule,
        mainFeatureModule,
        viewModelModule
    )

}