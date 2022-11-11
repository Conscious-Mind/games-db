package com.davidson.gamesdb.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.davidson.gamesdb.databinding.FragmentHomeBinding
import com.davidson.gamesdb.pagination.GamePagedAdapter
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        binding.lifecycleOwner = viewLifecycleOwner

        val rvAdapter1 = GamePagedAdapter().also {
            it.setOnclickListenerR { imageView, gameItem ->
                Toast.makeText(activity, gameItem.gameGistName, Toast.LENGTH_SHORT).show()
                val extras = FragmentNavigatorExtras(imageView to "GameDetailed")
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(gameItem), extras)

            }
        }
        val rvAdapter2 = GamePagedAdapter().also {
            it.setOnclickListenerR { imageView, gameItem ->
                Toast.makeText(activity, gameItem.gameGistName, Toast.LENGTH_SHORT).show()
                val extras = FragmentNavigatorExtras(imageView to "GameDetailed")
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(gameItem), extras)

            }
        }
        val rvAdapter3 = GamePagedAdapter().also {
            it.setOnclickListenerR { imageView, gameItem ->
                Toast.makeText(activity, gameItem.gameGistName, Toast.LENGTH_SHORT).show()
                val extras = FragmentNavigatorExtras(imageView to "GameDetailed")
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(gameItem), extras)

            }
        }
        val srLayoutHome = binding.srlHome
        val snapHelper1: SnapHelper = LinearSnapHelper()
        val snapHelper2: SnapHelper = LinearSnapHelper()
        val snapHelper3: SnapHelper = LinearSnapHelper()



        binding.rvHome.apply {
            adapter = rvAdapter1
            snapHelper1.attachToRecyclerView(this)
        }
        binding.rvHome2.apply {
            adapter = rvAdapter2
            snapHelper2.attachToRecyclerView(this)
        }

        binding.rvHome3.apply {
            adapter = rvAdapter3
            snapHelper3.attachToRecyclerView(this)
        }

        viewModel.gameFromPc.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                it?.let {
                    Log.i("TAG", it.toString())
                    rvAdapter1.submitData(it)
                }
            }
        }

        viewModel.gameFromPs5.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                it?.let {
                    Log.i("TAG", it.toString())
                    rvAdapter2.submitData(it)
                }
            }
        }
        viewModel.gameFromMobile.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                it?.let {
                    Log.i("TAG", it.toString())
                    rvAdapter3.submitData(it)
                }
            }
        }


        srLayoutHome.setOnRefreshListener {
            rvAdapter1.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT
            srLayoutHome.isRefreshing = false
        }





        rvAdapter1.addLoadStateListener { loadStates ->
            when (loadStates.refresh) {
                is LoadState.Loading -> {
                    Log.d("loadState", "Refresh - Loading")
                }
                is LoadState.NotLoading -> Log.d("loadState", "Refresh - Not Loading")
                is LoadState.Error -> {
                    Log.d("loadState", "Refresh - Error")
                }
            }

            when (loadStates.append) {
                is LoadState.Loading -> Log.d("loadState", "Append - Loading")
                is LoadState.NotLoading -> Log.d("loadState", "Append - Not Loading")
                is LoadState.Error -> Log.d("loadState", "Append - Error")
            }

//            when (loadStates.prepend) {
//                is LoadState.Loading -> Log.d("loadState", "Prepend - Loading")
//                is LoadState.NotLoading -> Log.d("loadState", "Prepend - Not Loading")
//                is LoadState.Error -> Log.d("loadState", "Prepend - Error")
//            }

        }

        return binding.root
    }


}