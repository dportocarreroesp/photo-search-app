package com.example.photosearchapp

import android.app.Application
import com.example.photosearchapp.data.AppContainer
import com.example.photosearchapp.data.DefaultAppContainer

class PhotosSearchApplication : Application() {
    /** AppContainer instance used by the rest of classes to obtain dependencies */
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}