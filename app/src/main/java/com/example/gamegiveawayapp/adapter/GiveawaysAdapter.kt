package com.example.gamegiveawayapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gamegiveawayapp.R
import com.example.gamegiveawayapp.databinding.GiveawayItemBinding
import com.example.gamegiveawayapp.model.Giveaways
import com.squareup.picasso.Picasso

class GiveawaysAdapter(
    private val giveaways: MutableList<Giveaways> = mutableListOf()
) : RecyclerView.Adapter<GiveawayViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GiveawayViewHolder {
        return GiveawayViewHolder(
            GiveawayItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: GiveawayViewHolder, position: Int) =
        holder.bind(giveaways[position])

    override fun getItemCount(): Int = giveaways.size

    fun setNewGiveaways(newGiveaways: List<Giveaways>) {
        giveaways.clear()
        giveaways.addAll(newGiveaways)
        notifyDataSetChanged()
    }

    fun getEventData(position: Int): Giveaways {
        val title: String = giveaways[position].title
        val cat: String = giveaways[position].category
        val date: String = giveaways[position].date

        return Giveaways(title, cat, date)
    }
}

class GiveawayViewHolder(
    private val binding: GiveawayItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(giveaways: Giveaways) {
        binding.title.text = giveaways.title
        binding.price.text = giveaways.worth
        binding.status.text = giveaways.status
        binding.date.text = giveaways.endDate
        binding.platform.text = giveaways.platforms

        Picasso.get()
            .load(giveaways.image)
            .into(binding.giveawayImage)
    }
}

interface OnItemClickListener {
    fun onItemClicked(position: Int)
}
