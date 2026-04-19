package ru.netogy.myapplication.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import ru.netogy.myapplication.R
import ru.netogy.myapplication.databinding.ActivityAppBinding
import ru.netogy.myapplication.fragment.NewPostFragment.Companion.textArg

class AppActivity : AppCompatActivity() {
    private var pendingSharedText: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityAppBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            WindowInsetsCompat.CONSUMED
        }

        intent?.let {
            if (it.action == Intent.ACTION_SEND) {
                val text = it.getStringExtra(Intent.EXTRA_TEXT)
                if (!text.isNullOrBlank()) {
                    pendingSharedText = text
                    intent.removeExtra(Intent.EXTRA_TEXT)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        pendingSharedText?.let { text ->

            findNavController(R.id.container).navigate(
                R.id.action_feedFragment_to_newPostFragment,
                Bundle().apply {
                    textArg = text
                }
            )
            pendingSharedText = null

        }
    }
}