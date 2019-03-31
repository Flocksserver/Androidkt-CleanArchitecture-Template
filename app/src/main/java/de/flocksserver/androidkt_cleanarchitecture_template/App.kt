package de.flocksserver.androidkt_cleanarchitecture_template

import android.app.Application
import android.content.Context
import de.flocksserver.androidkt_cleanarchitecture_template.di.components.ApplicationComponent
import de.flocksserver.androidkt_cleanarchitecture_template.di.components.DaggerApplicationComponent
import de.flocksserver.androidkt_cleanarchitecture_template.di.modules.ApplicationModule

/**
 * Created by marcel on 19.06.17.
 */
open class App : Application() {


    val applicationComponent: ApplicationComponent
        get() = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()

    override fun onCreate() {
        super.onCreate()
        applicationComponent.inject(this)
    }

    companion object {
        operator fun get(context: Context): App {
            return context.applicationContext as App
        }
    }
}