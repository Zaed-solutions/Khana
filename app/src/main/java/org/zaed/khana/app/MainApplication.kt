package org.zaed.khana.app

import android.app.Application
import com.facebook.FacebookSdk
import com.facebook.FacebookSdk.sdkInitialize
import com.facebook.appevents.AppEventsLogger
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.zaed.khana.app.di.appModule
import org.zaed.khana.data.source.local.model.RecentSearchEntity

@Suppress("DEPRECATION")
class MainApplication : Application() {
    companion object {
        lateinit var realm: Realm
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(appModule)
        }
        sdkInitialize(applicationContext)
        AppEventsLogger.activateApp(this@MainApplication)
        println("Facebook hash key: ${FacebookSdk.getApplicationSignature(this)}")


        realm = Realm.open(
            configuration = RealmConfiguration.create(
                schema = setOf(
                    RecentSearchEntity::class
                )
            )
        )
    }
}