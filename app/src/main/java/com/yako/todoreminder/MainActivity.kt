package com.yako.todoreminder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.yako.todoreminder.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_host_fragment)
        setupWithNavController(binding.bottomNavigation,navController)
    }

    fun startToDoActivity(id:Long){
        val intent = Intent(this, ToDoActivity::class.java)
        intent.putExtra("todoId",id)
        this.startActivity(intent)
    }
}