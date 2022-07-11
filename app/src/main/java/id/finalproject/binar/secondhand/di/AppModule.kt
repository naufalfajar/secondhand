package id.finalproject.binar.secondhand.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.finalproject.binar.secondhand.helper.SharedPreferences
import id.finalproject.binar.secondhand.model.local.SecondHandDatabase
import id.finalproject.binar.secondhand.service.SecondHandApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(SecondHandApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideSecondHandApi(retrofit: Retrofit): SecondHandApi =
        retrofit.create(SecondHandApi::class.java)

    @Provides
    @Singleton
    fun provideDatabase(app: Application): SecondHandDatabase =
        Room.databaseBuilder(app, SecondHandDatabase::class.java, "secondhand_database").build()

    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context) = SharedPreferences(context)
}