package com.example.filmapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.filmapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var bindingInst: ActivityMainBinding? = null
    private val binding get() = bindingInst!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingInst = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        bindingInst = null
    }
}