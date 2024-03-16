package com.aryasurya.restaurantreview.core.domain.repository

import com.aryasurya.restaurantreview.core.data.Resource
import com.aryasurya.restaurantreview.core.domain.model.Restaurant
import kotlinx.coroutines.flow.Flow

interface IRestaurantRepository {
  fun getAllRestaurant(): Flow<Resource<List<Restaurant>>>

  fun getFavoriteRestaurant(): Flow<List<Restaurant>>

  fun setFavoriteRestaurant(restaurant: Restaurant, state: Boolean)

  fun searchRestaurants(searchQuery: String): Flow<List<Restaurant>>
}