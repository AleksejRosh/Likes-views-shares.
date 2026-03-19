package ru.netogy.myapplication.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ru.netogy.myapplication.R
import ru.netogy.myapplication.databinding.ActivityMainBinding
import ru.netogy.myapplication.dto.changeOne
import ru.netogy.myapplication.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val viewModel by viewModels<PostViewModel>()
        viewModel.data.observe(this) { post ->

            with(binding) {
                author.text = post.author
                published.text = post.published
                content.text = post.content
                like.setImageResource(if (post.likedByMe) R.drawable.ic_liked_24dp else R.drawable.ic_like_24dp)
                countOfLikes.text = changeOne(post.likes)
                countOfShares.text = changeOne(post.shares)

            }
        }
        binding.like.setOnClickListener {
            viewModel.like()
        }
        binding.share.setOnClickListener {
            viewModel.share()
        }
    }
}