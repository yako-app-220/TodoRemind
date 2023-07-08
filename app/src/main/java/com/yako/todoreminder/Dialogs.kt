package com.yako.todoreminder

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.widget.DatePicker
import android.widget.NumberPicker
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class DateDialog(private val onSelected:(String)->Unit)
    : DialogFragment(),DatePickerDialog.OnDateSetListener{

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

class NumberPickerDialog: DialogFragment(), NumberPicker.OnValueChangeListener {

    private lateinit var listener: NoticeDialogListener // 親に渡すためのリスナー定義
    private var selectedItem: Int = 0 // 選択したアイテム格納

    interface NoticeDialogListener {
        fun onNumberPickerDialogPositiveClick(dialog: DialogFragment, selectedItem: Int)
        fun onNumberPickerDialogNegativeClick(dialog: DialogFragment)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            this.listener = context as NoticeDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException(("$context must implement NoticeDialogListener"))
        }
    }

    /**
     * ダイアログ作成
     **/
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val inflater = requireActivity().layoutInflater
        val dialogView = inflater.inflate(R.layout.number_picker_dialog, null)!!
        val builder = AlertDialog.Builder(context)

        // Dialogの設定
        builder.setView(dialogView)
        builder.setTitle("NumberPickerDialog")
        builder.setPositiveButton("OK") { _, _ ->
            this.listener.onNumberPickerDialogPositiveClick(this, this.selectedItem) //
        }
        builder.setNegativeButton("CANCEL") { _, _ ->
            this.listener.onNumberPickerDialogNegativeClick(this)
        }

        // NumberPickerの設定
        val np = dialogView.findViewById<NumberPicker>(R.id.numberPicker)
        np.setOnValueChangedListener(this)
        np.minValue = 0  // NumberPickerの最小値設定
        np.maxValue = 10 // NumberPickerの最大値設定
        np.value = 0    // NumberPickerの初期値

        return builder.create()
    }

    override fun onValueChange(picker: NumberPicker?, oldVal: Int, newVal: Int) {
        this.selectedItem = newVal
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onDetach() {
        super.onDetach()
    }

}
