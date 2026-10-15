package com.gemminiii.lumashelf.di

import com.gemminiii.core.network.ApiKeyProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Binds
    abstract fun bindApiKeyProvider(impl: ApiKeyProviderImpl): ApiKeyProvider
}