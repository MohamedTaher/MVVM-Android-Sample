package com.taher.footballdata.common

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.taher.footballdata.data.datarepository.DataRepository
import com.taher.footballdata.data.datarepository.source.local.database.AppDatabase
import com.taher.footballdata.data.datarepository.source.remote.network.ApiFactory
import com.taher.footballdata.data.datarepository.source.remote.network.ApiInterface
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton


class MyApplication: Application(), KodeinAware {

    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@MyApplication))


        bind<DataRepository>() with singleton { DataRepository(kodein) }

        bind<ViewModelProviderFactory>() with provider { ViewModelProviderFactory(kodein) }

        bind<AppDatabase>() with singleton { AppDatabase.getInstance(this@MyApplication) }

        bind<ApiInterface>() with singleton { ApiFactory.footballDataApi }
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

}