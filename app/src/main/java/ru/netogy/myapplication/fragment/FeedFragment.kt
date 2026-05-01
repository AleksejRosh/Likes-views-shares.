package ru.netogy.myapplication.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import ru.netogy.myapplication.R
import ru.netogy.myapplication.adapter.PostAdapter
import ru.netogy.myapplication.adapter.PostListener
import ru.netogy.myapplication.databinding.FragmentFeedBinding
import ru.netogy.myapplication.dto.Post
import ru.netogy.myapplication.viewmodel.PostViewModel

class FeedFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFeedBinding.inflate(layoutInflater)
        val viewModel: PostViewModel by activityViewModels()
        val adapter = PostAdapter(object : PostListener {
            override fun onEdit(post: Post) {
                val bundle = Bundle().apply {
                    putString("draft", post.draft)
                    putString("content", post.content)
                }
                viewModel.edit(post)
                findNavController().navigate(R.id.action_feedFragment_to_newPostFragment, bundle)
            }

            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onShare(post: Post) {
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, post.content)
                }
                val chooser =
                    Intent.createChooser(intent, getString(R.string.description_post_share))
                startActivity(chooser)
                viewModel.shareById(post.id)
            }

            override fun onShow(post: Post) {
                val bundle2 = Bundle().apply {
                    putLong("id", post.id)
                }
                findNavController().navigate(R.id.action_feedFragment_to_postFragment, bundle2)
            }

            override fun onDelete(post: Post) {
                viewModel.deleteById(post.id)
            }

            override fun onPlay(post: Post) {
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = (post.video).toUri()
                }
                startActivity(intent)
            }
        })
        binding.list.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner) { posts ->
            adapter.submitList(posts)
        }

        binding.add.setOnClickListener {
            viewModel.cancelEdit()
            findNavController().navigate(R.id.action_feedFragment_to_newPostFragment)
        }
        return binding.root
    }
}