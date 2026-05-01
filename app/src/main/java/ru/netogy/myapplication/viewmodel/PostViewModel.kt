package ru.netogy.myapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netogy.myapplication.db.AppDb
import ru.netogy.myapplication.dto.Post
import ru.netogy.myapplication.repository.PostRepository
import ru.netogy.myapplication.repository.PostRepositorySQLiteImpl

class PostViewModel(application: Application): AndroidViewModel(application) {
    private val repository: PostRepository = PostRepositorySQLiteImpl(AppDb.getInstance(application).postDao)
    val data = repository.getAll()
    private val lastDraft1 = MutableLiveData<Post?>()
    val lastDraft: LiveData<Post?> = lastDraft1

    init {
        if (repository is PostRepositorySQLiteImpl) {
            repository.getLastDraft().observeForever { lastDraft1.value = it }
        }
    }
    val edited = MutableLiveData(empty)
    fun likeById(id: Long) = repository.likeById(id)
    fun shareById(id: Long) = repository.shareById(id)
    fun deleteById(id: Long) = repository.deleteById(id)
    fun save(content: String) {
        edited.value?.let {
            val text = content.trim()
            if (text != it.content) {
                repository.save(it.copy(content = text))
                lastDraft1.value = null
            }
        }
        cancelEdit()
    }
    fun saveDraft(content: String) {
        edited.value?.let {
            val text = content.trim()
            if (text != it.content) {
                val draftPost = Post(
                    id = 0L,
                    author = "Me",
                    content = "",
                    published = "",
                    likedByMe = false,
                    likes = 0,
                    shares = 0,
                    video = "",
                    draft = text
                )
                repository.saveDraft(draftPost)
            }
        }
        cancelEdit()
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