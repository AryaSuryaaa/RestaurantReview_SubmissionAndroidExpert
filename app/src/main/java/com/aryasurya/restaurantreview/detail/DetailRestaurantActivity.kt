package com.aryasurya.restaurantreview.detail

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.aryasurya.restaurantreview.R
import com.aryasurya.restaurantreview.core.domain.model.Restaurant
import com.aryasurya.restaurantreview.databinding.ActivityDetailRestaurantBinding
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailRestaurantActivity  : AppCompatActivity() {

  companion object {
    const val EXTRA_DATA = "extra_data"
  }

  private val detailRestaurantViewModel : DetailRestaurantViewModel by viewModels()
  private lateinit var binding: ActivityDetailRestaurantBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityDetailRestaurantBinding.inflate(layoutInflater)
    setContentView(R.layout.activity_detail_restaurant)

    setSupportActionBar(binding.toolbar)

//    val detailRestaurant = intent.getParcelableExtra<Restaurant>(EXTRA_DATA)
//    showDetailRestaurant(detailRestaurant)
    val restaurantJson = intent.getStringExtra(EXTRA_DATA)
    if (restaurantJson != null) {
      val detailRestaurant = Gson().fromJson(restaurantJson, Restaurant::class.java)
      showDetailRestaurant(detailRestaurant)
    } else {
      Toast.makeText(this, "Tidak ada data", Toast.LENGTH_SHORT).show()
    }
  }

  private fun showDetailRestaurant(detailRestaurant: Restaurant?) {
    detailRestaurant?.let {
      supportActionBar?.title = detailRestaurant.name
      binding.content.tvDetailDescription.text = detailRestaurant.description
      Glide.with(this@DetailRestaurantActivity)
        .load("https://restaurant-api.dicoding.dev/images/medium/"+detailRestaurant.pictureId)
        .into(binding.ivDetailImage)

      var statusFavorite = detailRestaurant.isFavorite
      setStatusFavorite(statusFavorite)
      binding.fab.setOnClickListener {
        statusFavorite = !statusFavorite
        detailRestaurantViewModel.setFavoriteRestaurant(detailRestaurant, statusFavorite)
        setStatusFavorite(statusFavorite)
        Log.d("TAGar", "showDetailRestaurant: $statusFavorite")
      }
    }
  }

  private fun setStatusFavorite(statusFavorite: Boolean) {
    if (statusFavorite) {
      binding.fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite_white))
    } else {
      binding.fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_not_favorite_white))
    }
  }
}