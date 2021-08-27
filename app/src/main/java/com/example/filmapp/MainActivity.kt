package com.example.filmapp

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.filmapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var bindingInst: ActivityMainBinding? = null
    private val binding get() = bindingInst!!

    private val viewModel: MovieListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingInst = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnFetch.setOnClickListener { fetchData() }
    }

    private fun fetchData() {
        viewModel.loadMovies()
    }

    override fun onDestroy() {
        super.onDestroy()
        bindingInst = null
    }
}