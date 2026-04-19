package ru.netogy.myapplication.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import ru.netogy.myapplication.R
import ru.netogy.myapplication.databinding.CardPostBinding
import ru.netogy.myapplication.dto.Post
import ru.netogy.myapplication.viewmodel.PostViewModel
import kotlin.getValue

class PostFragment: Fragment() {
    val viewModel: PostViewModel by activityViewModels()

    private var currentPost: Post = Post(
        id = 0,
        author = "",
        published = "",
        content = ""
    )
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = CardPostBinding.inflate(layoutInflater)

        fun showPost(post: Post) {
            with(binding) {
                author.text = post.author
                content.text = post.content
                published.text = post.published
                like.text = post.likes.toString()
                share.text = post.shares.toString()
                like.isChecked = post.likedByMe
                if (!post.video.isBlank()) {
                    group.visibility = View.VISIBLE
                } else {
                    group.visibility = View.GONE
                }
            }
        }

        val postId = arguments?.getLong("id")
        viewModel.data.observe(viewLifecycleOwner) { posts ->
            if (posts.find { it.id == postId } != null) {
                currentPost = posts.find { it.id == postId }!!
                showPost(currentPost)
            } else findNavController().navigateUp()
        }

        with(binding) {
            binding.like.setOnClickListener {
                viewModel.likeById(currentPost.id)
            }
            share.setOnClickListener {
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, currentPost.content)
                }
                val chooser =
                    Intent.createChooser(intent, getString(R.string.description_post_share))
                startActivity(chooser)
                viewModel.shareById(currentPost.id)
            }
            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.menu_post)

                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.delete -> {
                                viewModel.deleteById(currentPost.id)
                                findNavController().navigateUp()
                                true
                            }
                            R.id.edit -> {
                                val bundle = Bundle().apply {
                                    putString("content", currentPost.content)
                                }
                                viewModel.edit(currentPost)
                                findNavController().navigate(R.id.action_postFragment_to_newPostFragment, bundle)
                                true
                            } else -> false
                        }

                    }
                    show()
                }
            }
            group.setOnClickListener {
                currentPost.video.let { videoUrl ->
                    val intent = Intent(Intent.ACTION_VIEW).apply {
                        data = videoUrl.toUri()
                    }
                    startActivity(intent)
                }
            }
        }
        return binding.root
    }
}
