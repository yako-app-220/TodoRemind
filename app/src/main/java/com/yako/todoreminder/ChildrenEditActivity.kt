package com.yako.todoreminder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.core.view.isInvisible
import androidx.fragment.app.DialogFragment
import androidx.room.Room
import com.yako.todoreminder.databinding.ActivityChildrenEditBinding
import com.yako.todoreminder.databinding.ActivityToDoBinding
import java.text.ParseException
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class ChildrenEditActivity : AppCompatActivity(),NumberPickerDialog.NoticeDialogListener{

    private lateinit var binding: ActivityChildrenEditBinding
    var selectView: TextView?=null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityChildrenEditBinding.inflate(layoutInflater).apply { setContentView(this.root) }
        val db = Room.databaseBuilder(
            applicationContext,
            ChildrenDatabase::class.java, "children.db"
        ).allowMainThreadQueries().build()

        val childId: Long = intent.getLongExtra("childrenId", 0L)
        val makerId: Long = intent.getLongExtra("makerId", 0L)
        val childDao = db.childrenDao()
        val children = childDao.getMaker(childId)

        if (childId== 0L) {
            binding.deleteButton.isInvisible = true
            binding.saveButton.setOnClickListener {
                val newChildId =childDao.insert(saveChildren(childId,makerId))
                //setAlarm(newId)
                //startActivity(toMainIntent)
            }
        } else {
            binding.nameEdit.setText(children.childName)
            binding.whyEdit.setText(children.childWhy)
            binding.memoEdit.setText(children.childMemo)
            binding.textDoDate.setText(children.childDoDay?.toString() ?: "")
            binding.textDoTime.setText(children.childDoTime?.toString() ?: "")
            binding.textLimitDate.setText(children.childLimitDay?.toString() ?: "")
            binding.textLimitTime.setText(children.childLimitTime?.toString() ?: "")
            binding.textDeleteDate.setText(children.childDeleteDay?.toString() ?: "")
            binding.textDeleteTime.setText(children.childDeleteTime?.toString() ?: "")

            binding.saveButton.setOnClickListener {
                // setAlarm(id)
                childDao.update(saveChildren(childId,makerId))
                //startActivity(toMainIntent)
                //Log.i("",childId.toString())
            }

            binding.deleteButton.setOnClickListener {
                childDao.delete(children)
                //startActivity(toMainIntent)
            }
        }

        binding.textDoTime.setOnClickListener {childrenTimeInput(binding.textDoTime)}
        binding.textDeleteTime.setOnClickListener {childrenTimeInput(binding.textDeleteTime)}
        binding.textLimitTime.setOnClickListener {childrenTimeInput(binding.textLimitTime)}

        binding.textDoDate.setOnClickListener{childrenDayInput(binding.textDoDate)}
        binding.textDeleteDate.setOnClickListener{ childrenDayInput(binding.textDeleteDate)}
        binding.textLimitDate.setOnClickListener{ childrenDayInput(binding.textLimitDate)}
    }

    fun childrenTimeInput(textView: TextView){
        TimeDialog{time->
            textView.text=time
        }.show(supportFragmentManager,"time_dialog")
    }

    fun childrenDayInput(textView: TextView){
        selectView=textView
        val dialog = NumberPickerDialog()
        dialog.show(supportFragmentManager, "NumberPickerDialog")
    }


    private fun saveChildren(childId: Long,makerId:Long): Children {
        return Children(
            childId = childId,
            makerId = makerId,
            childName = binding.nameEdit.text.toString(),
            childWhy = binding.whyEdit.text.toString(),
            childMemo = binding.memoEdit.text.toString(),
            childDoDay = binding.textDoDate.text.toString().toDateAfter(),
            childDoTime = binding.textDoTime.text.toString().toLocalTime(),
            childLimitDay = binding.textLimitDate.text.toString().toDateAfter(),
            childLimitTime = binding.textLimitTime.text.toString().toLocalTime(),
            childDeleteDay = binding.textDeleteDate.text.toString().toDateAfter(),
            childDeleteTime = binding.textDeleteTime.text.toString().toLocalTime(),
        )
    }

    private fun String.toDateAfter(): Long?{

        if (this==""){
            return null
        }else{
            return this.toLong()
        }
    }

    private fun String.toLocalTime(): LocalTime? {
        // フォーマットを指定する
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        return try {
            LocalTime.parse(this, formatter)
        } catch (e: DateTimeParseException) {
            null
        }
    }

    override fun onNumberPickerDialogPositiveClick(
        dialog: DialogFragment,
        selectedItem: Int
    ){
        selectView!!.text = selectedItem.toString()
    }
    override fun onNumberPickerDialogNegativeClick(dialog: DialogFragment) {
        return
    }
}