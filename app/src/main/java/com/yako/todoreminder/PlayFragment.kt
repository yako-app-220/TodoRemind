package com.yako.todoreminder

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.room.Room
import com.yako.todoreminder.databinding.FragmentPlayBinding
import com.yako.todoreminder.databinding.FragmentStockBinding

class PlayFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    private val binding get() = _binding!!
    private var _binding: FragmentPlayBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlayBinding.inflate(inflater, container, false)
        return binding.root
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //roomのデータを全て取得
        //val todo: List<ToDoItem> = toDoDao.getAll()

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        //roomのインスタンス生成
        val db = Room.databaseBuilder(
            requireContext(),
            ToDoDatabase::class.java, "ToDo.db"
        ).allowMainThreadQueries().build()
        //Daoのインスタンス生成
        val toDoDao = db.todoDao()
        val id=(activity as? MainActivity)?.getToDoId()
        if(id!!>0){
            val todo = toDoDao.getToDo(id)
            binding.todoNameEdit.setText(todo.name)
            binding.whyEditText.setText(todo.why)
            binding.memoEditText.setText(todo.memo)
        }else{

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}