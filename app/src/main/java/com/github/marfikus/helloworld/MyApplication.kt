package com.github.marfikus.helloworld

import android.app.Application
import com.github.marfikus.helloworld.easyMVVM.CacheDataSource
import com.github.marfikus.helloworld.easyMVVM.Model
import com.github.marfikus.helloworld.easyMVVM.ViewModel

class MyApplication : Application() {

    lateinit var viewModel: ViewModel

    override fun onCreate() {
        super.onCreate()
        viewModel = ViewModel(Model(CacheDataSource(this)))
    }
}