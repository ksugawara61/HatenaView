package net.k2o_info.hatenaview.model.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import net.k2o_info.hatenaview.Constant
import net.k2o_info.hatenaview.model.`object`.HatenaRssObject
import net.k2o_info.hatenaview.model.service.HatenaArticleService
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.net.UnknownServiceException
import java.util.concurrent.TimeUnit

/**
 * はてなブックマーク用リポジトリ
 *
 * @author katsuya
 * @since 1.0.0
 */
object HatenaRepository  {

    private val service: HatenaArticleService
    private val hatenaRssObject: MutableLiveData<HatenaRssObject> = MutableLiveData()

    /**
     * コンストラクタ
     */
    init {
        // HTTPリクエスト設定
        val client = OkHttpClient.Builder()
            .connectTimeout(Constant.OKHTTP_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(Constant.OKHTTP_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(Constant.OKHTTP_TIMEOUT, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl(Constant.HATENA_BASE_URL)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build()

        service = retrofit.create(HatenaArticleService::class.java)
    }

    /**
     * はてなブックマークのホットエントリを取得
     *
     * @param category カテゴリ
     * @return ホットエントリのLiveData
     */
    fun getHotentryArticle(category: String): LiveData<HatenaRssObject> {
        updateHotentryArticle(category)
        return hatenaRssObject
    }

    /**
     * はてなブックマークのホットエントリを更新
     *
     * @param category カテゴリ
     */
    fun updateHotentryArticle(category: String) {
        service.getHotentryArticle(category).enqueue(object : Callback<HatenaRssObject> {
            override fun onResponse(call: Call<HatenaRssObject>, response: Response<HatenaRssObject>) {
                if (response.isSuccessful) {
                    hatenaRssObject.postValue(response.body() as HatenaRssObject)
                } else {
                    throw UnknownServiceException("${response.code()}: response is failure")
                }
            }

            override fun onFailure(call: Call<HatenaRssObject>, t: Throwable) {
                throw UnknownServiceException(t.message)
            }
        })
    }

}