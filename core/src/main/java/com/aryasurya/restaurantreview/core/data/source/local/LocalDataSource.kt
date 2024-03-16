package com.aryasurya.restaurantreview.core.data.source.local

import com.aryasurya.restaurantreview.core.data.source.local.entity.RestaurantEntity
import com.aryasurya.restaurantreview.core.data.source.local.room.RestaurantDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val restaurantDao: RestaurantDao) {
    fun getAllRestaurant(): Flow<List<RestaurantEntity>> = restaurantDao.getAllRestaurant()

    fun getFavoriteRestaurant(): Flow<List<RestaurantEntity>> = restaurantDao.getFavoriteRestaurant()

    suspend fun insertRestaurant(restaurantList: List<RestaurantEntity>) = restaurantDao.insertRestaurant(restaurantList)

    fun setFavoriteTourism(restaurant: RestaurantEntity, newState: Boolean) {
        restaurant.isFavorite = newState
        restaurantDao.updateFavoriteRestaurant(restaurant)
    }

    fun searchRestaurants(searchQuery: String): Flow<List<RestaurantEntity>> = restaurantDao.searchRestaurants(searchQuery)
}