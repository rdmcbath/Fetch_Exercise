package com.example.fetchexercise.di.modules

import com.example.fetchexercise.data.repository.ItemRepository
import com.example.fetchexercise.data.repository.ItemRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideItemRepository(): ItemRepository {
        return ItemRepositoryImpl()
    }
}