package ru.netogy.myapplication.repository

import androidx.lifecycle.LiveData
import ru.netogy.myapplication.dto.Post

interface PostRepository {
    fun getAll(): LiveData<List<Post>>
    fun likeById(id: Long)
    fun shareById(id: Long)
}