package com.yako.todoreminder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isInvisible
import androidx.room.Room
import com.yako.todoreminder.databinding.ActivityToDoBinding
import io.realm.Realm
import io.realm.kotlin.where
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class ToDoActivity : AppCompatActivity() {

    private lateinit var binding:ActivityToDoBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityToDoBinding.inflate(layoutInflater).apply { setContentView(this.root) }

        val db = Room.databaseBuilder(
            applicationContext,
            ToDoDatabase::class.java, "ToDo.db"
        ).allowMainThreadQueries().build()

        val id: Long = intent.getLongExtra("todoId", 0L)
        val todoDao = db.todoDao()
        val todo = todoDao.getToDo(id)
        val toMainIntent = Intent(this, MainActivity::class.java)

        if (id == 0L) {
            binding.deleteButton.isInvisible = true
            binding.saveButton.setOnClickListener {
                val date="2023/04/18 08:08"
                val todo = ToDoItem(
                    id,
                    binding.nameEdit.text.toString(),
                    binding.whyEdit.text.toString(),
                    null
                )
                todoDao.insert(todo)
                startActivity(toMainIntent)
            }
        } else {

        }

    }


    private fun String.toDate(patten:String="yyyy/MM/dd HH:mm"): Date?{
        return try{
            SimpleDateFormat(patten).parse(this)
        }catch(e:IllegalAccessException){
            return null
        }catch(e:ParseException){
            return null
        }
    }
}