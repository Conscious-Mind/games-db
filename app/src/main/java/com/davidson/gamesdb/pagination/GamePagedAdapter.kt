package com.davidson.gamesdb.pagination

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.davidson.gamesdb.adapter.bindImage
import com.davidson.gamesdb.databinding.SingleGameItemBinding
import com.davidson.gamesdb.domain.DomainGame

class GamePagedAdapter :
    PagingDataAdapter<DomainGame, GamePagedAdapter.ItemViewHolder>(DiffUtilItemCallBack()) {

    class ItemViewHolder private constructor(val binding: SingleGameItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(gameItem: DomainGame) {
            bindImage(binding.imageView, gameItem.gameBgImage)
            binding.textView.text = gameItem.gameName
            binding.textView2.text = gameItem.gameReleaseDate
        }

        companion object {
            fun from(parent: ViewGroup): ItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = SingleGameItemBinding.inflate(layoutInflater, parent, false)
                return ItemViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder.from(parent)
    }
}

class DiffUtilItemCallBack : DiffUtil.ItemCallback<DomainGame>() {
    override fun areItemsTheSame(oldItem: DomainGame, newItem: DomainGame): Boolean {
        return oldItem.gameId == newItem.gameId
    }

    override fun areContentsTheSame(oldItem: DomainGame, newItem: DomainGame): Boolean {
        return oldItem == newItem
    }
}