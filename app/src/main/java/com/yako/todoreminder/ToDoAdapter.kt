package com.yako.todoreminder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yako.todoreminder.databinding.CellTodoItemBinding
import com.yako.todoreminder.databinding.FragmentStockBinding

class ToDoAdapter (private val onClickListener: OnClickListener):
    ListAdapter<ToDoItem, ToDoViewHolder>(diffUtilItemCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        val view =  CellTodoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ToDoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        val todo = getItem(position)
        holder.bind(todo)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(todo)
        }
    }
}

class ToDoViewHolder(
    private val binding:CellTodoItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(todo: ToDoItem) {
        binding.todoName.text =todo.name
        binding.dateText.text =todo.doDate.toString()
    }
}

private val diffUtilItemCallback = object : DiffUtil.ItemCallback<ToDoItem>() {
    override fun areContentsTheSame(oldItem: ToDoItem, newItem: ToDoItem): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: ToDoItem, newItem: ToDoItem): Boolean {
        return oldItem.id == newItem.id
    }
}

class OnClickListener(val clickListener: (todo: ToDoItem) -> Unit) {
    fun onClick(todo: ToDoItem) = clickListener(todo)
}
