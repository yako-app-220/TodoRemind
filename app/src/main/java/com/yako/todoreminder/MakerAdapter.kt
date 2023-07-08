package com.yako.todoreminder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yako.todoreminder.databinding.CellMakerListBinding

class MakerAdapter(private val onClickListener: MakerOnClickListener):
    ListAdapter<Maker, MakerViewHolder>(diffUtilItemCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MakerViewHolder {
        val view =  CellMakerListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MakerViewHolder(view)
    }

    override fun onBindViewHolder(holder: MakerViewHolder, position: Int) {
        val maker = getItem(position)
        holder.bind(maker)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(maker)
        }
    }
}
class MakerViewHolder(
    private val binding: CellMakerListBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(maker: Maker) {
        binding.makerName.text =maker.makerName
        binding.typeText.text =maker.type.toString()
    }
}
private val diffUtilItemCallback = object : DiffUtil.ItemCallback<Maker>() {
    override fun areContentsTheSame(oldItem: Maker, newItem: Maker): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: Maker, newItem: Maker): Boolean {
        return oldItem.makerId == newItem.makerId
    }
}

class MakerOnClickListener(val clickListener: (maker: Maker) -> Unit) {
    fun onClick(maker: Maker) = clickListener(maker)
}