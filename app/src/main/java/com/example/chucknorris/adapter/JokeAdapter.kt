package com.example.chucknorris.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chucknorris.databinding.EndlessListItemBinding
import com.example.chucknorris.model.Value

class JokeAdapter (
    ) : RecyclerView.Adapter<JokeViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeViewHolder {
            return JokeViewHolder(
               EndlessListItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: JokeViewHolder, position: Int) =
            holder.bind(JokesList.jokesList[position])

        override fun getItemCount(): Int = JokesList.jokesList.size

        fun setNewJokes(newJokes: List<Value>) {
            JokesList.jokesList.addAll(newJokes)
            notifyDataSetChanged()
        }

        fun clearJokes(){
            JokesList.jokesList.clear()
            notifyDataSetChanged()
        }
    }

    class JokeViewHolder(
        private val binding: EndlessListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(joke: Value){
            binding.joke.text=joke.joke
        }

    }