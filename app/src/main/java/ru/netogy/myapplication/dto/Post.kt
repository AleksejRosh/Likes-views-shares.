package ru.netogy.myapplication.dto

data class Post (
    val id: Int,
    val author: String,
    val published: String,
    val content: String,
    var likes: Int = 0,
    var likedByMe: Boolean = false,
    var shares: Int = 0
)

fun changeOne (a: Int): String {
    return when {
        a < 1_000 -> "$a"
        a < 10_000 -> changeWithComma(a, 1_000, "K")
        a < 100_000 -> changeWithoutComma(a)
        else -> changeWithComma(a, 1_000_000, "M")
    }
}

private fun changeWithComma(a: Int, c: Int, f: String): String {
    val d = (a/c).toInt()
    val e = ((a - d*c)/(c/10)).toInt()
    return "$d" + ",$e" + f
}
private fun changeWithoutComma(a: Int): String {
    val b = (a/1_000).toInt().toString()
    return b + "K"
}