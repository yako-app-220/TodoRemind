package com.yako.todoreminder

import androidx.room.*

@Dao
interface ToDoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: ToDoItem): Long
    @Update fun update(entity: ToDoItem)
    @Delete fun delete(entity: ToDoItem)
    @Query("select * from todo")
    fun getAll(): List<ToDoItem>
    @Query("select * from todo where id = :id")
    fun getToDo(id: Long): ToDoItem
}



