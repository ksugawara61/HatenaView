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
import timber.log.Timber
import java.net.UnknownServiceException
import java.util.concurrent.TimeUnit

/**
 * はてなブックマーク用リポジトリ
 *
 * @author katsuya
 * @since 1.0.0
 */
class HatenaRepository  {

    private val service: HatenaArticleService
    private val hotentoryHatenaRssObject: MutableLiveData<HatenaRssObject> = MutableLiveData()
    private val tagHatenaRssObject: MutableLiveData<HatenaRssObject> = MutableLiveData()

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
        return hotentoryHatenaRssObject
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
                    val tmpHatenaRssObject = response.body() as HatenaRssObject
                    tmpHatenaRssObject.status = true
                    hotentoryHatenaRssObject.postValue(tmpHatenaRssObject)
                } else {
                    Timber.e("${response.code()}: response is failure")
                    hotentoryHatenaRssObject.postValue(HatenaRssObject())
                }
            }

            override fun onFailure(call: Call<HatenaRssObject>, t: Throwable) {
                Timber.e("error: ${t.message}")
                hotentoryHatenaRssObject.postValue(HatenaRssObject())
            }
        })
    }

    /**
     * はてなブックマークのタグ別記事リストを取得
     *
     * @param tag タグ名
     * @param page ページ番号
     * @return タグ別記事リストのLiveData
     */
    fun getTagArticle(category: String, page: Int = 1): LiveData<HatenaRssObject> {
        updateTagArticle(category, page)
        return tagHatenaRssObject
    }

    /**
     * はてなブックマークのタグ別記事リストを更新
     *
     * @param tag タグ名
     */
    fun updateTagArticle(tag: String, page: Int = 1) {
        service.getTagArticle(tag = tag, page = page).enqueue(object : Callback<HatenaRssObject> {
            override fun onResponse(call: Call<HatenaRssObject>, response: Response<HatenaRssObject>) {
                if (response.isSuccessful) {
                    val tmpHatenaRssObject = response.body() as HatenaRssObject
                    tmpHatenaRssObject.status = true
                    tagHatenaRssObject.postValue(tmpHatenaRssObject)
                } else {
                    Timber.e("${response.code()}: response is failure")
                    tagHatenaRssObject.postValue(HatenaRssObject())
                }
            }

            override fun onFailure(call: Call<HatenaRssObject>, t: Throwable) {
                Timber.e("error: ${t.message}")
                tagHatenaRssObject.postValue(HatenaRssObject())
            }
        })
    }

}