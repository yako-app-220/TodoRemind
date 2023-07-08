package com.yako.todoreminder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.yako.todoreminder.databinding.ActivityMakerBinding

class MakerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMakerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMakerBinding.inflate(layoutInflater).apply { setContentView(this.root) }

        val db = Room.databaseBuilder(
            applicationContext,
            MakerDatabase::class.java, "Maker.db"
        ).allowMainThreadQueries().build()

        val id: Long = intent.getLongExtra("makerId", 0L)
        val makerDao = db.makerDao()
        val maker = makerDao.getMaker(id)
        val toMainIntent = Intent(this, MainActivity::class.java)


        if (id == 0L) {
            binding.deleteButton.isInvisible = true
            binding.floatingActionButton.isInvisible=true
            binding.saveButton.setOnClickListener {
                val newId =
                    makerDao.insert(saveMaker(id))
                //setMakerAlarm(newId)
                //startActivity(toMainIntent)
                Log.i("MakerActivity",newId.toString())
                binding.floatingActionButton.isInvisible=false
            }
        } else {
            binding.makerNameEditText.setText(maker.makerName)
            binding.makerWhyEditText.setText(maker.makerWhy)
            binding.makerMemoEditText.setText(maker.makerMemo)
            binding.switchPower.isChecked=maker.power
            binding.saveButton.setOnClickListener {
                Log.i("ToDoEdit",id.toString())
            }
            binding.deleteButton.setOnClickListener {
                //todoDao.delete(todo)
                //startActivity(toMainIntent)
            }
        }

        binding.floatingActionButton.setOnClickListener {
            startChildrenActivity(0L)
        }

        val childDb = Room.databaseBuilder(
            applicationContext,
            ChildrenDatabase::class.java, "children.db"
        ).allowMainThreadQueries().build()
        val childDao = childDb.childrenDao()
        val child: List<Children> = childDao.getAllChildren()
        val childrenAdapter = ChildrenAdapter(
            ChildrenOnClickListener { child ->
                //toEditIntent.putExtra("ID",todo.id)
                val childId=child.childId
                startChildrenActivity(childId)
            }
        )
        childrenAdapter.submitList(child)
        binding.childrenList.adapter = childrenAdapter
        binding.childrenList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }



    private fun saveMaker(id: Long): Maker {
        return Maker(
            makerId = id,
            makerName = binding.makerNameEditText.text.toString(),
            makerWhy = binding.makerWhyEditText.text.toString(),
            makerMemo = binding.makerMemoEditText.text.toString(),
            power = binding.switchPower.isChecked())
    }

    private fun startChildrenActivity(makerId: Long) {
        val intent = Intent(applicationContext, ChildrenEditActivity::class.java)
        intent.putExtra("childrenId", makerId)
        this.startActivity(intent)
    }

}