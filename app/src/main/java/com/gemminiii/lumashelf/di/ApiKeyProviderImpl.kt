package com.gemminiii.lumashelf.di

import com.gemminiii.core.network.ApiKeyProvider
import com.gemminiii.lumashelf.BuildConfig
import javax.inject.Inject

class ApiKeyProviderImpl @Inject constructor(): ApiKeyProvider {
    override fun getApiKey(): String = BuildConfig.POISKKINO_API_KEY
}