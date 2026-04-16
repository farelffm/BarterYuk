package com.example.barteryuk.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.barteryuk.R
import com.example.barteryuk.adapter.BarterAdapter
import com.example.barteryuk.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()

    /**
     * Menghubungkan layout fragment_home.xml dengan Fragment ini menggunakan ViewBinding.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Inisialisasi komponen UI, adapter RecyclerView, dan observasi data dari ViewModel.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inisialisasi Adapter untuk bagian Rekomendasi (Scroll Horizontal)
        // Fungsi lambda { item -> ... } menangani aksi klik untuk berpindah ke DetailFragment
        val recommendationAdapter = BarterAdapter(ArrayList(), showOwner = true) { item ->
            val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(item, false)
            findNavController().navigate(action)
        }
        binding.rvRecommendations.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvRecommendations.adapter = recommendationAdapter

        // Inisialisasi Adapter untuk bagian Semua Barang (Tampilan Grid 2 Kolom)
        val allItemsAdapter = BarterAdapter(ArrayList(), showOwner = true) { item ->
            val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(item, false)
            findNavController().navigate(action)
        }
        binding.rvAllItems.layoutManager = androidx.recyclerview.widget.GridLayoutManager(context, 2)
        binding.rvAllItems.adapter = allItemsAdapter

        // Mengamati perubahan data list barang dari ViewModel secara real-time
        viewModel.barterItems.observe(viewLifecycleOwner) { items ->
            val recItems = items.take(3) // Ambil 3 barang pertama untuk rekomendasi
            recommendationAdapter.updateData(recItems)
            allItemsAdapter.updateData(items)
            setupDotIndicator(recItems.size) // Siapkan indikator titik sesuai jumlah rekomendasi
        }

        // Listener untuk mendeteksi scroll pada bagian rekomendasi guna memperbarui posisi titik indikator
        binding.rvRecommendations.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val position = layoutManager.findFirstVisibleItemPosition()
                if (position != RecyclerView.NO_POSITION) {
                    updateDots(position) // Perbarui status titik yang aktif
                }
            }
        })
    }

    /**
     * Membuat indikator titik (dot) secara dinamis sesuai dengan jumlah item rekomendasi.
     * @param size Jumlah item yang ada di bagian rekomendasi.
     */
    private fun setupDotIndicator(size: Int) {
        binding.dotIndicator.removeAllViews()
        if (size <= 0) return
        
        for (i in 0 until size) {
            val dot = ImageView(requireContext())
            // Gunakan drawable lokal untuk indikator titik
            dot.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.dot_inactive))
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(8, 0, 8, 0)
            binding.dotIndicator.addView(dot, params)
        }
        updateDots(0) // Set titik pertama sebagai posisi aktif di awal
    }

    /**
     * Memperbarui tampilan visual titik indikator berdasarkan posisi item yang sedang dilihat.
     * @param position Indeks item yang saat ini berada di posisi paling kiri/depan.
     */
    private fun updateDots(position: Int) {
        val childCount = binding.dotIndicator.childCount
        for (i in 0 until childCount) {
            val imageView = binding.dotIndicator.getChildAt(i) as ImageView
            if (i == position) {
                // Titik aktif (berwarna/terisi)
                imageView.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.dot_active))
            } else {
                // Titik tidak aktif (transparan/kosong)
                imageView.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.dot_inactive))
            }
        }
    }

    /**
     * Membersihkan binding saat fragment dihancurkan untuk mencegah memory leak.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
