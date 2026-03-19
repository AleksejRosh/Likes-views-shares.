package ru.netogy.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import ru.netogy.myapplication.repository.PostRepository
import ru.netogy.myapplication.repository.PostRepositoryInMemoryImpl

class PostViewModel: ViewModel() {
    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val data = repository.get()
    fun like() = repository.like()
    fun share() = repository.share()
}