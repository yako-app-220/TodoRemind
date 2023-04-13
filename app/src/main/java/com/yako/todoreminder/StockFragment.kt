package com.yako.todoreminder

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.yako.todoreminder.databinding.FragmentStockBinding
import io.realm.Realm
import io.realm.kotlin.where


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


class StockFragment : Fragment() {

    //realm
    private lateinit var realm: Realm


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //realm
        realm= Realm.getDefaultInstance()
    }

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

        binding.list.layoutManager=LinearLayoutManager(context)
        val toDoItem=realm.where<ToDoItem>().findAll()
        val adapter=ToDoAdapter(toDoItem)
        binding.list.adapter=adapter

        binding.addFab.setOnClickListener {
            (activity as? MainActivity)?.startToDoActivity(0L)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    override fun onDestroy() {
        super.onDestroy()
        //realm
        realm.close()
    }


}