package com.yako.todoreminder

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class DateDialog(private val onSelected:(String)->Unit)
    :DialogFragment(),DatePickerDialog.OnDateSetListener{

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val date = c.get(Calendar.DATE)


        return DatePickerDialog(requireActivity(),this,year,month,date)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        onSelected("$year/${month+1}/$dayOfMonth")
        //onSelected((activity as? ToDoEditActivity)?.startTimeDialog("$year/${month+1}/$dayOfMonth"))
    }
    }

class TimeDialog(private val onSelected:(String)->Unit)
    :DialogFragment(), TimePickerDialog.OnTimeSetListener{

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.YEAR)
        val minute = c.get(Calendar.MONTH)
        return TimePickerDialog(context,this,hour,minute,true)
    }



    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        onSelected("%1$02d:%2$02d".format(hourOfDay,minute))
    }
}