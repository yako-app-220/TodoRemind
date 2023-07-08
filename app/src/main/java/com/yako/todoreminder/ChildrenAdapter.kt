package com.yako.todoreminder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yako.todoreminder.databinding.CellChildrenListBinding
import com.yako.todoreminder.databinding.CellMakerListBinding


class ChildrenAdapter(private val onClickListener: ChildrenOnClickListener):
    ListAdapter<Children, ChildrenViewHolder>(diffUtilItemCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildrenViewHolder {
        val view =  CellChildrenListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChildrenViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChildrenViewHolder, position: Int) {
        val children = getItem(position)
        holder.bind(children)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(children)
        }
    }
}
class ChildrenViewHolder(
    private val binding: CellChildrenListBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(children: Children) {
        binding.childName.text =children.childName
        binding.childDoText.text =children.childDoDay.toString()
    }
}
private val diffUtilItemCallback = object : DiffUtil.ItemCallback<Children>() {
    override fun areContentsTheSame(oldItem: Children, newItem: Children): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: Children, newItem: Children): Boolean {
        return oldItem.childId == newItem.childId
    }
}

class ChildrenOnClickListener(val clickListener: (children: Children) -> Unit) {
    fun onClick(children: Children) = clickListener(children)
}