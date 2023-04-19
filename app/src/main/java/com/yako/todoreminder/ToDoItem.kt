package com.yako.todoreminder

import androidx.room.*
import java.util.Date


@Entity(tableName = "todo")
data class ToDoItem (
    @PrimaryKey(autoGenerate = true)val id: Long,
    @ColumnInfo(name = "todo_name") val name: String,
    @ColumnInfo(name = "todo_why") val why: String,
    @ColumnInfo(name = "todo_doDate") val doDate: Date?)
