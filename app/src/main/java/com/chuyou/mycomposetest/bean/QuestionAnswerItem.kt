package com.chuyou.mycomposetest.bean

data class QuestionAnswerItem(
    val id: Int =0 ,
    val desc: String = "",
    val author: String = "",
    val link: String = "",
    val title: String = "",
    val time: String = "",
    val superChapterName: String = "",
    val chapterName: String = "",
    val collect: Boolean = false,
    val niceDate: String = "",
    val tags: List<Tag> = emptyList(),
)

data class Tag(
    val name: String = "",
    val url: String = "",
)
