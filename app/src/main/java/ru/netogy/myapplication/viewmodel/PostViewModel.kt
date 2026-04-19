package ru.netogy.myapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netogy.myapplication.db.AppDb
import ru.netogy.myapplication.dto.Post
import ru.netogy.myapplication.repository.PostRepository
import ru.netogy.myapplication.repository.PostRepositorySQLiteImpl

class PostViewModel(application: Application): AndroidViewModel(application) {
    private val repository: PostRepository = PostRepositorySQLiteImpl(AppDb.getInstance(application).postDao)
    val data = repository.getAll()
    val edited = MutableLiveData(empty)
    fun likeById(id: Long) = repository.likeById(id)
    fun shareById(id: Long) = repository.shareById(id)
    fun deleteById(id: Long) = repository.deleteById(id)
//    fun saveDraft(content: String) {
//        edited.value?.let {
//            val text = content.trim()
//            if (text != it.content) {
//                repository.save(it.copy(draft = text))
//            }
//        }
//        cancelEdit()
//    }
    fun save(content: String) {
        edited.value?.let {
            val text = content.trim()
            if (text != it.content) {
                repository.save(it.copy(content = text))
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
    //draft = ""
    )