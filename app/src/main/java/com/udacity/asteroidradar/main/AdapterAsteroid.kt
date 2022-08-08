package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.databinding.AsteroidlistBinding

class AdapterAsteroid(val clickListener: AsteroidListener) :  ListAdapter<AsteroidDatabase, AdapterAsteroid.ViewHolder>(AdapterAsteroidDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(clickListener,getItem(position)!!)
    }

    class ViewHolder private constructor(val binding: AsteroidlistBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(clickListener: AsteroidListener, item: AsteroidDatabase) {
            binding.asteroids = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = AsteroidlistBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
    class AdapterAsteroidDiffCallback : DiffUtil.ItemCallback<AsteroidDatabase>() {
        override fun areItemsTheSame(oldItem: AsteroidDatabase, newItem: AsteroidDatabase): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: AsteroidDatabase, newItem: AsteroidDatabase): Boolean {
            return oldItem.id==newItem.id
        }
    }
    class AsteroidListener(val clickListener: (asteroid: AsteroidDatabase) -> Unit) {
        fun onClick(Asteroid: AsteroidDatabase) = clickListener(Asteroid)
    }
}