package net.k2o_info.hatenaview.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import net.k2o_info.hatenaview.model.repository.HatenaRepository
import net.k2o_info.hatenaview.viewmodel.dto.ArticleDto
import java.util.*
import kotlin.collections.ArrayList

class ArticleListViewModel(application: Application) : AndroidViewModel(application) {

    private val list: MutableLiveData<List<ArticleDto>> = MutableLiveData()

    /**
     * コンストラクタ
     */
    init {
        // TODO: Repositoryを依存性がない状態で取得する
        val repository = HatenaRepository()
        repository.getHotentryArticle("all")

        // TODO Repositoryから取得したデータを変換する処理を実装する
        val dto1 = ArticleDto(
            1,
            "これはテスト1です",
            "これはテスト1のディスクリプションです",
            "経済",
            "https://www.ateam-japan.com/wp-content/uploads/1-16.png",
            250,
            Date(),
            Arrays.asList("吉岡里帆", "注目")
        )
        val dto2 = ArticleDto(
            2,
            "これはテスト2です",
            "これはテスト2のディスクリプションです",
            "政治",
            "https://eiga.k-img.com/images/person/83068/0f126f330c25f0ee/320.jpg?1478084080",
            300,
            Date(),
            Arrays.asList("新垣結衣", "ドラマ", "最新")
        )
        val dto3 = ArticleDto(
            2,
            "これはテスト3です",
            "これはテスト3のディスクリプションです",
            "スポーツ",
            "https://eiga.k-img.com/images/person/83068/0f126f330c25f0ee/320.jpg?1478084080",
            1000,
            Date(),
            Arrays.asList("イチロー", "3000本", "MLB")
        )
        val dtoList = ArrayList<ArticleDto>(Arrays.asList(dto1, dto2, dto3))
        list.value = dtoList
    }

    /**
     * データリストを返却
     *
     * @return データリスト
     */
    fun getList(): LiveData<List<ArticleDto>> {
        return list
    }

}