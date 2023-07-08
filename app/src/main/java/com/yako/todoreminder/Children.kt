package com.yako.todoreminder

import android.content.Context
import androidx.room.*
import java.time.LocalTime

@Entity(tableName = "children")
data class Children (
    @PrimaryKey(autoGenerate = true)val childId: Long,
    val makerId:Long,
    val childName:String="",
    val childWhy:String="",
    val childMemo:String="",
    val childDoDay:Long?,
    val childDoTime:LocalTime?,
    val childLimitDay:Long?,
    val childLimitTime:LocalTime?,
    val childDeleteDay:Long?,
    val childDeleteTime:LocalTime?,
)

@Dao
interface ChildrenDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: Children): Long
    @Update fun update(entity: Children)
    @Delete fun delete(entity: Children)
    @Query("select * from children")
    fun getAllChildren(): List<Children>
    @Query("select * from children where childId = :childId")
    fun getMaker(childId: Long): Children
}

@Database(entities = [Children::class], version = 1)
@TypeConverters(TimeConverters::class)
abstract class ChildrenDatabase: RoomDatabase() {
    abstract fun childrenDao(): ChildrenDao
    companion object {
        private const val DATABASE_FILE = "Children.db"
        fun getInstance(context: Context): ChildrenDatabase =
            Room.databaseBuilder(context,ChildrenDatabase::class.java, DATABASE_FILE).build()
    }
}