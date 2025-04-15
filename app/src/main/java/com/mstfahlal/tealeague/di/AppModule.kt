package com.mstfahlal.tealeague.di

import android.content.Context
import androidx.room.Room
import com.mstfahlal.tealeague.data.data_sources.local.AppDatabase
import com.mstfahlal.tealeague.data.data_sources.remote.CompetitionRemoteDataImp
import com.mstfahlal.tealeague.data.data_sources.remote.FootballApiService
import com.mstfahlal.tealeague.data.data_sources.remote.ICompetitionRemoteData
import com.mstfahlal.tealeague.data.repository.CompetitionRepositoryImp
import com.mstfahlal.tealeague.domain.repository.ICompetitionRepository
import com.mstfahlal.tealeague.domain.usecase.GetAllCompetition
import com.mstfahlal.tealeague.domain.usecase.IGetAllCompetition
import com.mstfahlal.tealeague.utils.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideOkhttp(): OkHttpClient {
        val client=OkHttpClient.Builder()
            .connectTimeout(50, TimeUnit.SECONDS)
            .writeTimeout(50,TimeUnit.SECONDS)
            .readTimeout(50,TimeUnit.SECONDS)
            .callTimeout(50,TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response {
                    val originalRequest = chain.request()
                    val originalUrl = originalRequest.url
                    val url = originalUrl.newBuilder().build()
                    val requestBuilder = originalRequest.newBuilder().url(url)
                        .addHeader("Accept", "application/json")
                    val request = requestBuilder.build()
                    val response = chain.proceed(request)
                    response.code
                    return response
                }
            }).build()
        return client
    }
    @Provides
    @Singleton
    fun provideRetrofitApi(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): FootballApiService {
        return retrofit.create(
            FootballApiService::class.java
        )
    }
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext, AppDatabase::class.java, "tealeague.db").allowMainThreadQueries().fallbackToDestructiveMigration().build()
    }
    @Provides
    fun provideCompetitionDataSourceRemote(apiService:FootballApiService) :
            ICompetitionRemoteData = CompetitionRemoteDataImp(apiService)
    @Provides
    fun provideCompetitionRepo(
        competitionRemoteData : ICompetitionRemoteData,databaseApp: AppDatabase,
    ) : ICompetitionRepository = CompetitionRepositoryImp(competitionRemoteData,databaseApp)
    @Provides
    fun provideCompetitionUseCase(repo:ICompetitionRepository): IGetAllCompetition = GetAllCompetition(repo)

}