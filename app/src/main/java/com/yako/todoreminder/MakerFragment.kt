package com.yako.todoreminder

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yako.todoreminder.databinding.FragmentMakerBinding
import com.yako.todoreminder.databinding.FragmentPlayBinding
import com.yako.todoreminder.databinding.FragmentStockBinding

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
    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }


}