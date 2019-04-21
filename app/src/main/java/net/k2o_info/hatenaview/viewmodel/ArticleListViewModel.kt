package net.k2o_info.hatenaview.viewmodel

import android.app.Application
import android.arch.lifecycle.*
import android.arch.lifecycle.Observer
import net.k2o_info.hatenaview.model.repository.HatenaRepository
import net.k2o_info.hatenaview.viewmodel.dto.ArticleDto
import java.util.*

class ArticleListViewModel(application: Application, private val owner: LifecycleOwner,
                           private val repository: HatenaRepository, private val category: String) : AndroidViewModel(application) {

    private val list: MutableLiveData<List<ArticleDto>> = MutableLiveData()

    /**
     * コンストラクタ
     */
    init {
        repository.getHotentryArticle(category).observe(owner, Observer {
            if (it != null) {
                val articleDtoList: MutableList<ArticleDto> = mutableListOf()
                val hatenaArticleObjectList = it.itemList ?: emptyList()
                for (hatenaArticle in hatenaArticleObjectList) {
                    val articleDto = ArticleDto(
                        type = 1,
                        title = hatenaArticle.title ?: continue,
                        description = hatenaArticle.description ?: continue,
                        category = "テスト",
                        imageUrl = hatenaArticle.imageUrl ?: continue,
                        users = hatenaArticle.bookmarkCount ?: continue,
                        publishedAt = Date(),
                        tagList = hatenaArticle.subjectList ?: continue
                    )
                    articleDtoList.add(articleDto)
                }
                list.postValue(articleDtoList)
            }
        })
    }

    /**
     * データリストを返却
     *
     * @return データリスト
     */
    fun getList(): LiveData<List<ArticleDto>> {
        return list
    }

    /**
     * データリストのリフレッシュ処理を行う
     */
    fun refreshList() {
        repository.updateHotentryArticle(category)
    }

    class ArticleListFactory(private val application: Application, private val owner: LifecycleOwner,
                                   private val repository: HatenaRepository, private val category: String): ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ArticleListViewModel(application, owner, repository, category) as T
        }

    }

}