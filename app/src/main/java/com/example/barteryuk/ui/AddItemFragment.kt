package com.example.barteryuk.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.barteryuk.databinding.FragmentAddItemBinding
import com.example.barteryuk.model.BarterItem

/**
 * AddItemFragment menyediakan formulir bagi pengguna untuk menambahkan barang baru yang ingin dibarter.
 */
class AddItemFragment : Fragment() {

    private var _binding: FragmentAddItemBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Konfigurasi Spinner untuk pilihan kategori barang
        val categories = arrayOf("Elektronik", "Fashion", "Hobi", "Furniture", "Lainnya")
        val adapter = object : ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, categories) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val v = super.getView(position, convertView, parent)
                (v as? android.widget.TextView)?.setTextColor(android.graphics.Color.BLACK)
                return v
            }

            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                val v = super.getDropDownView(position, convertView, parent)
                (v as? android.widget.TextView)?.setTextColor(android.graphics.Color.BLACK)
                v.setBackgroundColor(android.graphics.Color.WHITE)
                return v
            }
        }
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCategory.adapter = adapter

        // Simulasi fitur upload gambar
        binding.btnUploadImage.setOnClickListener {
            Toast.makeText(requireContext(), "Fitur Upload Gambar (Dummy) Berhasil", Toast.LENGTH_SHORT).show()
            binding.ivItemImage.setImageResource(android.R.drawable.ic_menu_gallery)
        }

        // Logika penyimpanan barang baru
        binding.btnSave.setOnClickListener {
            val name = binding.etName.text.toString()
            val description = binding.etDescription.text.toString()
            val value = binding.etValue.text.toString()
            val category = binding.spinnerCategory.selectedItem.toString()
            
            // Mendapatkan teks dari RadioButton yang terpilih (Baru/Bekas)
            val selectedConditionId = binding.rgCondition.checkedRadioButtonId
            val condition = if (selectedConditionId != -1) {
                view.findViewById<RadioButton>(selectedConditionId).text.toString()
            } else {
                "Bekas"
            }

            // Validasi: memastikan field utama tidak kosong
            if (name.isNotEmpty() && description.isNotEmpty() && value.isNotEmpty()) {
                // Membuat objek BarterItem baru dengan data dari form
                val newItem = BarterItem(
                    name = name,
                    description = description,
                    estimatedValue = value,
                    category = category,
                    condition = condition,
                    ownerEmail = viewModel.currentUser.value?.email // Menandai pemilik barang
                )
                
                // Menambahkan ke list di ViewModel
                viewModel.addItem(newItem)
                Toast.makeText(requireContext(), "Barang berhasil ditambahkan", Toast.LENGTH_SHORT).show()
                
                // Kembali ke fragment sebelumnya (ProfileFragment)
                findNavController().navigateUp()
            } else {
                Toast.makeText(requireContext(), "Harap isi semua kolom", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
