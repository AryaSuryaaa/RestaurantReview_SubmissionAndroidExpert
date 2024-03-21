package com.aryasurya.restaurantreview.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.aryasurya.restaurantreview.core.ui.RestaurantAdapter
import com.aryasurya.restaurantreview.databinding.FragmentFavoriteBinding
import com.aryasurya.restaurantreview.detail.DetailRestaurantActivity
import com.aryasurya.restaurantreview.di.FavoriteModuleDependencies
import com.google.gson.Gson
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class FavoriteFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelFactory

    private val favoriteViewModel: FavoriteViewModel by viewModels {
        factory
    }

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        DaggerFavoriteComponent.builder()
            .context(requireContext())
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    requireContext(),
                    FavoriteModuleDependencies::class.java
                )
            )
            .build()
            .inject(this)
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {

            val restaurantAdapter = RestaurantAdapter()
            restaurantAdapter.onItemClick = { selectedData ->
//                val intent = Intent(activity, DetailRestaurantActivity::class.java)
//                intent.putExtra(DetailRestaurantActivity.EXTRA_DATA, selectedData)
//                startActivity(intent)
                val intent = Intent(activity, DetailRestaurantActivity::class.java)
                intent.putExtra(DetailRestaurantActivity.EXTRA_DATA, Gson().toJson(selectedData))
                startActivity(intent)
            }

            favoriteViewModel.favoriteRestaurant.observe(viewLifecycleOwner) { dataRestaurant ->
                restaurantAdapter.setData(dataRestaurant)
                binding.viewEmpty.root.visibility =
                    if (dataRestaurant.isNotEmpty()) View.GONE else View.VISIBLE
            }

            with(binding.rvTourism) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = restaurantAdapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
