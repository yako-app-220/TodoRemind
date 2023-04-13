package com.yako.todoreminder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yako.todoreminder.databinding.ActivityMainBinding
import com.yako.todoreminder.databinding.ActivityToDoBinding
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class ToDoActivity : AppCompatActivity() {


    //realm
    private lateinit var realm: Realm
    private lateinit var binding:ActivityToDoBinding

    private var targetId:Long=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityToDoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //realm
        realm= Realm.getDefaultInstance()
        targetId =intent.getLongExtra("todoId",0L)

        binding.saveButton.setOnClickListener {
            saveToDoItem(targetId)
        }

    }

    private fun saveToDoItem(id:Long){
        //インスタンス必要？
        if (id==0L){
            realm.executeTransaction { db:Realm->
                val maxId=db.where<ToDoItem>().max("id")
                val nextId=(maxId?.toLong() ?: 0L)+1L
                val toDo =db.createObject<ToDoItem>(nextId)


                val doDate=binding.textDoTime.text.toString().toDate()
                if (doDate!=null){
                    toDo.doType=1L
                    toDo.doDate=doDate
                }
                val limitDate=binding.textLimitTime.text.toString().toDate()
                if (doDate!=null){
                    toDo.limitType=1L
                    toDo.limitDate=limitDate
                }
                val deleteDate=binding.textDeleteTime.text.toString().toDate()
                if (doDate!=null){
                    toDo.deleteType=1L
                    toDo.deleteDate=deleteDate
                }
                toDo.name=binding.nameEdit.text.toString()
                toDo.why=binding.whyEdit.text.toString()
                toDo.memo=binding.memoEdit.text.toString()
            }
        }
    }



    override fun onDestroy() {
        super.onDestroy()
        //realm
        realm.close()
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