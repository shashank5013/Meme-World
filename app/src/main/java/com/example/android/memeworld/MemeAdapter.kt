package com.example.android.memeworld

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.android.memeworld.databinding.SingleItemBinding

/**
 * Recycler view adapter which converts meme class object into views
 */
class MemeAdapter(val context: Context, val list:ArrayList<MemeClass>):RecyclerView.Adapter<MemeAdapter.MemeViewHolder>(){

    class MemeViewHolder(val binding: SingleItemBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemeViewHolder {
        return MemeViewHolder(SingleItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }


    override fun onBindViewHolder(holder: MemeViewHolder, position: Int) {
        val meme= list[position]
        holder.binding.author.text="posted by ${meme.author}"
        holder.binding.likeCounter.text=meme.likeCount.toString()
        holder.binding.subreddit.text=meme.subreddit
        holder.binding.memeTitle.text=meme.title

        //Using glide library to load images
        Glide.with(context).load(meme.imageUrl).listener(object:RequestListener<Drawable>{
            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                holder.binding.progressBar.visibility= View.GONE
                return false
            }

            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                holder.binding.progressBar.visibility= View.GONE
                return false
            }
        }) . skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(holder.binding.memeImage)

        //On click listener to open post in custom chrome tabs when clicked
        holder.binding.root.setOnClickListener {
            CustomTabsIntent.Builder().build().launchUrl(context, Uri.parse(meme.postLink))
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}