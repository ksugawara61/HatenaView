package net.k2o_info.hatenaview.viewmodel

import android.app.Application
import android.arch.lifecycle.*
import android.arch.lifecycle.Observer
import net.k2o_info.hatenaview.Constant
import net.k2o_info.hatenaview.model.repository.HatenaRepository
import net.k2o_info.hatenaview.viewmodel.dto.ArticleDto
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

class ArticleListViewModel(application: Application, private val repository: HatenaRepository,
                           private val category: String) : AndroidViewModel(application) {

    private val list: MutableLiveData<List<ArticleDto>> = MutableLiveData()

    /**
     * データリストをサブスクライブ
     *
     * @return データリスト
     */
    fun subscribeList(owner: LifecycleOwner): LiveData<List<ArticleDto>> {
        repository.getHotentryArticle(category).observe(owner, Observer {
            if (it != null) {
                val articleDtoList: MutableList<ArticleDto> = mutableListOf()
                val hatenaArticleObjectList = it.itemList ?: emptyList()
                val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
                for ((index, hatenaArticle) in hatenaArticleObjectList.withIndex()) {
                    var type = Constant.VIEW_TYPE_DEFAULT
                    if (index == 0) {
                        type = Constant.VIEW_TYPE_TOP
                    }

                    val articleDto = ArticleDto(
                        type = type,
                        title = hatenaArticle.title ?: continue,
                        link = hatenaArticle.link ?: continue,
                        description = hatenaArticle.description ?: continue,
                        imageUrl = hatenaArticle.imageUrl ?: continue,
                        users = hatenaArticle.bookmarkCount ?: continue,
                        publishedAt = dateFormat.parse(hatenaArticle.date),
                        tagList = hatenaArticle.subjectList ?: continue
                    )
                    articleDtoList.add(articleDto)
                }
                list.postValue(articleDtoList)
            }
        })

        return list
    }

    /**
     * データリストのリフレッシュ処理を行う
     */
    fun refreshList() {
        repository.updateHotentryArticle(category)
    }

    class ArticleListFactory(private val application: Application, private val repository: HatenaRepository,
                             private val category: String): ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ArticleListViewModel(application, repository, category) as T
        }

    }

}