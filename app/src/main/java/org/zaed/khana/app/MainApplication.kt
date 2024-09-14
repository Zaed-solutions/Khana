package org.zaed.khana.app

import android.app.Application
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.zaed.khana.app.di.appModule

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(appModule)
        }
        FacebookSdk.sdkInitialize(applicationContext)
        AppEventsLogger.activateApp(this@MainApplication)
        println("Facebook hash key: ${FacebookSdk.getApplicationSignature(this)}")


    }
}