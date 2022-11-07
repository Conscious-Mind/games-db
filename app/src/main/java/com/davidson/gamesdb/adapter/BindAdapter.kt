package com.davidson.gamesdb.adapter

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.davidson.gamesdb.R

@BindingAdapter("imageUrl")
fun bindImage(imageView: ImageView, srcUrl:String?){
    srcUrl?.let {
        val imgUri = it.toUri().buildUpon().scheme("https").build()
        Glide.with(imageView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                .placeholder(R.drawable.loading_animation))
            .error(R.drawable.ic_broken_image)
            .into(imageView)
    }
}