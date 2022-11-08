package com.davidson.gamesdb.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
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

        val rvAdapter = GamePagedAdapter()

        binding.rvHome.apply {
            adapter = rvAdapter
        }

        viewModel.gamesList.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                it?.let {
                    Log.i("TAG", it.toString())
                    rvAdapter.submitData(it)
                }
            }

        }

        rvAdapter.addLoadStateListener { loadStates ->
//            when (loadStates.refresh) {
//                is LoadState.Loading -> Log.d("loadState", "Refresh - Loading")
//                is LoadState.NotLoading -> Log.d("loadState", "Refresh - Not Loading")
//                is LoadState.Error -> Log.d("loadState", "Refresh - Error")
//            }

//            when (loadStates.append) {
//                is LoadState.Loading -> Log.d("loadState", "Append - Loading")
//                is LoadState.NotLoading -> Log.d("loadState", "Append - Not Loading")
//                is LoadState.Error -> Log.d("loadState", "Append - Error")
//            }

//            when (loadStates.prepend) {
//                is LoadState.Loading -> Log.d("loadState", "Prepend - Loading")
//                is LoadState.NotLoading -> Log.d("loadState", "Prepend - Not Loading")
//                is LoadState.Error -> Log.d("loadState", "Prepend - Error")
//            }

        }
        binding.button.setOnClickListener{
            rvAdapter.retry()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}