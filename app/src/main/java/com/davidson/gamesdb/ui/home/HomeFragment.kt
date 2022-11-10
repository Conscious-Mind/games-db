package com.davidson.gamesdb.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.davidson.gamesdb.databinding.FragmentHomeBinding
import com.davidson.gamesdb.pagination.GamePagedAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.delay
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

        val rvAdapter = GamePagedAdapter()
        val srLayoutHome = binding.srlHome
        val tabHome = binding.tabLayout
        val snapHelper: SnapHelper = LinearSnapHelper()

        binding.rvHome.apply {
            adapter = rvAdapter
            snapHelper.attachToRecyclerView(this)
        }

        viewModel.gamesList.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                it?.let {
                    Toast.makeText(context, "observed", Toast.LENGTH_SHORT).show()
                    Log.i("TAG", it.toString())
                    rvAdapter.submitData(it)
                }
            }
        }


        srLayoutHome.setOnRefreshListener {
            viewModel.searchQuery.value = "aven"
            rvAdapter.retry()
            rvAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT
            srLayoutHome.isRefreshing = false
        }


        tabHome.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tabHome.selectedTabPosition) {
                    0 -> {
//                        Toast.makeText(
//                            context,
//                            "${tabHome.selectedTabPosition}",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                        binding.rvHome.animate().rotationBy(360f).duration = 1000L
                        viewModel.searchQuery.value = "aft"
                        rvAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT
                    }
                    1 -> {
//                        Toast.makeText(
//                            context,
//                            "${tabHome.selectedTabPosition}",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                        binding.rvHome.animate().rotationBy(360f).duration = 1000L
                        viewModel.searchQuery.value = "f"
                        rvAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT

                    }
                    2 -> {
//                        Toast.makeText(
//                            context,
//                            "${tabHome.selectedTabPosition}",
//                            Toast.LENGTH_SHORT
//                        ).show()
                        binding.rvHome.visibility = View.INVISIBLE
//                        binding.rvHome.animate().rotationBy(360f).duration = 1000L
                        viewModel.searchQuery.value = "t"
                        rvAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT
                        binding.rvHome.visibility = View.VISIBLE
                    }
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // Handle tab reselect
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // Handle tab unselect
                viewModel.searchQuery.value = "returnEmpty#21"
            }
        })



        rvAdapter.addLoadStateListener { loadStates ->
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