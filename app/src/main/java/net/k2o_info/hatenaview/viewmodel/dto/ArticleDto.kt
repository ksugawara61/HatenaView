package net.k2o_info.hatenaview.viewmodel.dto

import java.util.*

/**
 * View用の記事オブジェクト
 *
 * @author katsuya
 * @since 1.0.0
 */
data class ArticleDto constructor(
    var type: Int,
    var title: String,
    //var link: String,
    var description: String,
    var category: String,
    var imageUrl: String,
    var users: Int,
    var publishedAt: Date,
    var tagList: List<String>
)
