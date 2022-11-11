package com.davidson.gamesdb.pagination

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.davidson.gamesdb.adapter.bindImage
import com.davidson.gamesdb.databinding.GameGistCardBinding
import com.davidson.gamesdb.domain.DomainGameGist

class GamePagedAdapter :
    PagingDataAdapter<DomainGameGist, GamePagedAdapter.ItemViewHolder>(DiffUtilItemCallBack()) {

    private var clickListener: ((imageView: ImageView, gameItem: DomainGameGist) -> Unit)? = null

    class ItemViewHolder private constructor(private val binding: GameGistCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(
            position: Int,
            gameItem: DomainGameGist,
            clickListener: ((imageView: ImageView, gameItem: DomainGameGist) -> Unit)?
        ) {
            bindImage(binding.imageView, gameItem.gameGistBgImage)
            binding.textView.text = gameItem.gameGistName
            binding.textView2.text = gameItem.gameGistRating.toString() + "★"
            binding.imageView.transitionName = "game$position"
            clickListener?.let {
                binding.root.setOnClickListener {
                    clickListener.invoke(binding.imageView, gameItem)
                }
            }
        }

        companion object {
            fun from(parent: ViewGroup): ItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = GameGistCardBinding.inflate(layoutInflater, parent, false)
                return ItemViewHolder(binding)
            }
        }
    }

    fun setOnclickListenerR(clickListener: ((imageView: ImageView, gameItem: DomainGameGist) -> Unit)) {
        this.clickListener = clickListener
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(position, it, clickListener) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder.from(parent)
    }
}

class DiffUtilItemCallBack : DiffUtil.ItemCallback<DomainGameGist>() {
    override fun areItemsTheSame(oldItem: DomainGameGist, newItem: DomainGameGist): Boolean {
        return oldItem.gameGistId == newItem.gameGistId
    }

    override fun areContentsTheSame(oldItem: DomainGameGist, newItem: DomainGameGist): Boolean {
        return oldItem == newItem
    }
}