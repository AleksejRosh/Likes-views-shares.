package ru.netogy.myapplication.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ru.netogy.myapplication.adapter.PostAdapter
import ru.netogy.myapplication.databinding.ActivityMainBinding
import ru.netogy.myapplication.util.AndroidUtils
import ru.netogy.myapplication.viewmodel.PostViewModel
import ru.netogy.myapplication.R
import ru.netogy.myapplication.adapter.PostListener
import ru.netogy.myapplication.dto.Post

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

        val viewModel: PostViewModel by viewModels()
        val adapter = PostAdapter(object : PostListener {
            override fun onEdit(post: Post) {
                viewModel.edit(post)
            }

            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onShare(post: Post) {
                viewModel.shareById(post.id)
            }

            override fun onDelete(post: Post) {
                viewModel.deleteById(post.id)
            }
        })
        binding.list.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }
        viewModel.edited.observe(this) { post ->
            if (post.id != 0L) {
                binding.group.visibility = android.view.View.VISIBLE
                with(binding.content) {
                    setText(post.content)
                    AndroidUtils.showKeyboard(this)
                }
            }
        }
        binding.save.setOnClickListener {
            binding.group.visibility = android.view.View.GONE
            with (binding.content) {
                if (text.isNullOrBlank()) {
                    Toast.makeText(
                        this@MainActivity,
                        context.getString(R.string.error_empty_content),
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
                viewModel.save(text.toString())

                setText("")
                clearFocus()
                AndroidUtils.hideKeyboard(this)
            }
        }

        binding.close.setOnClickListener {
            viewModel.cancelEdit()
            binding.content.text?.clear()
            AndroidUtils.hideKeyboard(binding.content)
            binding.group.visibility = android.view.View.GONE
        }

    }
}