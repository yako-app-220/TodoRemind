package com.yako.todoreminder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateFormat
import android.widget.TextView
import androidx.core.view.isInvisible
import androidx.room.Room
import com.yako.todoreminder.databinding.ActivityToDoBinding
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class ToDoEditActivity : AppCompatActivity() {

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

        binding.doTimeSelectButton.setOnClickListener{calenderTextInput(binding.textDoTime)}
        binding.limitTimeSelectButton.setOnClickListener{calenderTextInput(binding.textLimitTime)}
        binding.deleteTimeSelectButton.setOnClickListener{calenderTextInput(binding.textDeleteTime)}

        if (id == 0L) {
            binding.deleteButton.isInvisible = true
            binding.saveButton.setOnClickListener {

                todoDao.insert(saveTodo(id))
                startActivity(toMainIntent)
            }

        } else {

            binding.nameEdit.setText(todo.name)
            binding.whyEdit.setText(todo.why)
            binding.textDoTime.text= changeMyDateText(todo.doDate)
            binding.textLimitTime.text=changeMyDateText(todo.limitDate)
            binding.textDeleteTime.text= changeMyDateText(todo.deleteDate)
            //binding.textLimitTime.text= DateFormat.format("yyyy/MM/dd HH:mm",todo.limitDate)
            //binding.textDeleteTime.text= DateFormat.format("yyyy/MM/dd HH:mm",todo.deleteDate)

            binding.saveButton.setOnClickListener {
                todoDao.update(saveTodo(id))
                startActivity(toMainIntent)
            }

            binding.deleteButton.setOnClickListener {
                todoDao.delete(todo)
                startActivity(toMainIntent)
            }
        }


    }


    private fun saveTodo(id: Long): ToDoItem {
        return ToDoItem(
            id,
            name = binding.nameEdit.text.toString(),
            why = binding.whyEdit.text.toString(),
            doDate = binding.textDoTime.text.toString().toDate(),
            limitDate = binding.textLimitTime.text.toString().toDate(),
            deleteDate = binding.textDeleteTime.text.toString().toDate()
        )
    }



    private fun calenderTextInput(targetText:TextView){
        var calenderText=""

        TimeDialog{time->
            if (calenderText=="") {
                val current = LocalDateTime.now()
                val formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")
                calenderText = current.format(formatter)
            }
            calenderText += " $time"
            targetText.text=calenderText
        }.show(supportFragmentManager,"time_dialog")

        DateDialog{date->
            if (date!="") {
                calenderText = date
            }
        }.show(supportFragmentManager,"date_dialog")

    }

    private fun changeMyDateText(date: Date?): String{
        if (date!=null){
            return DateFormat.format("yyyy/MM/dd HH:mm",date).toString()
        }else{
            return "なし"
        }
        //var temp="なし"
        //temp= date?.let {  } as String
        //return temp
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