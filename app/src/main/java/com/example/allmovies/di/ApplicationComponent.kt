package com.example.allmovies.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import javax.inject.Singleton

@Singleton
@Component(modules = [
    MainModule::class,
    NetworkModule::class,
    ViewModelBuilderModule::class,
    DataModule::class,
    DispatcherModule::class,
    SubcomponentsModule::class
])
interface ApplicationComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): ApplicationComponent
    }

    fun mainComponent(): MainComponent.Factory
}

@Module(subcomponents = [MainComponent::class])
object SubcomponentsModule