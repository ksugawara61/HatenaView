package net.k2o_info.hatenaview.viewmodel.fragment

import android.app.Application
import android.arch.lifecycle.*
import android.arch.lifecycle.Observer
import net.k2o_info.hatenaview.Constant
import net.k2o_info.hatenaview.model.repository.HatenaRepository
import net.k2o_info.hatenaview.viewmodel.dto.ArticleDto
import net.k2o_info.hatenaview.viewmodel.translator.ArticleTranslator
import timber.log.Timber
import java.net.UnknownServiceException
import java.text.SimpleDateFormat
import java.util.*

/**
 * ArticleListFragment用ViewModel
 *
 * @author katsuya
 * @since 1.0.0
 */
class ArticleListViewModel(application: Application, private val repository: HatenaRepository,
                           private val category: String) : AndroidViewModel(application) {

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
        repository.getHotentryArticle(category).observe(owner, Observer {
            if (it != null && it.status) {
                val hatenaArticleObjectList = it.itemList ?: emptyList()
                val articleDtoList = ArticleTranslator.translateHatenaArticleListToArticleList(hatenaArticleObjectList)
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
        repository.updateHotentryArticle(category)
    }

    class ArticleListFactory(private val application: Application, private val repository: HatenaRepository,
                             private val category: String): ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ArticleListViewModel(application, repository, category) as T
        }

    }

}