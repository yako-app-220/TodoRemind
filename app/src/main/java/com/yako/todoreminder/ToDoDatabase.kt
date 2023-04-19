package com.yako.todoreminder

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [ToDoItem::class], version = 1)
abstract class ToDoDatabase: RoomDatabase() {
    abstract fun todoDao(): ToDoDao

    companion object {
        private const val DATABASE_FILE = "ToDo.db"

        fun getInstance(context: Context): ToDoDatabase =
            Room.databaseBuilder(context,ToDoDatabase::class.java, DATABASE_FILE).build()
    }
}