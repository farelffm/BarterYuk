package com.example.barteryuk.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.barteryuk.databinding.FragmentDetailBinding

/**
 * DetailFragment menampilkan rincian lengkap dari sebuah barang yang dipilih.
 */
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    // Mengambil argumen yang dikirim melalui SafeArgs
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Data barang dan status kepemilikan dari argumen navigasi
        val item = args.item
        val isMyItem = args.isMyItem

        // Mengisi data ke dalam komponen UI
        binding.tvDetailName.text = item.name
        binding.tvDetailCategory.text = "Kategori: ${item.category}"
        binding.tvDetailValue.text = "Rp ${item.estimatedValue}"
        binding.tvDetailCondition.text = "Kondisi: ${item.condition}"
        binding.tvDetailDescription.text = item.description

        // Tombol kembali ke halaman sebelumnya
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        // Logika perubahan teks tombol berdasarkan kepemilikan barang
        if (isMyItem) {
            // Jika barang milik sendiri, muncul tombol Edit
            binding.btnBarter.text = "Edit Barang"
            binding.btnBarter.icon = null // Sembunyikan icon swap jika tombol Edit
            binding.btnBarter.setOnClickListener {
                Toast.makeText(requireContext(), "Fitur Edit (Dummy) untuk ${item.name}", Toast.LENGTH_SHORT).show()
            }
        } else {
            // Jika barang milik orang lain, muncul tombol Ajukan Barter
            binding.btnBarter.text = "Ajukan Barter"
            binding.btnBarter.setOnClickListener {
                Toast.makeText(requireContext(), "Permintaan barter untuk ${item.name} telah diajukan!", Toast.LENGTH_LONG).show()
                binding.btnBarter.isEnabled = false
                binding.btnBarter.text = "Permintaan Terkirim"
                binding.btnBarter.icon = null
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
