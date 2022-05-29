package otus.demo.totalcoverage.coreapi

import android.content.Context
import android.content.SharedPreferences
import retrofit2.Retrofit

interface CoreProvider {

    fun context(): Context

    fun provideRetrofit(): Retrofit

    fun provideSharedPreferences(): SharedPreferences
}