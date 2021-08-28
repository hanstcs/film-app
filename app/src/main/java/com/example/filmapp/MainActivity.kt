package com.example.filmapp

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmapp.base.BaseAdapter
import com.example.filmapp.databinding.ActivityMainBinding
import com.example.filmapp.repository.MovieModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var bindingInst: ActivityMainBinding? = null
    private val binding get() = bindingInst!!

    private val viewModel: MovieListViewModel by viewModels()

    private lateinit var moviesAdapter: BaseAdapter<MovieModel, MovieViewHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingInst = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fetchData()
        observeData()
        setupAdapter()
    }

    private fun fetchData() {
        viewModel.loadMovies()
    }

    private fun setupAdapter() {
        moviesAdapter = BaseAdapter(
            { parent, _ -> MovieViewHolder.inflate(parent) },
            { viewHolder, _, data ->
                viewHolder.bind(data)
                viewHolder.setActionListener {
                    Toast.makeText(baseContext, data.title ?: "Movie", Toast.LENGTH_SHORT).show()
                }
            }
        )
        binding.rvMovies.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            adapter = moviesAdapter
        }
    }

    private fun observeData() {
        viewModel.state.observe(this,
            { viewState ->
                when (viewState) {
                    is MovieListViewState.ShowLoading -> {

                    }
                    is MovieListViewState.Failed -> {

                    }
                    is MovieListViewState.ShowMovies -> {
                        moviesAdapter.addClearItems(viewState.movies)
                    }
                }
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        bindingInst = null
    }
}
