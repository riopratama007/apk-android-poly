package com.poly

import android.app.Application
import android.content.Context
import com.poly.core.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {

    companion object {
        private var instance: MyApplication? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }

        fun application(): Application {
            return instance!!
        }
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }

}