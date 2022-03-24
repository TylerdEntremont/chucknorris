package com.example.chucknorris.views

import androidx.fragment.app.Fragment
import com.example.chucknorris.viewModel.JokeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

open class BaseFragment: Fragment() {
    protected val jokeViewModel: JokeViewModel by viewModel()
}