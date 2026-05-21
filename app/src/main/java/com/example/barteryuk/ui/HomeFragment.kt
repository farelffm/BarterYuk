package com.example.barteryuk.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.barteryuk.adapter.BannerAdapter
import com.example.barteryuk.adapter.BarterAdapter
import com.example.barteryuk.adapter.RecommendationAdapter
import com.example.barteryuk.databinding.FragmentHomeBinding
import com.example.barteryuk.databinding.ItemCategoryBinding
import com.example.barteryuk.model.BarterItem

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBanner()
        setupCategories()
        setupRecommendations()
        setupAllItems()
    }

    private fun setupBanner() {
        val bannerImages = listOf(
            android.R.drawable.ic_dialog_info,
            android.R.drawable.ic_dialog_alert,
            android.R.drawable.ic_dialog_email
        )
        val bannerAdapter = BannerAdapter(bannerImages)
        binding.viewPagerBanner.adapter = bannerAdapter

        // Update dots (manual simple implementation for now)
        binding.viewPagerBanner.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateDots(position)
            }
        })
    }

    private fun updateDots(position: Int) {
        for (i in 0 until binding.layoutDots.childCount) {
            val dot = binding.layoutDots.getChildAt(i)
            if (i == position) {
                dot.setBackgroundResource(com.example.barteryuk.R.drawable.dot_active)
            } else {
                dot.setBackgroundResource(com.example.barteryuk.R.drawable.dot_inactive)
            }
        }
    }

    private fun setupCategories() {
        // Data Dummy untuk Kategori yang lebih lengkap
        val categories = listOf(
            Triple("Elektronik", android.R.drawable.ic_menu_camera, "#0D47A1"),
            Triple("Pakaian", android.R.drawable.ic_menu_view, "#0D47A1"),
            Triple("Kendaraan", android.R.drawable.ic_menu_directions, "#0D47A1"),
            Triple("Hobi", android.R.drawable.ic_menu_compass, "#0D47A1"),
            Triple("Olahraga", android.R.drawable.ic_menu_mylocation, "#0D47A1"),
            Triple("Musik", android.R.drawable.ic_lock_silent_mode_off, "#0D47A1"),
            Triple("Makanan", android.R.drawable.ic_menu_set_as, "#0D47A1"),
            Triple("Buku", android.R.drawable.ic_menu_edit, "#0D47A1"),
            Triple("Jasa", android.R.drawable.ic_menu_manage, "#0D47A1"),
            Triple("Lainnya", android.R.drawable.ic_menu_more, "#0D47A1")
        )

        binding.layoutCategories.removeAllViews()
        for (category in categories) {
            val catBinding = ItemCategoryBinding.inflate(layoutInflater, binding.layoutCategories, false)
            catBinding.tvCategoryName.text = category.first
            catBinding.ivCategoryIcon.setImageResource(category.second)
            
            // Set click listener (optional: filter logic later)
            catBinding.root.setOnClickListener {
                // Implement filter logic
            }

            binding.layoutCategories.addView(catBinding.root)
        }
    }

    private fun setupRecommendations() {
        viewModel.barterItems.observe(viewLifecycleOwner) { items ->
            val adapter = RecommendationAdapter(items.take(5)) { item: BarterItem ->
                val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(item, false)
                findNavController().navigate(action)
            }
            binding.rvRecommendations.adapter = adapter
        }
    }

    private fun setupAllItems() {
        viewModel.barterItems.observe(viewLifecycleOwner) { items ->
            val adapter = BarterAdapter(items) { item ->
                val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(item, false)
                findNavController().navigate(action)
            }
            binding.rvAllItems.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
