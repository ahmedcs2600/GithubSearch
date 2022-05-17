package com.app.githubsearch.di.modules

import android.content.Context
import com.app.common.BASE_URL
import com.app.common.CERTIFICATE_TRANSPARENCY_URL
import com.app.githubsearch.utils.toURL
import com.app.network.GithubApiService
import com.app.network.core.NetworkErrorProvider
import com.appmattus.certificatetransparency.certificateTransparencyInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class CertificateTransparencyInterceptor

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(
        @LoggingInterceptor loggingInterceptor: Interceptor,
        @AuthInterceptor authInterceptor: Interceptor,
        @CertificateTransparencyInterceptor mCertificateTransparencyInterceptor: Interceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .hostnameVerifier { host, _ -> host == BASE_URL.toURL().host }
            .addNetworkInterceptor(mCertificateTransparencyInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun providesRetrofit(
        okHttpClient: OkHttpClient,
        factory: Converter.Factory
    ): Retrofit {
        return Retrofit.Builder().apply {
            baseUrl(BASE_URL)
            client(okHttpClient)
            addConverterFactory(factory)
        }.build()
    }


    @Provides
    @CertificateTransparencyInterceptor
    fun providesCertificateTransparencyInterceptor(): Interceptor {
        return certificateTransparencyInterceptor {
            +CERTIFICATE_TRANSPARENCY_URL
        }
    }

    @Singleton
    @Provides
    fun providesGithubApiService(retrofit: Retrofit): GithubApiService =
        retrofit.create(GithubApiService::class.java)

    @Singleton
    @Provides
    fun providesNetworkErrorProvider(@ApplicationContext context: Context) =
        NetworkErrorProvider(context)

    @Provides
    @Singleton
    fun providesConverterFactory(): Converter.Factory = GsonConverterFactory.create()
}