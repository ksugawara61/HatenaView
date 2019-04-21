package net.k2o_info.hatenaview.model.service

import net.k2o_info.hatenaview.model.`object`.HatenaRssObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * はてなブックマークのRSS取得用サービスインタフェース
 *
 * @author katsuya
 * @since 1.0.0
 */
interface HatenaArticleService {

    /**
     * ホットエントリ取得用
     *
     * @param category カテゴリ
     * @return ホットエントリリスト
     */
    @GET("hotentry/{category}.rss")
    fun getHotentryArticle(
        @Path("category") category: String
    ): Call<HatenaRssObject>

    /**
     * タグ別リスト取得用
     *
     * @param tag タグ
     * @param page 表示するページ
     * @param mode モード（rss固定）
     */
    @GET("search/tag")
    fun getTagArticle(
        @Query(value = "q", encoded = true) tag: String,
        @Query(value = "page", encoded = true) page: Int = 1,
        @Query(value = "mode", encoded = true) mode: String = "rss"
    ): Call<HatenaRssObject>

}

