package net.k2o_info.hatenaview.viewmodel.activity

import android.app.Application
import android.arch.lifecycle.*
import android.arch.lifecycle.Observer
import net.k2o_info.hatenaview.model.entity.CategoryEntity
import net.k2o_info.hatenaview.model.repository.CategoryRepository
import net.k2o_info.hatenaview.viewmodel.dto.CategoryDto
import java.util.*
import kotlin.collections.ArrayList

/**
 * MainAcitivity用ViewModel
 *
 * @author katsuya
 * @since 1.0.0
 */
class MainViewModel(application: Application, private val repository: CategoryRepository): AndroidViewModel(application) {

    private var selectedTabPosition = 0
    private val list: MutableLiveData<List<CategoryDto>> = MutableLiveData()

    /**
     * データリストをサブスクライブ
     *
     * @return データリスト
     */
    fun subscribeList(owner: LifecycleOwner): LiveData<List<CategoryDto>> {
        repository.getCategoryList().observe(owner, Observer {
            if (it != null && it.isNotEmpty()) {
                val categoryDtoList: MutableList<CategoryDto> = mutableListOf()

                for (categoryEntity in it) {
                    val categoryDto = CategoryDto(
                        categoryName = categoryEntity.categoryName,
                        categoryQuery = categoryEntity.categoryQuery
                    )
                    categoryDtoList.add(categoryDto)
                }
                list.postValue(categoryDtoList)
            } else {
                initInsertCategoryList()
            }
        })

        return list
    }

    /**
     * 選択されているタブの位置を取得
     *
     * @return タブの位置
     */
    fun getSelectedTabPosition(): Int {
        return selectedTabPosition
    }

    /**
     * 選択されているタブの位置を保存
     *
     * @param position タブの位置
     */
    fun setSelectedTabPosition(position: Int) {
        selectedTabPosition = position
    }

    /**
     * 初回時にカテゴリを登録
     */
    private fun initInsertCategoryList() {
        val categoryList = ArrayList<CategoryEntity>(
            Arrays.asList(
                CategoryEntity(categoryId = 1, categoryName = "総合", categoryQuery = "all"),
                CategoryEntity(categoryId = 2, categoryName = "一般", categoryQuery = "general"),
                CategoryEntity(categoryId = 3, categoryName = "世の中", categoryQuery = "social"),
                CategoryEntity(categoryId = 4, categoryName = "政治と経済", categoryQuery = "economics"),
                CategoryEntity(categoryId = 5, categoryName = "暮らし", categoryQuery = "life"),
                CategoryEntity(categoryId = 6, categoryName = "学び", categoryQuery = "knowledge"),
                CategoryEntity(categoryId = 7, categoryName = "テクノロジー", categoryQuery = "it"),
                CategoryEntity(categoryId = 8, categoryName = "おもしろ", categoryQuery = "fun"),
                CategoryEntity(categoryId = 9, categoryName = "エンタメ", categoryQuery = "entertainment"),
                CategoryEntity(categoryId = 10, categoryName = "アニメとゲーム", categoryQuery = "game")
            )
        )
        repository.insertCategoryList(categoryList)
    }

    class MainFactory(private val application: Application, private val repository: CategoryRepository)
        : ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MainViewModel(application, repository) as T
        }

    }
}