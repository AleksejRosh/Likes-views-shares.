package ru.netogy.myapplication.repository

import androidx.lifecycle.LiveData
import ru.netogy.myapplication.dto.Post

interface PostRepository {
    fun get(): LiveData<Post>
    fun like()
    fun share()
}