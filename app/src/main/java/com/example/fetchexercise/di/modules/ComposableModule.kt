package com.example.fetchexercise.di.modules

import com.example.fetchexercise.data.repository.ItemRepository
import com.example.fetchexercise.data.repository.ItemRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


//@InstallIn specifies availability for the entire app's lifecycle

@Module
@InstallIn(SingletonComponent::class)
object ComposableModule {

    @Provides
    @Singleton
    fun provideItemRepository(): ItemRepository {
        return ItemRepositoryImpl()
    }
}