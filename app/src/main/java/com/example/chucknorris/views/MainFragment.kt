package com.example.chucknorris.views

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.chucknorris.R
import com.example.chucknorris.adapter.JokesList
import com.example.chucknorris.databinding.FragmentMainBinding
import com.example.chucknorris.viewModel.ResultState


class MainFragment : BaseFragment() {

    private var clicked=false

    private var explicit=true

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding by lazy{
        FragmentMainBinding.inflate(layoutInflater)
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{


        jokeViewModel.joke.observe(viewLifecycleOwner) { resultState ->
            when (resultState) {
                is ResultState.SUCCESS -> {
                    if (clicked) {
                        AlertDialog.Builder(requireContext())
                            .setMessage(resultState.results.value.joke).show()
                        clicked=false
                    }
                }
                is ResultState.ERROR -> {
                    Log.e("Main", resultState.error.localizedMessage, resultState.error)
                    Toast.makeText(
                        requireContext(),
                        resultState.error.localizedMessage,
                        Toast.LENGTH_LONG
                    ).show()
                }
                else -> {}
            }
        }

        binding.explicitFilterButton.setOnClickListener {
            explicit=!explicit
        }

            binding.randomJoke.setOnClickListener {
                clicked=true
                if (explicit) jokeViewModel.getJoke("")
                else jokeViewModel.getJoke("[explicit]")
            }

            binding.changeName.setOnClickListener {
                findNavController().navigate(R.id.action_MainFragment_to_TextInputFragment, bundleOf(Pair("explicit",explicit)))
            }

            binding.endlessList.setOnClickListener {
                JokesList.jokesList.clear()
                findNavController().navigate(R.id.action_MainFragment_to_EndlessListFragment, bundleOf(Pair("explicit",explicit)))
            }

            return binding.root

        }

}