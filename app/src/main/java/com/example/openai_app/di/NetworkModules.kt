package com.example.openai_app.di

import com.example.openai_app.model.remote.APIServices
import com.example.openai_app.model.remote.AuthInterceptor
import com.example.openai_app.model.repository.RemoteRepository
import com.example.openai_app.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModules {
    @Singleton
    @Provides
    fun provideBaseUrl(): String{
        return BASE_URL
    }

    @Singleton
    @Provides
    fun provideConverterFactory(): Converter.Factory{
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    fun provideAuthInterceptor(): AuthInterceptor{
        return AuthInterceptor()
    }
    @Singleton
    @Provides
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor
    ): OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .callTimeout(60, TimeUnit.SECONDS)
            .build()
    }
    @Singleton
    @Provides
    fun provideRetrofit(
        baseUrl: String,
        converter: Converter.Factory,
        client: OkHttpClient
    ): Retrofit{
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(converter)
            .client(client)
            .build()
    }

    @Singleton
    @Provides
    fun provideAPIService(
        retrofit: Retrofit
    ): APIServices {
        return retrofit.create(APIServices::class.java)
    }

    @Singleton
    @Provides
    fun provideRemoteRepository(
        apiServices: APIServices
    ): RemoteRepository{
        return RemoteRepository(apiServices)
    }
}