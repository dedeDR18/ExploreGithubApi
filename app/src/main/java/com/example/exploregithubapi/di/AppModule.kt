package com.example.exploregithubapi.di

import android.app.Application
import androidx.room.Room
import com.example.exploregithubapi.data.UserRepositoryImpl
import com.example.exploregithubapi.data.local.room.UserDatabase
import com.example.exploregithubapi.data.remote.network.GithubApiService
import com.example.exploregithubapi.domain.repository.UserRepository
import com.example.exploregithubapi.domain.usecase.GetUsers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
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
    fun provideUsecase(repository: UserRepository) = GetUsers(repository)

    @Provides
    @Singleton
    fun provideRepository(api: GithubApiService, db: UserDatabase): UserRepository {
        return UserRepositoryImpl(api, db.userDao)
    }

    @Provides
    @Singleton
    fun provideDatabase(app: Application): UserDatabase {
        return Room.databaseBuilder(
            app,
            UserDatabase::class.java,
            UserDatabase.DATABASE_NAME,
        ).build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val request = requestBuilder(chain)
                chain.proceed(request)
            }
            .build()
    }

    private fun requestBuilder(chain: Interceptor.Chain): Request {
        val original = chain.request()
        return original.newBuilder()
            .header("Accept", "application/vnd.github.v3+json")
            .header("Authorization", "token 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")
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(): GithubApiService {
        return Retrofit.Builder()
            .baseUrl(GithubApiService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(provideOkHttpClient())
            .build()
            .create(GithubApiService::class.java)
    }


}