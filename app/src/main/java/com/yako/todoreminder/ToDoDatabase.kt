package com.yako.todoreminder

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(entities = [ToDoItem::class], version = 1)
@TypeConverters(DateConverters::class)
abstract class ToDoDatabase: RoomDatabase() {
    abstract fun todoDao(): ToDoDao

    companion object {
        private const val DATABASE_FILE = "ToDo.db"

        fun getInstance(context: Context): ToDoDatabase =
            Room.databaseBuilder(context,ToDoDatabase::class.java, DATABASE_FILE).build()
    }
}