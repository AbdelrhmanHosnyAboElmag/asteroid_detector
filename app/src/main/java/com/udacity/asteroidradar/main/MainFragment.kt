package com.udacity.asteroidradar.main

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.*
import com.udacity.asteroidradar.api.getToday
import com.udacity.asteroidradar.databinding.FragmentMainBinding


class MainFragment : Fragment() {
      var adapter: AdapterAsteroid? =null
    private val viewModel: MainViewModel by lazy {
        val activity = requireNotNull(this.activity) {
        }
        ViewModelProvider(
            this,
            MainViewModel.Factory(activity.application)
        )[MainViewModel::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel
         adapter = AdapterAsteroid(AdapterAsteroid.AsteroidListener { asteroids ->
            val x = AsteroidToDatabaseContainer(asteroids)
            viewModel.onDetailFragmentClick(x.asDatabaseModel())
        })
        binding.asteroidRecycler.adapter = adapter
        viewModel.asteroidsList.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter!!.submitList(it)
            }
        })
        viewModel.navigateToDetailFragment.observe(viewLifecycleOwner, Observer { asteroid ->
            asteroid?.let {
                this.findNavController().navigate(
                    MainFragmentDirections
                        .actionShowDetail(asteroid)
                )
                viewModel.onDetailFragmentNavigated()
            }
        })

        setHasOptionsMenu(true)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.show_saved_menu->viewModel.asteroidsList.observe(viewLifecycleOwner, Observer {
                it?.let {
                    adapter!!.submitList(it)
                }
            })

            R.id.show_today_menu->viewModel.asteroidsListToday.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter!!.submitList(it)
            }
        })
            R.id.show_week_menu->viewModel.asteroidsList.observe(viewLifecycleOwner, Observer {
                it?.let {
                        adapter!!.submitList(it)
                }
            })
        }
        return true
    }
}
