package ru.netogy.myapplication.activity


import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ru.netogy.myapplication.R
import ru.netogy.myapplication.databinding.ActivityNewPostBinding



class NewPostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityNewPostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val editText = intent.getStringExtra(NewPostContract.KEY_TEXT)
        binding.edit.setText(editText)
        binding.ok.setOnClickListener {
            val text = binding.edit.text.toString()
            if (text.isBlank()) {
                setResult(RESULT_CANCELED)
            } else {
                val intent = Intent().putExtra(NewPostContract.KEY_TEXT, text)
                setResult(RESULT_OK, intent)
            }
            finish()
        }
    }
}

object NewPostContract: ActivityResultContract<String, String?>() {
    const val KEY_TEXT = "post_text"
    override fun createIntent(context: Context, input: String): Intent {
        val intent = Intent(context, NewPostActivity::class.java)
        intent.putExtra(KEY_TEXT, input)
        return intent
    }
    override fun parseResult(resultCode: Int, intent: Intent?) = intent?.getStringExtra(KEY_TEXT)
}