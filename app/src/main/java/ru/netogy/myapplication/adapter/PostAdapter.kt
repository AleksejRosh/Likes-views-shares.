package ru.netogy.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netogy.myapplication.R
import ru.netogy.myapplication.databinding.CardPostBinding
import ru.netogy.myapplication.dto.Post
import ru.netogy.myapplication.dto.changeOne

typealias LikeListener = (Post) -> Unit
typealias ShareListener = (Post) -> Unit

class PostAdapter(private val likeListener: LikeListener, private val shareListener: ShareListener): ListAdapter<Post, PostViewHolder>(
    PostDiffCallback
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, likeListener, shareListener)
    }

    override fun onBindViewHolder(viewHolder: PostViewHolder, position: Int) {
        val post = getItem(position)
        viewHolder.bind(post)
    }

}

class PostViewHolder(private val binding: CardPostBinding, private val likeListener: LikeListener, private val shareListener: ShareListener): RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {
        with (binding) {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            like.setImageResource(if (post.likedByMe) R.drawable.ic_liked_24dp else R.drawable.ic_like_24dp)
            countOfLikes.text = changeOne(post.likes)
            countOfShares.text = changeOne(post.shares)
            like.setOnClickListener {
                likeListener(post)
            }
            share.setOnClickListener {
                shareListener(post)
            }
        }
    }
}

object PostDiffCallback: DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Post, newItem: Post) = oldItem == newItem

}