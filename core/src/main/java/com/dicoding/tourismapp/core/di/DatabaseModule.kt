package com.dicoding.tourismapp.core.di

import android.content.Context
import androidx.room.Room
import com.dicoding.tourismapp.core.data.source.local.room.RestaurantDao
import com.dicoding.tourismapp.core.data.source.local.room.RestaurantDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

//    @Singleton
//    @Provides
//    fun provideDatabase(@ApplicationContext context: Context): TourismDatabase = Room.databaseBuilder(
//        context,
//        TourismDatabase::class.java, "Tourism.db"
//    ).fallbackToDestructiveMigration().build()
//
//    @Provides
//    fun provideTourismDao(database: TourismDatabase): TourismDao = database.tourismDao()

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): RestaurantDatabase = Room.databaseBuilder(
        context,
        RestaurantDatabase::class.java, "Restaurant.db"
    ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideRestaurantDao(database: RestaurantDatabase): RestaurantDao = database.restaurantDao()
}