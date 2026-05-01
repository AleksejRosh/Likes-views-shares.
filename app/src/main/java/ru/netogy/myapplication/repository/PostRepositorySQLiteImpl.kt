package ru.netogy.myapplication.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netogy.myapplication.dao.PostDao
import ru.netogy.myapplication.dto.Post

class PostRepositorySQLiteImpl(private val dao: PostDao): PostRepository {

    private var posts = emptyList<Post>()
    private val data = MutableLiveData(posts)
    private val lastDraft = MutableLiveData<Post?>(null)
    override fun getLastDraft(): LiveData<Post?> = lastDraft

    init {
        posts = dao.getAll()
        data.value = posts
    }

    override fun getAll(): LiveData<List<Post>> = data

    override fun likeById(id: Long) {
        dao.likeById(id)
        posts = posts.map {
            if (it.id != id) it else it.copy(likedByMe = !it.likedByMe, likes = if (it.likedByMe) it.likes - 1 else it.likes + 1 )
        }
        data.value = posts
    }

    override fun shareById(id: Long) {
        dao.shareById(id)
        posts = posts.map {
            if (it.id != id) it else it.copy(shares = it. shares + 1)
        }
        data.value = posts
    }

    override fun deleteById(id: Long) {
        dao.removeById(id)
        posts = posts.filter{it.id != id}
        data.value = posts
    }

    override fun save(post: Post) {
        val id = post.id
        val saved = dao.save(post)
        if (post.id == 0L) {
            posts = listOf(saved) + posts
        } else posts = posts.map{
            if (it.id == id) saved else it
        }
        data.value = posts
    }

    override fun saveDraft(post: Post) {
        val saved = dao.saveDraft(post)
        lastDraft.value = saved
    }


}