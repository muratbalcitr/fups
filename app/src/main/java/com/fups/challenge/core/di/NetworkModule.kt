package com.fups.challenge.core.di

import android.content.Context
import com.fups.challenge.BuildConfig
import com.fups.challenge.core.di.qualifers.DefaultOkHttpClientBuilder
import com.fups.challenge.core.di.qualifers.ProjectOkHttpClient
import com.fups.challenge.core.di.qualifers.ProjectRetrofit
import com.fups.challenge.core.netwok.DEFAULT_CALL_TIMEOUT_MILLIS
import com.fups.challenge.core.netwok.DEFAULT_CONNECT_TIMEOUT_MILLIS
import com.fups.challenge.core.netwok.DEFAULT_READ_TIMEOUT_MILLIS
import com.fups.challenge.core.netwok.DEFAULT_WRITE_TIMEOUT_MILLIS
import com.fups.data.remote.ProjectService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideProjectService(
        @ProjectRetrofit projectRetrofit: Retrofit
    ): ProjectService = projectRetrofit.create(ProjectService::class.java)

    @ProjectRetrofit
    @Provides
    fun provideProjectRetrofit(
        @ProjectOkHttpClient projectOkHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder().apply {
        val gson: Gson = GsonBuilder()
            .setLenient()
            .create()

        baseUrl(BuildConfig.SERVICE_URL)
        addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        addConverterFactory(GsonConverterFactory.create(gson))
        client(projectOkHttpClient)
    }.build()

    @ProjectOkHttpClient
    @Provides
    fun provideProjectOkHttpClient(
        @DefaultOkHttpClientBuilder okHttpClientBuilder: OkHttpClient.Builder,
        @ApplicationContext context: Context
    ) = okHttpClientBuilder.apply {
    }.build()

    @Provides
    @DefaultOkHttpClientBuilder
    fun provideDefaultOkHttpBuilder(
        @ApplicationContext context: Context
    ): OkHttpClient.Builder = OkHttpClient.Builder()
        .addInterceptor(provideLoggingInterceptor())
        .callTimeout(DEFAULT_CALL_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
        .connectTimeout(DEFAULT_CONNECT_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
        .readTimeout(DEFAULT_READ_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
        .writeTimeout(DEFAULT_WRITE_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)
}
