package tech.minthura.handy.application

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import tech.minthura.handy.di.modules.appModule

class HandyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
//            androidLogger()
            androidContext(this@HandyApplication)
            modules(listOf(appModule))
        }
    }
}