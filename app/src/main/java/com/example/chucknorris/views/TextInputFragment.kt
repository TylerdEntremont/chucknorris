package com.example.chucknorris.views

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.chucknorris.databinding.FragmentTextInputBinding
import com.example.chucknorris.viewModel.ResultState


class TextInputFragment : BaseFragment() {

    private var _binding: FragmentTextInputBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var explicit =true


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

        _binding = FragmentTextInputBinding.inflate(inflater, container, false)


        jokeViewModel.joke.observe(viewLifecycleOwner) { resultState ->
            when (resultState) {
                is ResultState.SUCCESS -> {
                    AlertDialog.Builder(requireContext()).setMessage(resultState.results.value.joke)
                        .show()
                }
                is ResultState.ERROR -> {
                    Log.e("Text", resultState.error.localizedMessage, resultState.error)
                    Toast.makeText(
                        requireContext(),
                        resultState.error.localizedMessage,
                        Toast.LENGTH_LONG
                    ).show()
                }
                else->{}
            }
        }
        
        binding.Submit.setOnClickListener {
            val names = binding.newName.text.toString().split(" ")
            if (explicit) jokeViewModel.getNewNameJoke(names[0],names[1],"")
            else jokeViewModel.getNewNameJoke(names[0],names[1],"[explicit]")
        }

        return binding.root

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}