package com.yako.todoreminder

import android.content.Context
import androidx.room.*
import java.time.LocalTime

@Entity(tableName = "maker")
data class Maker(
    @PrimaryKey(autoGenerate = true) val makerId: Long,
    val type:Long=0,
    val makerName:String="",
    val power: Boolean=true,
    //val makerTime: LocalTime?=null,
    val makerWhy:String="",
    val makerMemo:String="",
    )

//@Entity(tableName = "Factor")
//data class Factor (
//    @PrimaryKey(autoGenerate = true)val factorId: Long)

@Dao
interface MakerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: Maker): Long
    @Update fun update(entity: Maker)
    @Delete fun delete(entity: Maker)
    @Query("select * from maker")
    fun getAll(): List<Maker>
    @Query("select * from maker where makerId = :makerId")
    fun getMaker(makerId: Long): Maker
}

@Database(entities = [Maker::class], version = 1)
@TypeConverters(DateConverters::class)
abstract class MakerDatabase: RoomDatabase(){
    abstract fun makerDao(): MakerDao
    companion object {
        private const val DATABASE_FILE = "Maker.db"
        fun getInstance(context: Context): MakerDatabase =
            Room.databaseBuilder(context,MakerDatabase::class.java, DATABASE_FILE).build()
    }
}
