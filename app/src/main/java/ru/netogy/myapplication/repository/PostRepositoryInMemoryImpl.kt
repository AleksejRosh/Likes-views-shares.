package ru.netogy.myapplication.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netogy.myapplication.dto.Post

class PostRepositoryInMemoryImpl: PostRepository {

    private var posts = listOf(
        Post(
            1,
            "Netology. Университет интернет-профессий будущего",
            "21 мая в 18:36",
            "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb"
        ),
        Post (
            2,
            "Netology. Университет интернет-профессий будущего",
            "20 мая в 12:46",
            "Привет, это новая Нетология!"
        ),
        Post (
            3,
            "Netology. Университет интернет-профессий будущего",
            "20 мая в 12:46",
            "Привет, это новая Нетология!"
        )
    )

    private val data = MutableLiveData(posts)

    override fun getAll(): LiveData<List<Post>> = data

    private var nextId = posts.first().id + 3

    override fun likeById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(likedByMe = !it.likedByMe, likes = if (it.likedByMe) it.likes - 1 else it.likes + 1 )
        }
        data.value = posts
    }

    override fun shareById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(shares = it. shares + 1)
        }
        data.value = posts
    }

    override fun deleteById(id: Long) {
        posts = posts.filter{it.id != id}
        data.value = posts
    }

    override fun save(post: Post) {
        if (post.id == 0L) {
            posts = listOf(
                post.copy(
                    id = nextId++,
                    author = "Me",
                    published = "Now"
                )
            ) + posts
        } else posts = posts.map{
            if (it.id == post.id) {

                it.copy(content = post.content)
            } else it
        }
        data.value = posts
    }

}