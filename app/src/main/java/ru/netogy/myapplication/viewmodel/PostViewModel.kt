package ru.netogy.myapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netogy.myapplication.dto.Post
import ru.netogy.myapplication.repository.PostRepository
import ru.netogy.myapplication.repository.PostRepositoryFileImpl

class PostViewModel(application: Application): AndroidViewModel(application) {
    private val repository: PostRepository = PostRepositoryFileImpl(application)
    val data = repository.getAll()
    val edited = MutableLiveData(empty)
    fun likeById(id: Long) = repository.likeById(id)
    fun shareById(id: Long) = repository.shareById(id)
    fun deleteById(id: Long) = repository.deleteById(id)

    fun save(content: String) {
        edited.value?.let {
            val text = content.trim()
            if (text != it.content) {
                repository.save(it.copy(content = text))
            }
        }
        edited.value = empty
    }
    fun edit(post: Post) {
        edited.value = post
    }
    fun cancelEdit() {
        edited.value = empty
    }
}
private val empty = Post(
    id = 0,
    author = "",
    published = "",
    content = ""
    )