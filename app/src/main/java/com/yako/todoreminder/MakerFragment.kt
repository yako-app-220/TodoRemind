package com.yako.todoreminder

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.yako.todoreminder.databinding.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


/**
 * A simple [Fragment] subclass.
 * Use the [MakerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MakerFragment : Fragment() {

    private val binding get() = _binding!!
    private var _binding: FragmentMakerBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMakerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //roomのインスタンス生成
        val db = Room.databaseBuilder(
            requireContext(),
            MakerDatabase::class.java, "Maker.db"
        ).allowMainThreadQueries().build()
        //Daoのインスタンス生成
        val makerDao = db.makerDao()
        //roomのデータを全て取得
        val maker: List<Maker> = makerDao.getAll()
        //OnClickListenerを引数としてMemoAdapterのインスタンス生成

        Log.i("MakerFragment",maker.toString())
        val makerAdapter = MakerAdapter(
            MakerOnClickListener { maker ->
                //toEditIntent.putExtra("ID",todo.id)
                val makerId=maker.makerId
                (activity as? MainActivity)?.startMakerActivity(makerId)
            }
        )
        makerAdapter.submitList(maker)
        binding.makerList.adapter = makerAdapter
        binding.makerList.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.makerList.setOnClickListener {
            //(activity as? MainActivity)?.startMakerActivity()
        }
        binding.addMakerButton.setOnClickListener {
            (activity as? MainActivity)?.startMakerActivity(0L)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}