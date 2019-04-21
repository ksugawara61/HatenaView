package net.k2o_info.hatenaview.model.service

import net.k2o_info.hatenaview.model.`object`.HatenaRssObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

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

}

