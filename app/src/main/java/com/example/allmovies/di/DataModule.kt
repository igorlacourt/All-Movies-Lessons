package com.example.allmovies.di

import com.example.allmovies.repository.HomeDataSource
import com.example.allmovies.repository.HomeDataSourceImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class DataModule {
    @Singleton
    @Binds
    abstract fun provideHomeDataSource(datasource: HomeDataSourceImpl): HomeDataSource
}