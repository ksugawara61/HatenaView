package net.k2o_info.hatenaview.viewmodel.translator

import net.k2o_info.hatenaview.Constant
import net.k2o_info.hatenaview.model.`object`.HatenaArticleObject
import net.k2o_info.hatenaview.viewmodel.dto.ArticleDto
import java.text.SimpleDateFormat
import java.util.*

object ArticleTranslator {

    /**
     * はてなRSSオブジェクトを記事用に変換する
     *
     * @param hatenaArticleObjectList はてな記事オブジェクト
     * @return 変換後のリスト
     */
    fun translateHatenaArticleListToArticleList(hatenaArticleObjectList: List<HatenaArticleObject>): List<ArticleDto> {
        val articleDtoList: MutableList<ArticleDto> = mutableListOf()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.JAPAN)
        for ((index, hatenaArticle) in hatenaArticleObjectList.withIndex()) {
            var type = Constant.VIEW_TYPE_DEFAULT
            if (index == 0) {
                type = Constant.VIEW_TYPE_TOP
            }

            val articleDto = ArticleDto(
                type = type,
                title = hatenaArticle.title ?: continue,
                link = hatenaArticle.link ?: continue,
                description = hatenaArticle.description ?: "",
                imageUrl = hatenaArticle.imageUrl ?: "",
                users = hatenaArticle.bookmarkCount ?: 0,
                publishedAt = dateFormat.parse(hatenaArticle.date),
                tagList = hatenaArticle.subjectList ?: emptyList()
            )
            articleDtoList.add(articleDto)
        }

        return articleDtoList
    }

}