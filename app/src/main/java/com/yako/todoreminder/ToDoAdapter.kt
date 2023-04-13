package com.yako.todoreminder

import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter

class ToDoAdapter (data:OrderedRealmCollection<ToDoItem>):
    RealmRecyclerViewAdapter<ToDoItem, ToDoAdapter.ViewHolder>(data,true) {


    init {
        setHasStableIds(true)
    }
    class ViewHolder(cell: View) : RecyclerView.ViewHolder(cell) {
        val date : TextView=cell.findViewById(R.id.todoName)
        val title: TextView = cell.findViewById(R.id.dateText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoAdapter.ViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.cell_todo_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val toDoItem:ToDoItem?=getItem(position)
        holder.date.text=DateFormat.format("yyyy/MM/dd HH:mm",toDoItem?.playDate)
        holder.title.text=toDoItem?.name
    }

    override fun getItemId(position:Int):Long{
        return getItem(position)?.id ?:0
    }


}