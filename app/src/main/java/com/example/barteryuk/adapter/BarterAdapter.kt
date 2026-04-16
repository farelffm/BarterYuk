package com.example.barteryuk.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.barteryuk.databinding.ItemBarterBinding
import com.example.barteryuk.model.BarterItem

/**
 * BarterAdapter adalah pengelola tampilan (Adapter) untuk menampilkan daftar BarterItem dalam RecyclerView.
 * @param listBarter Sumber data berupa list barang.
 * @param showOwner Menentukan apakah informasi pemilik barang ditampilkan atau tidak.
 * @param onItemClick Callback fungsi yang dipicu saat sebuah item diklik.
 */
class BarterAdapter(
    private val listBarter: ArrayList<BarterItem>,
    private val showOwner: Boolean = true,
    private val onItemClick: (BarterItem) -> Unit
) : RecyclerView.Adapter<BarterAdapter.BarterViewHolder>() {

    /**
     * ViewHolder yang memegang referensi ke view untuk setiap baris/item dalam list.
     */
    inner class BarterViewHolder(private val binding: ItemBarterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        
        /**
         * Mengikat data BarterItem ke komponen UI di dalam layout item_barter.xml.
         */
        fun bind(barterItem: BarterItem) {
            with(binding) {
                // Menampilkan informasi dasar barang
                tvItemName.text = barterItem.name
                tvItemCategory.text = barterItem.category
                tvItemValue.text = "Rp ${barterItem.estimatedValue}"
                tvItemCondition.text = barterItem.condition
                
                // Menampilkan atau menyembunyikan identitas pemilik berdasarkan parameter showOwner
                if (showOwner) {
                    tvItemOwner.visibility = android.view.View.VISIBLE
                    // Mengambil username dari email (teks sebelum karakter '@')
                    tvItemOwner.text = "Oleh: ${barterItem.ownerEmail?.split("@")?.get(0) ?: "Anonim"}"
                } else {
                    tvItemOwner.visibility = android.view.View.GONE
                }
                
                // Mengatur aksi klik pada seluruh area item (CardView)
                root.setOnClickListener {
                    onItemClick(barterItem)
                }
            }
        }
    }

    /**
     * Membuat instance ViewHolder baru dengan meng-inflate layout item_barter.xml.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BarterViewHolder {
        val binding = ItemBarterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BarterViewHolder(binding)
    }

    /**
     * Menghubungkan data spesifik dari list ke ViewHolder berdasarkan posisinya.
     */
    override fun onBindViewHolder(holder: BarterViewHolder, position: Int) {
        holder.bind(listBarter[position])
    }

    /**
     * Mengembalikan jumlah total item dalam list.
     */
    override fun getItemCount(): Int = listBarter.size

    /**
     * Memperbarui seluruh data dalam adapter dan memberitahu RecyclerView untuk menggambar ulang.
     * @param newList Daftar data barang yang baru.
     */
    fun updateData(newList: List<BarterItem>) {
        listBarter.clear()
        listBarter.addAll(newList)
        notifyDataSetChanged() // Memicu pembaruan tampilan secara keseluruhan
    }
}
