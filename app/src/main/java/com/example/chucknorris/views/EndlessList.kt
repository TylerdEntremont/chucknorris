package com.example.chucknorris.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chucknorris.adapter.EndlessRecyclerViewScrollListener
import com.example.chucknorris.adapter.JokeAdapter
import com.example.chucknorris.databinding.FragmentEndlessListBinding
import com.example.chucknorris.viewModel.JokeViewModel
import com.example.chucknorris.viewModel.ResultState
import org.koin.androidx.viewmodel.ext.android.viewModel


class EndlessList : Fragment() {

    private val jokeViewModel:JokeViewModel by viewModel()

    private val jokeAdapter by lazy {
        JokeAdapter()
    }
    private var explicit=true

    private val binding by lazy{
        FragmentEndlessListBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            explicit = it.getBoolean("explicit")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val linearLayoutManager=LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)


        binding.endlessListRV.apply {
            layoutManager = linearLayoutManager
            adapter = jokeAdapter
        }


        jokeViewModel.joke.observe(viewLifecycleOwner) { state ->
            when(state) {
                is ResultState.LOADING -> {
                    Toast.makeText(requireContext(), "loading...", Toast.LENGTH_LONG).show()
                }
                is ResultState.SUCCESSMULTI -> {
                    val jokes = state.results.value
                    jokeAdapter.setNewJokes(jokes)
                }
                is ResultState.ERROR -> {
                    Toast.makeText(requireContext(), state.error.localizedMessage, Toast.LENGTH_LONG).show()
                }
                else -> {}
            }
        }

        val scrollListener = object: EndlessRecyclerViewScrollListener(linearLayoutManager){
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?){
                if (explicit) jokeViewModel.getTwentyJokes("")
                else jokeViewModel.getTwentyJokes("explicit")
            }
        }
        binding.endlessListRV.addOnScrollListener(scrollListener)

        if (explicit) jokeViewModel.getTwentyJokes("")
        else jokeViewModel.getTwentyJokes("explicit")

    return binding.root
    }
}