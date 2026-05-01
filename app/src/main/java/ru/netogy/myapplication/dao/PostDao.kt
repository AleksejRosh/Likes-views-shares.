package ru.netogy.myapplication.dao

import ru.netogy.myapplication.dto.Post

interface PostDao {
    fun getAll(): List<Post>
    fun save(post: Post): Post
    fun likeById(id: Long)
    fun removeById(id: Long)
    fun shareById(id: Long)
    fun saveDraft(post: Post): Post
}