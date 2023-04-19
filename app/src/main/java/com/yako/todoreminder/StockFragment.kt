package com.yako.todoreminder

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.yako.todoreminder.databinding.FragmentStockBinding



class StockFragment : Fragment() {

    private val binding get() = _binding!!
    private var _binding: FragmentStockBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentStockBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //roomのインスタンス生成
        val db = Room.databaseBuilder(
            requireContext(),
            ToDoDatabase::class.java, "ToDo.db"
        ).allowMainThreadQueries().build()

        //Daoのインスタンス生成
        val toDoDao = db.todoDao()
        //roomのデータを全て取得
        val todo: List<ToDoItem> = toDoDao.getAll()

        var todoId=-1L
        //OnClickListenerを引数としてMemoAdapterのインスタンス生成
        val todoAdapter = ToDoAdapter(
            OnClickListener { todo ->
                //toEditIntent.putExtra("ID",todo.id)
                todoId=todo.id
            }
        )

        todoAdapter.submitList(todo)
        binding.list.adapter = todoAdapter
        binding.list.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        binding.list.setOnClickListener {
            (activity as? MainActivity)?.startToDoActivity(todoId)
        }

        binding.addFab.setOnClickListener {
            (activity as? MainActivity)?.startToDoActivity(0L)
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

}