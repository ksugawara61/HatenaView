package net.k2o_info.hatenaview.viewmodel.activity

import android.app.Application
import android.arch.lifecycle.*
import android.arch.lifecycle.Observer
import net.k2o_info.hatenaview.Constant
import net.k2o_info.hatenaview.model.repository.HatenaRepository
import net.k2o_info.hatenaview.viewmodel.dto.ArticleDto
import java.text.SimpleDateFormat
import java.util.*

/**
 * TagArticleListActivity用ViewModel
 *
 * @author katsuya
 * @since 1.0.0
 */
class TagArticleListViewModel(application: Application, private val repository: HatenaRepository,
                              private val tag: String) : AndroidViewModel(application) {

    private val status: MutableLiveData<Boolean> = MutableLiveData()
    private val list: MutableLiveData<List<ArticleDto>> = MutableLiveData()

    init {
        status.postValue(true)
    }

    /**
     * データ取得のステータスをサブスクライブ
     *
     * @return データ取得のステータス
     */
    fun subscribeStatus(): LiveData<Boolean> {
        return status
    }

    /**
     * データリストをサブスクライブ
     *
     * @return データリスト
     */
    fun subscribeList(owner: LifecycleOwner): LiveData<List<ArticleDto>> {
        repository.getTagArticle(tag).observe(owner, Observer {
            if (it != null && it.status) {
                val articleDtoList: MutableList<ArticleDto> = mutableListOf()
                val hatenaArticleObjectList = it.itemList ?: emptyList()
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
                status.postValue(true)
                list.postValue(articleDtoList)
            } else {
                status.postValue(false)
            }
        })

        return list
    }

    /**
     * データリストのリフレッシュ処理を行う
     */
    fun refreshList() {
        repository.updateTagArticle(tag)
    }

    class TagArticleListFactory(private val application: Application, private val repository: HatenaRepository,
                             private val tag: String): ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return TagArticleListViewModel(application, repository, tag) as T
        }

    }

}