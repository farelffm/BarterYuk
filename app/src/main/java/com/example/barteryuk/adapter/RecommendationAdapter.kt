package com.example.barteryuk.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.barteryuk.databinding.ItemRecommendationBinding
import com.example.barteryuk.model.BarterItem

class RecommendationAdapter(
    private val items: List<BarterItem>,
    private val onItemClick: (BarterItem) -> Unit
) : RecyclerView.Adapter<RecommendationAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemRecommendationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: BarterItem) {
            binding.tvProductTitle.text = item.name
            // Placeholder untuk gambar bisa diatur di sini jika ada URL/Resource
            binding.btnInterested.setOnClickListener { onItemClick(item) }
            binding.root.setOnClickListener { onItemClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRecommendationBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}
